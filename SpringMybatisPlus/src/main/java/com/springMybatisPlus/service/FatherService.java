package com.springMybatisPlus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springMybatisPlus.domain.Father;

import java.util.List;

public interface FatherService extends IService<Father> {

    public List<Father> selectFatherDetails();

}
