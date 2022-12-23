package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Attribute {

    private Long id;
    private Long attributeValueId;
    private String name;
    private Integer showStatus;
    private Integer isDelete;
    private Date createTime;
    private Date updateTime;
    private Long componentInputId;
    private String inputName;

}
