package com.cm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cm.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

//    public int insert(User user);
}
