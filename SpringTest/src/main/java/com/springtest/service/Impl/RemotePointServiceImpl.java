package com.springtest.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springtest.domain.RemotePoint;
import com.springtest.mapper.RemotePointMapper;
import com.springtest.service.RemotePointService;
import org.springframework.stereotype.Service;

@Service
public class RemotePointServiceImpl extends ServiceImpl<RemotePointMapper, RemotePoint> implements RemotePointService {
}
