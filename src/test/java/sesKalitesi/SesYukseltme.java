package sesKalitesi;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class SesYukseltme {

    public static void main(String[] args) {
        // Giriş ve çıkış dosya yolları
        String girisDosyasi = "C:\\Users\\user\\Desktop\\film\\appiumKurulum_dusuk_ses.mp4";
        String cikisDosyasi = "C:\\Users\\user\\Desktop\\film\\appiumKurulum.mp4";

        System.out.println(System.currentTimeMillis());
        try {
            // FFmpeg yolunu belirt
            FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg.exe");

            System.out.println("🎤 Ses iyileştirme başlıyor...");
            System.out.println("📁 Giriş: " + girisDosyasi);
            System.out.println("📁 Çıkış: " + cikisDosyasi);

            // 1. YÖNTEM: Basit ses yükseltme (volume filtresi)
            // Ses seviyesini 3 katına çıkarır (volume=5)
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(girisDosyasi)
                    .overrideOutputFiles(true)
                    .addOutput(cikisDosyasi)
                    .setVideoCodec("libx264")                                          // Video codec'i koru
                    .setAudioCodec("aac")                                              // Ses codec'i
                    .setAudioBitRate(192000)                                           // Ses bitrate'i
                    .addExtraArgs("-filter:a", "volume=15.0")                   // Ses seviyesini 5 katına çıkar
                    .addExtraArgs("-preset", "medium")
                    .addExtraArgs("-crf", "23")
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            executor.createJob(builder).run();

            System.out.println("✅ İşlem tamamlandı!");
            System.out.println("🎬 Çıktı dosyası: " + cikisDosyasi);

        } catch (Exception e) {
            System.err.println("❌ Bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

}