package com.cm.video;

import com.cm.video.service.WebsocketService;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootTest
@Slf4j
public class JavavcCameraTest {

    @Test
    void test1() throws IOException {
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber("rtsp://admin:admin123456@10.61.208.152:554/h264/ch1/main/av_stream");
        fFmpegFrameGrabber.setOption("rtsp_transport", "tcp");
        fFmpegFrameGrabber.setVideoBitrate(3000000);
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("D:\\1.mp4", 960, 720, 1);
        recorder.setFrameRate(30);
        recorder.setVideoBitrate(3000000);
        fFmpegFrameGrabber.start();
        recorder.start();
        Frame frame = null;
        boolean isStop = true;
        int count = 0;
        while (isStop && (frame = fFmpegFrameGrabber.grabFrame()) != null) {
            log.info(String.valueOf(count));
            recorder.record(frame);
            if (count++ > 200) {
                isStop = false;
            }
        }
        recorder.stop();
        fFmpegFrameGrabber.stop();
    }

    @Test
    void test2() throws IOException {
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber("rtsp://admin:admin123456@10.61.208.152:554/h264/ch1/main/av_stream");
        fFmpegFrameGrabber.setOption("rtsp_transport", "tcp");
        fFmpegFrameGrabber.setVideoBitrate(3000000);
        fFmpegFrameGrabber.start();
        while (true) {
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage convert = converter.convert(fFmpegFrameGrabber.grabFrame());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(convert, "jpg", stream);
            byte[] bytes = stream.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            String trim = "data:image/png;base64," + encoder.encode(bytes).trim();
            stream.close();
            WebsocketService.send("123", trim);
            log.info(trim);
        }
    }

    @Test
    void test3() throws IOException {
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber("rtsp://admin:admin123456@10.61.208.152:554/h264/ch1/main/av_stream");
        fFmpegFrameGrabber.setOption("rtsp_transport", "tcp");
        fFmpegFrameGrabber.setVideoBitrate(3000000);
        fFmpegFrameGrabber.start();
        Java2DFrameConverter converter = new Java2DFrameConverter();

    }

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    @Test
    void test4() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(simpleDateFormat.format(new Date()));
                    Thread.sleep(3000);
                    System.out.println(simpleDateFormat.format(new Date()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "123");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                thread.start();
            }
        },0);
        Thread.sleep(1000000);
    }

    @Test
    public void test5(){
        log.info("123");
    }

}
