package com.shardingSphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shardingSphere.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserShardingMapper extends BaseMapper<User> {

    int myInsert(User user);

}
