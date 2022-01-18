package com.springMybatisPlus.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springMybatisPlus.domain.Child;
import com.springMybatisPlus.domain.Father;
import com.springMybatisPlus.mapper.ChildMapper;
import com.springMybatisPlus.mapper.FatherMapper;
import org.springframework.stereotype.Service;

@Service
public class ChildServiceImpl extends ServiceImpl<ChildMapper, Child> implements ChildService {
}
