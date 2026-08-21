package birlestirme;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class videoMix6 {

    public static void main(String[] args) {
        try {
            // 1. Birleştirilecek videoların yolları (appiumDersleri klasörü)
           // String video1 = "C:\\Users\\user\\Desktop\\appiumDersleri\\loginPart1.mp4";
            String video2 = "C:\\Users\\user\\Desktop\\appiumDersleri\\loginPart2.mp4";
            String video3 = "C:\\Users\\user\\Desktop\\appiumDersleri\\loginPart3.mp4";
            String video4 = "C:\\Users\\user\\Desktop\\appiumDersleri\\loginPart4.mp4";
            String cikisDosyasi = "C:\\Users\\user\\Desktop\\appiumDersleri\\birlesmis_video.mp4";

            // 2. FFmpeg yolu
            FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg.exe");

            // 3. Geçici liste dosyası oluştur (tüm videoları ekle)
            File listFile = new File("C:\\Users\\user\\Desktop\\appiumDersleri\\liste.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(listFile))) {
              //  writer.write("file '" + video1.replace("\\", "/") + "'");
               // writer.newLine();
                writer.write("file '" + video2.replace("\\", "/") + "'");
                writer.newLine();
                writer.write("file '" + video3.replace("\\", "/") + "'");
                writer.newLine();
                writer.write("file '" + video4.replace("\\", "/") + "'");
            }

            System.out.println("🎬 4 video birleştiriliyor (yeniden kodlanıyor)...");
            System.out.println("Bu işlem birkaç dakika sürebilir...");

            // 4. BİRLEŞTİRME - YENİDEN KODLAMA İLE
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(listFile.getAbsolutePath())
                    .setFormat("concat")
                    .addExtraArgs("-safe", "0")
                    .overrideOutputFiles(true)
                    .addOutput(cikisDosyasi)
                    .setVideoCodec("libx264")     // H.264 video codec
                    .setAudioCodec("aac")         // AAC audio codec
                    .setVideoFrameRate(30)         // Sabit FPS
                    .setVideoResolution(1920, 1080) // Sabit çözünürlük
                    .setAudioBitRate(128000)       // 128k bitrate
                    .setVideoBitRate(2500000)      // 2.5M bitrate
                    .addExtraArgs("-preset", "medium") // Hız/kalite dengesi
                    .addExtraArgs("-crf", "23")    // Kalite (18-28 arası)
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            executor.createJob(builder).run();

            // 5. Geçici dosyayı sil
            if (listFile.exists()) {
                listFile.delete();
            }

            System.out.println("✅ Başarılı! Birleştirilmiş video burada: " + cikisDosyasi);

        } catch (IOException e) {
            System.err.println("❌ Bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}