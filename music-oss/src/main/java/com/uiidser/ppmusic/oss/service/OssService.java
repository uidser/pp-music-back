package com.uiidser.ppmusic.oss.service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface OssService {
    String getUploadToken();

    void callback(HttpServletRequest authorization, Map map1) throws IOException;
}
