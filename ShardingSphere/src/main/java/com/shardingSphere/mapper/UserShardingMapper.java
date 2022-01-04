package com.shardingSphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shardingSphere.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserShardingMapper extends BaseMapper<User> {

}
