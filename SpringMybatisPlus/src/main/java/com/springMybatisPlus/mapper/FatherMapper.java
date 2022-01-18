package com.springMybatisPlus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springMybatisPlus.domain.Father;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Mapper
public interface FatherMapper extends BaseMapper<Father> {

    public List<Father> selectFatherDetails();

}
