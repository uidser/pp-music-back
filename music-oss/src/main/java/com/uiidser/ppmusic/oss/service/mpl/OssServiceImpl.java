package com.uiidser.ppmusic.oss.service.mpl;

import com.google.gson.Gson;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.uidser.ppmusic.common.feign.SearchFeignService;
import com.uidser.ppmusic.common.service.MediaService;
import com.uiidser.ppmusic.oss.service.OssService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

@Service
public class OssServiceImpl implements OssService {

    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey;

    @Value("${bucked}")
    private String bucked;

    @Value("${url}")
    private String url;

    @Resource
    private MediaService mediaService;

    @Value("${callback}")
    private String callbackUrl;

    @Resource
    private SearchFeignService searchFeignService;
    @Override
    public String getUploadToken() {
        Gson gson = new Gson();
        StringMap policy = new StringMap();
        policy.put("callbackUrl", callbackUrl);
        policy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"mediaId\":$(x:mediaId),\"type\":$(x:type),\"column\":$(x:column),\"fsize\":$(fsize)}");
        policy.put("callbackBodyType", "application/json");
        long expireSeconds = 3600;
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucked, null, expireSeconds, policy);
    }

    @Override
    public void callback(HttpServletRequest httpServletRequest, Map map) throws IOException {
        Auth auth = Auth.create(accessKey, secretKey);
        String authorization = httpServletRequest.getHeader("Authorization");
        byte[] body = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(map);
        body = outputStream.toByteArray();
        Boolean valid = auth.isValidCallback(authorization, callbackUrl, body, "application/json");
        if(valid) {
            map.put("url", url);
            Long mediaId = Long.valueOf(map.get("mediaId").toString());
            String key = map.get("key").toString();
            String mediaUrl = "http://" + url + "/" + key;
            String column = map.get("column").toString();
            if(column.equals("media_url")) {
                searchFeignService.updateMediaUrl(mediaId, mediaUrl);
            }
            mediaService.editMediaUrl(mediaId, mediaUrl, column);
        } else {

        }
    }
}
