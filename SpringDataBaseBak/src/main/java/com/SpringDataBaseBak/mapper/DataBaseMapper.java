package com.SpringDataBaseBak.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashSet;
import java.util.List;

@Mapper
public interface DataBaseMapper {

    @Select("show databases")
    public HashSet<String> showDataBases();

}
