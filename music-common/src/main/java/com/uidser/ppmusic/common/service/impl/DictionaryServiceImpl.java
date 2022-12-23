package com.uidser.ppmusic.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Dictionary;
import com.uidser.ppmusic.common.entity.vo.DictionaryVo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.mapper.DictionaryMapper;
import com.uidser.ppmusic.common.service.DictionaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private DictionaryMapper dictionaryMapper;

    @Override
    public PageInfo<DictionaryVo> page(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getCurrent(), queryVo.getLimit());
        List<Dictionary> dictionaryList = dictionaryMapper.getAll(queryVo);
        List<DictionaryVo> dictionaryVoList = new ArrayList<>();
        this.packageDictionaryVo(dictionaryList, dictionaryVoList);
        return new PageInfo<>(dictionaryVoList);
    }

    @Override
    public void insert(Dictionary dictionary) {
        dictionary.setCreateTime(new Date());
        dictionaryMapper.insert(dictionary);
    }

    @Override
    public void edit(Dictionary dictionary) {
        dictionary.setUpdateTime(new Date());
        dictionaryMapper.edit(dictionary);
    }

    @Override
    public Dictionary getById(Long id) {
        return dictionaryMapper.getById(id);
    }

    private void packageDictionaryVo(List<Dictionary> dictionaryList, List<DictionaryVo> dictionaryVoList) {
        for (Dictionary dictionary: dictionaryList) {
            if(dictionary.getParentId() == 0) {
                DictionaryVo dictionaryVo = new DictionaryVo();
                BeanUtils.copyProperties(dictionary, dictionaryVo);
                dictionaryVo.setParentId(0L);
                dictionaryVoList.add(dictionaryVo);
                this.recursive(dictionaryVo, dictionaryList);
            }
        }
    }

    private void recursive(DictionaryVo dictionaryVo, List<Dictionary> dictionaryList) {
        List<DictionaryVo> dictionaryVoList = new ArrayList<>();
        for (Dictionary dictionary: dictionaryList) {
            if(dictionary.getParentId() == dictionaryVo.getId()) {
                DictionaryVo dictionaryVo1 = new DictionaryVo();
                BeanUtils.copyProperties(dictionary, dictionaryVo1);
                dictionaryVo1.setParentId(dictionaryVo.getId());
                dictionaryVo1.setId(dictionary.getId());
                dictionaryVoList.add(dictionaryVo1);
                dictionaryVo.setChildren(dictionaryVoList);
                this.recursive(dictionaryVo1, dictionaryList);
            }
        }
    }
}
