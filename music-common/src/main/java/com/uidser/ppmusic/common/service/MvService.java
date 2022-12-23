package com.uidser.ppmusic.common.service;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.vo.MvCommitVo;
import com.uidser.ppmusic.common.entity.vo.MvReturnVo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

public interface MvService {
    PageInfo<MvReturnVo> page(QueryVo queryVo);

    MvReturnVo getById(Long id);

    void insert(MvCommitVo mvCommitVo);

    void edit(MvCommitVo mvCommitVo);

    void changeShowStatus(Long mvId, Integer status);

    void batchDelete(Long[] ids);
}
