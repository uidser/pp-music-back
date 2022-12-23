package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.ComponentInput;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ComponentInputMapper {
    List<ComponentInput> getAll();

    List<ComponentInput> getByIds(@Param("ids") List<Long> componentInputIds);
}
