package com.uidser.ppmusic.common.entity.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class QueryVo {

    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private Date endTime;
    private String queryText;
    private Integer current;
    private Integer limit;
    private Integer mediaType;

}
