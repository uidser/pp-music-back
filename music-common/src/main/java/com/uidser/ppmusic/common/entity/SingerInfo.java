package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.List;

@Data
public class SingerInfo extends Singer{

    private List<Media> songList;
    private List<Media> mvList;
    private List<Album> albumList;
    private List<Media> recommendVideoList;
    //TODO 成就
//    private List<Honer> honerList;

}
