package com.uidser.ppmusic.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.uidser.ppmusic.common.entity.Media;
import com.uidser.ppmusic.common.entity.Singer;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.search.entity.QueryReturnVo;
import com.uidser.ppmusic.search.service.SearchService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Override
    public QueryReturnVo query(QueryVo queryVo) {
        String queryText = queryVo.getQueryText();
        List<Query> queryList = new ArrayList<>();
        Query nameQuery = Query.of(q -> q.match(mq -> mq.field("name").query(fv -> fv.stringValue(queryText))));
        Query authorQuery = Query.of(q -> q.match(mq -> mq.field("author").query(fv -> fv.stringValue(queryText))));
        Query albumQuery = Query.of(q -> q.match(mq -> mq.field("album").query(fv -> fv.stringValue(queryText))));
        Query mvQuery = Query.of(q -> q.match(mq -> mq.field("mv").query(fv -> fv.stringValue(queryText))));
        HighlightField nameHighLight = HighlightField.of(hlf -> hlf.matchedFields("name")
                .matchedFields("name").preTags("<span style='color: #ff0039;'>")
                .postTags("</span>"));
        HighlightField authorHighLight = HighlightField.of(hlf -> hlf.matchedFields("author")
                .matchedFields("author").preTags("<span style='color: #ff0039;'>")
                .postTags("</span>"));
        HighlightField albumHighLight = HighlightField.of(hlf -> hlf.matchedFields("album")
                .matchedFields("album").preTags("<span style='color: #ff0039;'>")
                .postTags("</span>"));
        Map<String, HighlightField> stringHighlightFieldMap = new HashMap<>();
        stringHighlightFieldMap.put("name", nameHighLight);
        stringHighlightFieldMap.put("author", authorHighLight);
        stringHighlightFieldMap.put("album", albumHighLight);
        queryList.add(nameQuery);
        queryList.add(authorQuery);
        queryList.add(albumQuery);
        queryList.add(mvQuery);
        SearchRequest searchRequest = SearchRequest.of(seq -> {
            seq.index("media");
            seq.query(q -> q.bool(bq -> bq.should(queryList)));
            seq.sort(so -> so.field(fs -> fs.field("playQuantity").order(SortOrder.Desc)));
            seq.highlight(hl -> hl.fields(stringHighlightFieldMap));
            return seq;
        });
        SearchResponse<Media> searchResponse = null;
        try {
            searchResponse = elasticsearchClient.search(searchRequest, Media.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Hit<Media>> hitList = searchResponse.hits().hits();
        if(hitList.size() > 0) {
            QueryReturnVo queryReturnVo = new QueryReturnVo();
            List<Media> songList = queryReturnVo.getSongList();
            for (Hit<Media> mediaHit: hitList) {
                Media media = new Media();
                BeanUtils.copyProperties(mediaHit.source(), media);
                if(mediaHit.highlight().size() > 0) {
                    Map<String, List<String>> stringListMap = mediaHit.highlight();
                    Set<String> stringSet = stringListMap.keySet();
                    for (String key: stringSet) {
                        if(key.equals("name")) {
                            media.setName(stringListMap.get(key).get(0));
                        }else if (key.equals("author")) {
                            media.setAuthor(stringListMap.get(key).get(0));
                        }else if (key.equals("album")) {
                            media.setAlbum(stringListMap.get(key).get(0));
                        }
                    }
                }
                songList.add(media);
            }
            return queryReturnVo;
        }
        return null;
    }

    @Override
    public void insertSinger(Singer singer) {
        IndexRequest<Object> singerIndexRequest = IndexRequest.of(iqo -> iqo.index("singer").id(singer.getId().toString()).document(singer));
        try {
            elasticsearchClient.index(singerIndexRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Singer> queryByCategory(Map<String, String> categoryIdMap) {
        List<Query> queryList = new ArrayList<>();
        Set<String> stringSet = categoryIdMap.keySet();
        List<FieldValue> fieldValueList = new ArrayList<>();
        for (String key: stringSet) {
            Long categoryId = Long.valueOf(categoryIdMap.get(key));
            FieldValue fieldValue = FieldValue.of(categoryId);
            fieldValueList.add(fieldValue);
            Query query = Query.of(q -> q.nested(nq -> {
                nq.path("categoryList");
                nq.query(q2 -> q2.bool(bq -> bq.must(q3 -> q3.term(tq -> tq.field("categoryList.id").value(categoryId)))));
                return nq;
            }));
            queryList.add(query);
        }
        SearchRequest searchRequest = SearchRequest.of(sr -> {
            sr.index("singer");
            sr.query(q -> q.bool(bq -> bq.must(queryList)));
            return sr;
        });
        SearchResponse<Singer> searchResponse = null;
        try {
            searchResponse = elasticsearchClient.search(searchRequest, Singer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Hit<Singer>> hits = searchResponse.hits().hits();
        List<Singer> singerList = new ArrayList<>();
        if(hits.size() > 0) {
            for (Hit<Singer> singerHit: hits) {
                singerList.add(singerHit.source());
            }
        }
        return singerList;
    }

    @Override
    public List<Media> searchSingleSingerSong(Long singerId, QueryVo queryVo) {
        Query singerIdQuery = Query.of(q -> q.nested(nq -> {
            nq.path("singer");
            nq.query(q2 -> q2.bool(bq -> bq.must(q3 -> q3.term(tq -> tq.field("singer.id").value(singerId)))));
            return nq;
        }));
        Query nameQuery = Query.of(q -> q.match(mq -> mq.field("name").query(fv -> fv.stringValue(queryVo.getQueryText()))));
        Query typeQuery = Query.of(q -> q.term(tq -> tq.field("type").value(11)));
        List<Query> queryList = new ArrayList<>();
        queryList.add(singerIdQuery);
        queryList.add(nameQuery);
        queryList.add(typeQuery);
        SearchRequest searchRequest = SearchRequest.of(sr -> {
            sr.index("media");
            sr.query(q -> q.bool(bq -> bq.must(queryList)));
            sr.size(1000);
            sr.sort(so -> so.field(fs -> fs.field("playQuantity").order(SortOrder.Desc)));
            return sr;
        });
        SearchResponse<Media> songSearchResponse = null;
        try {
            songSearchResponse = elasticsearchClient.search(searchRequest, Media.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Hit<Media>> hits = songSearchResponse.hits().hits();
        List<Media> mediaList = new ArrayList<>();
        if(hits.size() > 0) {
            for (Hit<Media> songHit: hits) {
                Media media = new Media();
                BeanUtils.copyProperties(songHit, media);
                mediaList.add(media);
            }
        }
        return mediaList;
    }

    @Override
    public void insertMedia(Media media) {
        IndexRequest<Object> mediaIndexRequest = IndexRequest.of(iro -> iro.index("media").id(media.getId().toString()).document(media));
        try {
            elasticsearchClient.index(mediaIndexRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateMediaUrl(Long mediaId, String path) {
        SearchResponse<Media> search = null;
        try {
            search = elasticsearchClient.search(sr -> {
                sr.index("media");
                sr.query(q -> q.term(tq -> tq.field("_id").value(mediaId)));
                return sr;
            }, Media.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Hit<Media> mediaHit = search.hits().hits().get(0);
        Media media = mediaHit.source();
        media.setMediaUrl(path);
        UpdateRequest<Media, Object> mediaUpdate = UpdateRequest.of(ur -> {
            ur.index("media");
            ur.id(mediaId.toString());
            ur.doc(media);
            return ur;
        });
        try {
            elasticsearchClient.update(mediaUpdate, Media.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
