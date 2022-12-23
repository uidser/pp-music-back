package com.uidser.ppmusic.common.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class AttributeCommitVo {

    private Long id;
    private String name;
    private Integer showStatus;
    private Integer isDelete;
    private Date createTime;
    private Date updateTime;
    private Long attributeGroupId;
    private Long componentInputId;

}
