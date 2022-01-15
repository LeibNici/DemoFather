package com.springtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.springtest.domain.BusCarOperation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusCarOperationMapper extends BaseMapper<BusCarOperation> {
}
