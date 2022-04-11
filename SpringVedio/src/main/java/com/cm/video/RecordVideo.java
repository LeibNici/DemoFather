package com.cm.video;

import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;

import java.io.IOException;

import static org.bytedeco.ffmpeg.global.avcodec.av_packet_unref;

/**
 * @author chenming
 * @description
 * @create: 2022-04-08
 */
public class RecordVideo {

    private FFmpegFrameGrabber grabber = null;
    private FFmpegFrameRecorder recorder = null;

    public RecordVideo sourcesRtsp(String src) throws FFmpegFrameGrabber.Exception {
        grabber = MediaUtils.createGrabber(src);
        grabber.start();
        return this;
    }

    /**
     * 选择输出
     * @param out
     * @author eguid
     * @throws IOException
     */
    public RecordVideo target(String out) throws IOException {
        // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制） ?overrun_nonfatal=1&fifo_size=50000000
        //这里udp地址增加参数扩大udp缓存
        recorder = new FFmpegFrameRecorder(out + "?overrun_nonfatal=1&fifo_size=50000000", MediaUtils.FRAME_WIDTH, MediaUtils.FRAME_HEIGHT, 0);
        // 直播流格式
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        // 降低编码延时
        recorder.setVideoOption("tune", "zerolatency");
        recorder.setMaxDelay(500);
        recorder.setGopSize(10);
        // 提升编码速度
        recorder.setVideoOption("preset", "ultrafast");
        // 录制的视频格式 flv(rtmp格式) h264(udp格式) mpegts(未压缩的udp) rawvideo
        recorder.setFormat("h264");
        // 帧数
        double frameLength = grabber.getLengthInFrames();
        long frameTime = grabber.getLengthInTime();
        double v = frameLength * 1000 * 1000 / frameTime;
        recorder.setFrameRate(v);
        //百度翻译的比特率，默认400000
        recorder.setVideoBitrate(200000);
//        recorder.setAudioOption("crf", "23");
        // 建议从grabber获取AudioChannels
//        recorder.setAudioChannels(grabber.getAudioChannels());
//        recorder.setInterleaved(true);
        // yuv420p
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
        recorder.start(grabber.getFormatContext());
        return this;
    }

    /**
     * 转封装
     * @author eguid
     * @throws IOException
     */
    public RecordVideo go() throws IOException {
        System.out.println("开始推送...");
        long err_index = 0;//采集或推流导致的错误次数
        // 释放探测时缓存下来的数据帧，避免pts初始值不为0导致画面延时
        grabber.flush();
        //错误采集判断
        for(int no_frame_index = 0; no_frame_index < 10 || err_index > 1;) {
            AVPacket pkt;
            try {
                pkt = grabber.grabPacket();
                if(pkt == null || pkt.size() <= 0 || pkt.data() == null) {
                    //空包记录次数跳过
                    no_frame_index ++;
                    continue;
                }
                //不需要编码频帧推出去
                err_index += (recorder.recordPacket(pkt) ? 0 : 1);//如果失败err_index自增1
                av_packet_unref(pkt);
            } catch (IOException e) {//推流失败
                err_index++;
            }
        }
        return this;
    }

    public static void main(String[] args) throws Exception, IOException {
        //运行，设置视频源和推流地址
        new RecordVideo().sourcesRtsp("rtsp://admin:admin123456@10.61.208.152:554/h264/ch1/main/av_stream")
                .target("udp://localhost:8822")
                .go();
    }

}
