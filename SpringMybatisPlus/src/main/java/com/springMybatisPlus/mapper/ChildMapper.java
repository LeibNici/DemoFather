package com.springMybatisPlus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springMybatisPlus.domain.Child;
import com.springMybatisPlus.domain.Father;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChildMapper extends BaseMapper<Child> {
}
