package com.cm.SpringJavaCV;

import com.cm.SpringJavaCV.utils.JavaCV_Util;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameGrabber;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author chenming
 * @description
 * @create: 2022-06-01
 */
@SpringBootTest
@Slf4j
public class JavaCV_Test {

    private static FFmpegFrameGrabber grabber = null; // 视频抓取
    private FFmpegFrameRecorder recorder = null; // 视频解码

    //    rtsp://admin:admin123456@10.61.208.152:554/h264/ch1/main/av_stream
    private String rtsp = "rtsp://admin:admin123456@10.61.208.153:554/h264/ch1/main/av_stream";
    //    rtmp://10.51.77.88:1935/hls/2
    private String rtmp = "rtmp://10.51.77.88:1935/hls/2";

    @Test
    public void test1() {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(rtsp);
        grabber.setOption("timeout", "3000000");
        grabber.setOption("rtsp_transport", "tcp");
        try {
            grabber.start();

            int imageHeight = grabber.getImageHeight();
            int imageWidth = grabber.getImageWidth();
            int audioCodec = grabber.getAudioCodec();
            int videoCodec = grabber.getVideoCodec();
            double videoFrameRate = grabber.getVideoFrameRate();
            int videoBitrate = grabber.getVideoBitrate();
            int audioChannels = grabber.getAudioChannels();
            int audioBitrate = grabber.getAudioBitrate();


            log.info("imageHeight:{}," +
                            "imageWidth:{}," +
                            "audioCodec:{}," +
                            "videoCodec:{}," +
                            "videoFrameRate:{}," +
                            "videoBitrate:{}," +
                            "audioChannels:{}," +
                            "audioBitrate:{}",
                    imageHeight,
                    imageWidth,
                    audioCodec,
                    videoCodec,
                    videoFrameRate,
                    videoBitrate,
                    audioChannels,
                    audioBitrate);

            recorder = new FFmpegFrameRecorder(rtmp, imageWidth, imageHeight);
            recorder.setVideoOption("crf", "28");
            recorder.setFrameRate(videoFrameRate);
            recorder.setVideoBitrate(videoBitrate);
            recorder.setAudioChannels(audioChannels);
            recorder.setAudioBitrate(audioBitrate);

            AVFormatContext fc = null;

            recorder.setFormat("flv");
            recorder.setVideoCodec(videoCodec);
            fc = grabber.getFormatContext();

            recorder.start();

        } catch (FFmpegFrameGrabber.Exception | FFmpegFrameRecorder.Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void test2() throws Exception {
        CameraPojo cameraPojo = new CameraPojo();
        cameraPojo.setRtsp(rtsp);
        cameraPojo.setRtmp(rtmp);
        CameraPush cameraPush = new CameraPush(cameraPojo);
        cameraPush.from().to().go(new Thread());

        Thread.sleep(10000000);
    }

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");


    @Test
    void test4() throws InterruptedException {
        Map<Integer, MyThread> map = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            MyThread myThread = new MyThread();
            myThread.setName(String.valueOf(i));
            map.put(i,myThread);
            myThread.start();
        }

        log.info("主线程停止15s");
        Thread.sleep(8000);
        map.get(2).setFlag(false);

        Thread.sleep(1000000);
    }

    @Data
    class MyThread extends Thread {
        private boolean flag = true;
        @SneakyThrows
        public void run() {
            while (flag) {
                System.out.println("Time: " + Thread.currentThread().getName());
                Thread.sleep(1000);
            }
        }
    }

}
