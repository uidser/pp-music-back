package com.uidser.ppmusic.common.service;

import com.uidser.ppmusic.common.entity.Singer;

import java.util.List;

public interface SingerMediaRelationService {
    void relation(Long id, List<Long> singerIdList);

    List<Long> get(Long id);
}
