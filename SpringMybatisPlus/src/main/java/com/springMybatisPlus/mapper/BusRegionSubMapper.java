package com.springMybatisPlus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springMybatisPlus.domain.BusRegionSub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 子区域Mapper接口
 *
 * @author ruoyi
 * @date 2022-03-16
 */
@Mapper
public interface BusRegionSubMapper extends BaseMapper<BusRegionSub> {

    @Select("select * from safea")
    List<Object> ss();

}
