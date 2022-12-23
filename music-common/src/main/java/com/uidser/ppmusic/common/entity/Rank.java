package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Rank {

    private Long id;
    private String title;
    private String introducation;
    private Integer showLength;
    private String name;
    private Integer type;
    private String sort;
    private Double rankTime;
    private String profilePicture;
    private Date rankUpdateTime;
    private Date createTime;
    private Date updateTime;
    private Integer isDelete;
    private Integer showStatus;
    private Integer rankFrequency;
    private Long rankListenQuantity;
    private List<Long> previewMediaIdList = new ArrayList<>();
    private List<Long> rankMediaIdList = new ArrayList<>();
    private List<Media> mediaList = new ArrayList<>();

}
