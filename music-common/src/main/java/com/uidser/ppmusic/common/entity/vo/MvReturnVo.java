package com.uidser.ppmusic.common.entity.vo;

import com.uidser.ppmusic.common.entity.Mv;
import com.uidser.ppmusic.common.entity.Song;
import lombok.Data;

@Data
public class MvReturnVo extends Mv {

    private Song song;

}
