package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.List;

@Data
public class SingerListPage {

    private List<Singer> singerList;
    private List<Singer> lastListenSingerList;
    private List<Category> categoryList;

}
