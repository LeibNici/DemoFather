package com.springtest.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springtest.domain.BusCarOperation;
import com.springtest.mapper.BusCarOperationMapper;
import com.springtest.service.BusCarOperationService;
import org.springframework.stereotype.Service;

@Service
public class BusCarOperationServiceImpl extends ServiceImpl<BusCarOperationMapper,BusCarOperation> implements BusCarOperationService {
}
