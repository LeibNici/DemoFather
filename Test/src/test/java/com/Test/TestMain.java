package com.Test;

import com.Test.test.Person;
import com.Test.test.PingUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
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
    public void test3() throws InterruptedException {

        List<Map<String, Object>> result = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 1; i < 255; i++) {
            int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String s = "10.229.36." + finalI;
                    Map<String, String> win = PingUtils.getNetworkStatusByPing("win", "ping " + s + " -n 1 -w 99999");

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


    @Test
    public void test4() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(255);

        for (int i = 1; i < 10; i++) {
            String ip = "10.229.36." + i;
            new Thread(new getDelay(ip, latch)).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("main 结束");
    }

    static class getDelay implements Runnable {

        private String ip;
        private CountDownLatch latch;

        public getDelay(String ip, CountDownLatch latch) {
            this.ip = ip;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Map<String, String> win = PingUtils.getNetworkStatusByPing("win", "ping " + ip + " -n 1 -w 99999");
                String delay = win.get("delay");
                log.info("当前ip: {} , 延迟： {}", ip, delay);
            } finally {
                latch.countDown();
            }
        }
    }

    @Test
    public void cleanList() {
        double[] data = new double[]{1, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 15, 99999, 99999};
        List<Double> dataList = new ArrayList<>();
        for (double vo : data) {
            dataList.add(vo);
        }
        List<Double> outliersList = sigma3Filter(dataList);
        System.out.println(JSON.toJSONString(outliersList));

    }

    private List<Double> sigma3Filter(List<Double> data) {
        List<Double> returnList = new ArrayList<>(data);
        double[] dataA = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            dataA[i] = data.get(i);
        }
        List<Double> outliersList = sigma(dataA, dataA, 3);
        for (double vo : outliersList) {
            if (returnList.contains(vo)) {
                returnList.remove(vo);
            }
        }
        return returnList;
    }

    public static List<Double> sigma(double[] data, double[] arr, int x) {

        double avg = StatUtils.mean(data);
        System.out.println("算数平均值μ：" + avg);//算数平均值

        StandardDeviation standardDeviation = new StandardDeviation();
        double stDev = standardDeviation.evaluate(data);
        System.out.println("标准差σ为：" + stDev);

        List<Double> outliersList = new ArrayList<>();
        for (double vo : arr) {
            //判断异常值方法
            System.out.println("gongshi 1 :" + Math.abs(vo - avg));
            System.out.println("gongshi 2 :" + x * stDev);
            if (Math.abs(vo - avg) > (x * stDev)) {
                outliersList.add(vo);
                System.out.println("使用" + x + "σ准则进行过滤，该数组中的" + vo + "属于异常值!");
            }
        }
        return outliersList;
    }

    @Test
    void test5() {
        CountDownLatch latch = new CountDownLatch(10);

        int j = 0;

        for (int i = 0; i < 10; i++) {
            new Thread(new Sout(j, latch)).start();
        }
        try {
            latch.await(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Sout implements Runnable {

        private int num;
        private CountDownLatch latch;

        public Sout(int num, CountDownLatch latch) {
            this.num = num;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(4000);
                log.info("num: {}, date:{}", num + 1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

    @Test
    void test() {
        List<Integer> tasks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            tasks.add(i);
        }
        ExecutorService pool = Executors.newFixedThreadPool(20);

        CountDownLatch latch = new CountDownLatch(tasks.size());

        for (Integer task : tasks) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("task " + task + " end");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                }
            });

        }
    }

    @Test
    void test6() throws UnknownHostException {

        String[] split = "192.168.0.1".replace(".","\\").split("[^\\d]+");
        System.out.println(split.length);
        int a = Integer.valueOf(split[0]);
        int b = Integer.valueOf(split[1]);
        int c = Integer.valueOf(split[2]);
        int d = Integer.valueOf(split[3]);
        double result = a * Math.pow(2, 24) + b * Math.pow(2, 16) + c * Math.pow(2, 8) + d;
        System.out.println(Double.valueOf(result));

    }

    @Test
    void test7() throws UnknownHostException {
        String s = com.cm.PingUtils.ALLATORIxDEMO("111");
        String delay = com.cm.PingUtils.getNetworkStatusByPing("127.0.0.1").get("delay");
        System.out.println(s);
        System.out.println(delay);
    }
}
