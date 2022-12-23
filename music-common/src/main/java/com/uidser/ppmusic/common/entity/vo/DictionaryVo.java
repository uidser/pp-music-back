package com.uidser.ppmusic.common.entity.vo;

import com.uidser.ppmusic.common.entity.Dictionary;
import lombok.Data;

import java.util.List;

@Data
public class DictionaryVo extends Dictionary {

    private List<DictionaryVo> children;

}
