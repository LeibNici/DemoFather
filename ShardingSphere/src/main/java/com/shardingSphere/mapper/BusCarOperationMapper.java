package com.shardingSphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shardingSphere.domain.BusCarOperation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 车辆运行记录Mapper接口
 *
 * @author mxg
 * @date 2021-02-23
 */
@Mapper
public interface BusCarOperationMapper extends BaseMapper<BusCarOperation>
{

}
