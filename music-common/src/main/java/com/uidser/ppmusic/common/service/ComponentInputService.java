package com.uidser.ppmusic.common.service;


import com.uidser.ppmusic.common.entity.ComponentInput;

import java.util.List;

public interface ComponentInputService {
    List<ComponentInput> getAll();

    List<ComponentInput> getByIds(List<Long> componentInputIds);
}
