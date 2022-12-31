package com.uidser.ppmusic.common.entity;

import com.uidser.ppmusic.common.entity.vo.AttributeAttributeValueVo;
import com.uidser.ppmusic.common.validate.Delete;
import com.uidser.ppmusic.common.validate.Insert;
import com.uidser.ppmusic.common.validate.Update;
import com.uidser.ppmusic.common.validate.annocation.DateValid;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Validated
public class Media {

    @NotBlank(message = "id不能为空", groups = {Update.class, Delete.class})
    @Null(message = "id必须为空", groups = {Insert.class})
    private Long id;

    @NotBlank(message = "媒体名不能为空", groups = {Insert.class})
    private String name;

    @NotBlank(message = "歌手名不能为空", groups = {Insert.class})
    private String author;

    private String mediaUrl;

    @NotEmpty(message = "歌手id不能为空", groups = {Insert.class})
    private List<Long> singerIdList;

    @NotBlank(message = "专辑不能为空", groups = {Insert.class})
    private String album;

    @NotNull(message = "专辑id不能为空", groups = {Insert.class})
    @Min(value = 0 ,message = "专辑id不能小于零", groups = {Insert.class})
    private Long albumId;

    private Long favoriteQuantity;

    private String mediaProfilePictureImg;

    @DateValid(message = "发行时间不能为空", groups = {Insert.class})
    private Date publishDate;

    private String mediaTime;

    private Long mvId;

    private Integer type;

    private Integer isHaveMv;

    private Date createTime;

    private Date updateTime;

    private String word;

    private String mv;

    private Integer isDelete;

    private Integer showStatus;

    @Future(message = "与发布时间必须大于当前时间", groups = {Insert.class})
    private Date prePublishDate;

    private Long playQuantity;

    private List<MediaSingerESModel> singer = new ArrayList<>();
    
}
