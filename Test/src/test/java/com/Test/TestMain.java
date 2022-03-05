package com.Test;

import com.Test.test.Person;
import com.Test.test.PingUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> message : messages) {
            Map<String, Object> payload = JSON.parseObject((String) message.get("payload"));
            payload.replace("timestamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));
            jsonArray.add(payload);
        }
        try {
            saveJsonFile(JSON.toJSONString(jsonArray), "D:\\2.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() throws IOException {

        saveJsonFile("123", "D:\\1.json");
    }

    public void saveJsonFile(String message, String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        bufferedWriter.write(message);

        bufferedWriter.flush();
        bufferedWriter.close();

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

    private final static Executor executor = Executors.newCachedThreadPool();//启用多线程

    @Test
//    @RepeatedTest(10)
    public void test3() throws InterruptedException {

        List<Map<String, Object>> result = new ArrayList<>();

        for (int i = 1; i < 255; i++) {
            int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String s = "10.229.36." + finalI;
                    Map<String, String> win = PingUtils.getNetworkStatusByPing("win", "ping " + s + " -n 1 -w 1000");

                    HashMap<String, Object> objectObjectHashMap = new HashMap<>();
                    objectObjectHashMap.put("ip", finalI);
                    objectObjectHashMap.put("delay", win.get("delay"));
                    result.add(objectObjectHashMap);
                }
            });

        }

        List<Map<String, Object>> collect = result.stream().sorted((h1, h2) -> (new BigDecimal(h2.get("ip").toString())).compareTo(new BigDecimal(h1.get("ip").toString()))).collect(Collectors.toList());
        collect.forEach(stringObjectMap -> System.out.println(stringObjectMap.get("ip")));
        log.info(String.valueOf(result.size()));
    }


}
