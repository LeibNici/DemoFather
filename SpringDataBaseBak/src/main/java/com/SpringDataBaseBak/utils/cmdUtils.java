package com.SpringDataBaseBak.utils;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

/**
 * @author chenming
 * @description
 * @create: 2022-02-11
 */
@Component
public class cmdUtils {

    @Async("async")
    public void excute(File file, String cmd) throws IOException, InterruptedException {
        BufferedReader br = null;

        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", cmd);
        pb.redirectErrorStream(true);
        File tmpFile = new File(file.getPath() + "\\temp.tmp");
        pb.redirectOutput(tmpFile);
        pb.start().waitFor();

        InputStream in = new FileInputStream(tmpFile);
        br = new BufferedReader(new InputStreamReader(in));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
        br = null;
        tmpFile.delete();
        System.out.println(Thread.currentThread().getName() + " 执行完成");
    }

}
