package com.cm.video;

import com.alibaba.fastjson.JSON;
import com.cm.video.service.WebsocketService;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author chenming
 * @description
 * @create: 2022-05-06
 */
@SpringBootApplication
@Slf4j
public class VideoApplication {

    private static FFmpegFrameGrabber grabber;

    private static boolean isStart = false;

    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class);
//        push();
    }

    public static FFmpegFrameGrabber createGrabber(String rtsp) {
        // 获取视频源
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(rtsp);
        grabber.setOption("rtsp_transport", "tcp");
        //设置帧率
        grabber.setFrameRate(30);
        //设置获取的视频宽度
        grabber.setImageWidth(960);
        //设置获取的视频高度
        grabber.setImageHeight(720);
        //设置视频bit率
        grabber.setVideoBitrate(3000000);
        return grabber;
    }

    public static void push() {
        Java2DFrameConverter converter = new Java2DFrameConverter();
        try {
            grabber = createGrabber("rtsp://admin:admin123456@10.61.208.153:554/h264/ch1/main/av_stream");
            grabber.start();
            while (true) {
                try {
                    Frame frame = grabber.grabImage();
                    if (frame == null) {
                        grabber.restart();
                        continue;
                    }
                    BufferedImage bufferedImage = converter.getBufferedImage(frame);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "jpg", stream);
                    byte[] bytes = stream.toByteArray();
                    BASE64Encoder encoder = new BASE64Encoder();
                    String trim = "data:image/png;base64," + encoder.encode(bytes).trim();
                    for (String user : WebsocketService.getWebsocketMap().keySet()) {
                        log.info(trim);
                        WebsocketService.send(user, trim);
                    }
                } catch (Exception e) {
                    continue;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
