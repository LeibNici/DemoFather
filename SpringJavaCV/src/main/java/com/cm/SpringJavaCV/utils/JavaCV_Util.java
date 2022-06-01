package com.cm.SpringJavaCV.utils;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameGrabber;
import org.springframework.stereotype.Component;

/**
 * @author chenming
 * @description
 * @create: 2022-06-01
 */
@Component
@Slf4j
public class JavaCV_Util {

    private static FFmpegFrameGrabber grabber = null; // 视频抓取
    private FFmpegFrameRecorder recorder = null; // 视频解码

    //    rtsp://admin:admin123456@10.61.208.152:554/h264/ch1/main/av_stream
    private static String rtsp = "rtsp://admin:admin123456@10.61.208.151:554/h264/ch1/main/av_stream";
    //    rtmp://10.51.77.88:1935/hls/2
    private String rtmp = "rtmp://10.51.77.88:1935/hls/2";

    public static void from() throws FrameGrabber.Exception {
        log.info(rtsp);
        grabber = new FFmpegFrameGrabber(rtsp);
        grabber.setOption("timeout", "3000000");
        grabber.start();

        int imageHeight = grabber.getImageHeight();
        int imageWidth = grabber.getImageWidth();

        log.info("imageHeight:{},imageWidth:{}", imageHeight, imageWidth);

        if (imageHeight == 0 && imageWidth == 0) {
            System.out.println("超时");
        }

        grabber.close();

    }

}
