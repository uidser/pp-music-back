package com.uidser.ppmusic.search.entity;

import com.uidser.ppmusic.common.entity.Media;
import com.uidser.ppmusic.common.entity.Singer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueryReturnVo {

    private List<Media> songList = new ArrayList<>();
    private Singer singer;
    private List<Media> mvList = new ArrayList<>();

}
