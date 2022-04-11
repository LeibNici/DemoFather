package com.cm.video;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @author chenming
 * @description
 * @create: 2022-04-08
 */
@SpringBootApplication
public class VideoApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(VideoApplication.class);
        new RecordVideo().sourcesRtsp("rtsp://admin:admin123456@10.61.208.152:554/h264/ch1/main/av_stream")
                .target("udp://localhost:8080")
                .go();
    }

}
