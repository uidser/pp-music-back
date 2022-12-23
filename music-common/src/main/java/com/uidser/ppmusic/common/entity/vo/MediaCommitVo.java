package com.uidser.ppmusic.common.entity.vo;

import com.uidser.ppmusic.common.entity.Media;
import lombok.Data;

import java.util.List;

@Data
public class MediaCommitVo extends Media {

    List<AttributeAttributeValueVo> attributeAttributeValueVoList;

}
