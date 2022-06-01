package com.cm.video.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenming
 * @description
 * @create: 2022-05-26
 */
@RestController
public class VideoController {
    Map<Integer, Process> map = new HashMap<>();

    @GetMapping("/push")
    public String push() throws IOException {
//        start "3333" /i D:\Tool\ffmpeg-4.4-essentials_build\bin\ffmpeg.exe -i rtsp://admin:admin123456@10.61.208.152:554/h264/ch1/main/av_stream -vcodec copy -acodec copy -f flv rtmp://10.51.77.88:1935/hls/2
        String cmd = "D:\\Tool\\ffmpeg-4.4-essentials_build\\bin\\ffmpeg.exe -i rtsp://admin:admin123456@10.61.208.152:554/h264/ch1/main/av_stream -vcodec copy -acodec copy -f flv rtmp://10.51.77.88:1935/hls/2";
        List<String> cmdList = new ArrayList<>();
        cmdList.add("cmd");
        cmdList.add("/c");
        cmdList.add("start");
        cmdList.add("\"123\"");
        cmdList.add("cmd.exe");
        cmdList.add("/c");
        cmdList.add(cmd);
        ProcessBuilder processBuilder = new ProcessBuilder(cmdList);
        Process process = processBuilder.start();
        map.put(1, process);
        return "ok";
    }

    @GetMapping("/destroy")
    public String directory() throws IOException {
//        taskkill /f /t /fi "windowtitle eq 3333"
        List<String> cmdList = new ArrayList<>();
        cmdList.add("taskkill");
        cmdList.add("/f");
        cmdList.add("/t");
        cmdList.add("/fi");
        cmdList.add("\"windowtitle eq 123\"");
        ProcessBuilder processBuilder = new ProcessBuilder(cmdList);
        Process process = processBuilder.start();
        return "ok";
    }

    @GetMapping("/status")
    public String status() {
        return String.valueOf(map.get(1).isAlive());
    }

}
