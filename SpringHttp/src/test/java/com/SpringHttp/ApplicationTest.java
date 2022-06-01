package com.SpringHttp;

import com.SpringHttp.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author chenming
 * @description
 * @create: 2022-05-05
 */
@SpringBootTest
@Slf4j
public class ApplicationTest {

    @Test
    void test1() throws IOException {
        JSONObject jsonObject = JSON.parseObject("{\"username\":\"admin\",\"password\":\"admin123\",\"uuid\":\"c85c8526251942c2bb63a7bbce00b2db\"}");
        Connection.Response post = HttpUtils.post("http://10.229.36.193:1024/dev-api/auth/login", jsonObject.toJSONString());
        post.body();
        log.info("123");
    }

}
