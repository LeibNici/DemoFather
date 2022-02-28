package com.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author chenming
 * @description
 * @create: 2022-02-28
 */
@SpringBootTest
@Slf4j
public class TestMain {

    @Test
    public void test1() {
        File file = new File("D:\\mqtt.json");
        String json = readJsonFile(file);
        Map<String, Object> jsonObject = JSON.parseObject(json);
        List<Map<String, Object>> messages = (List<Map<String, Object>>) jsonObject.get("messages");
        for (Map<String, Object> message : messages) {
            log.info(String.valueOf(message.get("payload")));
        }
    }

    public String readJsonFile(File jsonFile) {
        String jsonStr = "";
        try {
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
