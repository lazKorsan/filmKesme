package videoDuzletme;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import java.util.concurrent.TimeUnit;

public class CorrectTime {
    public static void main(String[] args) {
        String girisDosyasi = "C:\\Users\\user\\Desktop\\duzeltme\\gecerliKayit.mp4";
        String cikisDosyasi = "C:\\Users\\user\\Desktop\\duzeltme\\gecerliKayit_duzeltilmis.mp4";
        String ffmpegYolu = "C:\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg.exe";

        try {
            FFmpeg ffmpeg = new FFmpeg(ffmpegYolu);

            // YÖNTEM 1: Sadece kopyala (en hızlı, süre sorununu genelde çözer)
            System.out.println("Yöntem 1 deneniyor: Sadece kopyalama ile düzeltme...");
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(girisDosyasi)
                    .overrideOutputFiles(true)
                    .addOutput(cikisDosyasi)
                    .setVideoCodec("copy")
                    .setAudioCodec("copy")
                    .setFormat("mp4")
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            executor.createJob(builder).run();

            System.out.println("Video başarıyla düzeltildi!");

        } catch (Exception e) {
            System.out.println("Yöntem 1 başarısız: " + e.getMessage());
            System.out.println("Yöntem 2 deneniyor: Yeniden kodlama ile düzeltme...");

            try {
                FFmpeg ffmpeg = new FFmpeg(ffmpegYolu);

                FFmpegBuilder builder2 = new FFmpegBuilder()
                        .setInput(girisDosyasi)
                        .overrideOutputFiles(true)
                        .addOutput(cikisDosyasi)
                        .setVideoCodec("libx264")
                        .setAudioCodec("aac")
                        .setVideoFrameRate(24, 1)  // FPS: (sayı, payda)
                        // .setTargetSize(100_000)  // İsterseniz hedef dosya boyutu (KB cinsinden)
                        .setFormat("mp4")
                        .done();

                FFmpegExecutor executor2 = new FFmpegExecutor(ffmpeg);
                executor2.createJob(builder2).run();

                System.out.println("Video yeniden kodlanarak düzeltildi!");

            } catch (Exception e2) {
                System.out.println("Duzeltme başarısız: " + e2.getMessage());
                e2.printStackTrace();
            }
        }
    }
}