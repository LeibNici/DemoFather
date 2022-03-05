package com.Test.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenming
 * @description
 * @create: 2022-03-04
 */
@Slf4j
public class PingUtils {

    /**
     * 通过ip获取信息,loss:丢包率，delay:延时
     *
     * @param cmd
     * @return
     */
    public static Map<String, String> getNetworkStatusByPing(String os, String cmd) {
        Map<String, String> networkMap = new HashMap<>(3);
        //获取当前进程运行对象
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        String line = null;
        InputStream inputStream = null;
        InputStreamReader isReader = null;
        BufferedReader reader = null;

        String loss = "";//丢包率
        String delay = "";//延时
        try {
            process = runtime.exec(cmd);
            inputStream = process.getInputStream();//实例化输入流
            isReader = new InputStreamReader(inputStream, "GB2312");
            reader = new BufferedReader(isReader);
            StringBuffer buffer = new StringBuffer();
            if (os.contains("win") || os.contains("Win") || os.contains("WIN")) {//Windows系统执行结果解析
                while ((line = reader.readLine()) != null) {
                    //丢包率
                    if (line.contains("%")) {
                        loss = line.substring(line.lastIndexOf("=") + 1, line.indexOf("%") + 1);
                        if (loss.contains("(")) {
                            loss = loss.substring(loss.indexOf("(") + 1).trim();
                        }
                    }
                    //网络延时
                    if ((line.contains(",") || line.contains("，")) && line.contains("=") && line.contains("ms")) {
                        delay = line.substring(line.lastIndexOf("=") + 1, line.lastIndexOf("ms") + 2).trim();
                    }
                }
            } else {//Linux系统执行结果解析
                while ((line = reader.readLine()) != null) {
                    //丢包率
                    if (line.contains("%")) {
                        String[] msg = null;
                        if (line.contains(",")) {
                            msg = line.split(",");
                        } else if (line.contains("，")) {
                            msg = line.split("，");
                        }
                        if (msg.length > 0) {
                            loss = msg[2].substring(0, msg[2].indexOf("%") + 1).trim();
                        }
                    }
                    //网络延时
                    if (line.contains("/")) {
                        String[] msg = line.split("=");
                        String[] names = msg[0].split("/");
                        String[] values = msg[1].split("/");
                        for (int i = 0; i < names.length; i++) {
                            String str = names[i];
                            if ("avg".equalsIgnoreCase(str)) {
                                delay = values[i];
                                break;
                            }
                        }
                    }
                }
            }

            if (StringUtils.isNotEmpty(loss)) {
                networkMap.put("loss", loss);
            } else {
                networkMap.put("loss", "0%");
            }
            if (StringUtils.isNotEmpty(delay)) {
                networkMap.put("delay", delay);
            } else {
                networkMap.put("delay", "99999ms");
                networkMap.put("status", "-1");//无法ping通
            }

            inputStream.close();
            isReader.close();
            reader.close();
        } catch (IOException e) {
            log.error("通过ping方式获取网络信息异常：" + e.getMessage());
            e.printStackTrace();
        }
        return networkMap;
    }


}
