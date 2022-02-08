package com.maintest;

import com.SpringDataBaseBak.DataBaseBakApplication;
import com.SpringDataBaseBak.domain.InCludeDataBase;
import com.SpringDataBaseBak.mapper.DataBaseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author chenming
 * @description
 * @create: 2022-02-08
 */
@SpringBootTest(classes = DataBaseBakApplication.class)
public class mainTest {

    @Autowired
    private DataBaseMapper dataBaseMapper;

    @Autowired
    private InCludeDataBase inCludeDataBase;

    @Test
    public void test() {
        if (inCludeDataBase.getIncludeOrExclude().equals("exclude")){
            System.out.println("123");
        }
    }

}
