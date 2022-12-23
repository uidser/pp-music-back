package com.uidser.ppmusic.common.resposneutil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uidser.ppmusic.common.r.R;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {

    public static void response(HttpServletResponse response, R r) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), r);
    }

}
