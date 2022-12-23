package com.uidser.ppmusic.common.entity;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class Scheduled {

    private Double time;

    @Min(value = 0, message = "必须为正确的排行榜id")
    private Long rankId;

    @Min(value = 0, message = "必须为正确的类型id")
    private Integer type;

    @Min(value = 0, message = "必须为正确的期数id")
    private Integer frequency;

    @Min(value = 0, message = "必须为正确的数字")
    private Integer showLength;

    private Integer mediaType;

}
