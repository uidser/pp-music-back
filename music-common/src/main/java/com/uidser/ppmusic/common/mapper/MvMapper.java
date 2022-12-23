package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.vo.MvCommitVo;
import com.uidser.ppmusic.common.entity.vo.MvReturnVo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MvMapper {
    List<MvReturnVo> page(QueryVo queryVo);

    void insert(MvCommitVo mvCommitVo);

    void edit(MvCommitVo mvCommitVo);

    void changeShowStatus(Long mvId, Integer status);

    void batchDelete(@Param("ids") Long[] ids);
}
