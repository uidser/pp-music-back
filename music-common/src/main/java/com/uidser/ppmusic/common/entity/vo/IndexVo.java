package com.uidser.ppmusic.common.entity.vo;

import com.uidser.ppmusic.common.entity.Media;
import com.uidser.ppmusic.common.entity.Rank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IndexVo {

    private List<Media> newSongList = new ArrayList<>();
    private List<Media> mvList = new ArrayList<>();
    private List<Media> firstBrowsePlayList = new ArrayList<>();
    private List<Rank> rankList = new ArrayList<>();
//    private List<Rotation> rotationList;

}
