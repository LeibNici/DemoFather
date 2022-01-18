package com.springMybatisPlus.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springMybatisPlus.domain.Father;
import com.springMybatisPlus.mapper.FatherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FatherServiceImpl extends ServiceImpl<FatherMapper, Father> implements FatherService {

    @Autowired
    private FatherMapper fatherMapper;

    @Override
    public List<Father> selectFatherDetails() {
        return fatherMapper.selectFatherDetails();
    }
}
