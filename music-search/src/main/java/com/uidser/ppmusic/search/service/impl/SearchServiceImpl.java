package com.uidser.ppmusic.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
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
        Set<String> stringSet = categoryIdMap.keySet();
        List<FieldValue> fieldValueList = new ArrayList<>();
        for (String key: stringSet) {
            Long categoryId = Long.valueOf(categoryIdMap.get(key));
            FieldValue fieldValue = FieldValue.of(categoryId);
            fieldValueList.add(fieldValue);
        }
        SearchRequest searchRequest = SearchRequest.of(sr -> sr.query(q -> q.nested(nq -> {
            nq.path("categoryList");
            nq.query(q2 -> q2.bool(bq -> bq.must(q3 -> q3.terms(tq -> tq.field("categoryList.id").terms(tqf -> tqf.value(fieldValueList))))));
            return nq;
        })));
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
}
