package com.ruoyi.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.system.domain.RemotePoint;
import com.ruoyi.system.redis.service.RedisService;
import com.ruoyi.system.service.Impl.RemotePointServiceImpl;
import com.ruoyi.system.utils.CopyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class springtest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RemotePointServiceImpl remotePointService;

    @Test
    public void S() {
//        QueryWrapper<RemotePoint> queryWrapper = new QueryWrapper();
//        queryWrapper.likeRight("record_date","2021-10-30");
        List<RemotePoint> list = remotePointService.list(null);
//        System.out.println(list.size());
        redisService.setCacheObject("remotePoint", list);
    }

    @Test
    public void S1() {
        // 原始list
        List<RemotePoint> resource = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RemotePoint remotePoint = new RemotePoint();
            remotePoint.setId(i);
            resource.add(remotePoint);
        }

        List<RemotePoint> target = new ArrayList<>();
        target = CopyUtils.deepCopy(resource);

        target.forEach(remotePoint -> {
            remotePoint.setId(remotePoint.getId()+1);
        });

        target.size();

    }



}
