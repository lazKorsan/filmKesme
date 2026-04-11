package birlestirme;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class videoMix3 {

    public static void main(String[] args) {
        try {
            String video1 = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\video1.mp4";
            String video2 = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\video2.mp4";

            // Geçici dosyalar için
            String tempVideo1 = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\temp_video1.mp4";
            String tempVideo2 = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\temp_video2.mp4";
            String cikisDosyasi = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\birlesmis_video.mp4";

            FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg.exe");
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);

            System.out.println("🎬 Adım 1/3: Videolar standartlaştırılıyor...");

            // Önce her iki videoyu da aynı özelliklere dönüştür
            FFmpegBuilder builder1 = new FFmpegBuilder()
                    .setInput(video1)
                    .overrideOutputFiles(true)
                    .addOutput(tempVideo1)
                    .setVideoCodec("libx264")
                    .setAudioCodec("aac")
                    .setVideoFrameRate(30)
                    .setVideoResolution(1920, 1080)
                    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                    .done();
            executor.createJob(builder1).run();

            FFmpegBuilder builder2 = new FFmpegBuilder()
                    .setInput(video2)
                    .overrideOutputFiles(true)
                    .addOutput(tempVideo2)
                    .setVideoCodec("libx264")
                    .setAudioCodec("aac")
                    .setVideoFrameRate(30)
                    .setVideoResolution(1920, 1080)
                    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                    .done();
            executor.createJob(builder2).run();

            System.out.println("🎬 Adım 2/3: Videolar birleştiriliyor...");

            // Liste dosyası oluştur
            File listFile = new File("C:\\Users\\user\\Desktop\\video_kesmekliklik\\liste.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(listFile))) {
                writer.write("file '" + tempVideo1.replace("\\", "/") + "'");
                writer.newLine();
                writer.write("file '" + tempVideo2.replace("\\", "/") + "'");
            }

            // Birleştir
            FFmpegBuilder builder3 = new FFmpegBuilder()
                    .setInput(listFile.getAbsolutePath())
                    .setFormat("concat")
                    .addExtraArgs("-safe", "0")
                    .overrideOutputFiles(true)
                    .addOutput(cikisDosyasi)
                    .setVideoCodec("libx264")
                    .setAudioCodec("aac")
                    .addExtraArgs("-c", "copy")
                    .done();
            executor.createJob(builder3).run();

            System.out.println("🎬 Adım 3/3: Geçici dosyalar temizleniyor...");

            // Temizlik
            listFile.delete();
            new File(tempVideo1).delete();
            new File(tempVideo2).delete();

            System.out.println("✅ Başarılı! Yeni video: " + cikisDosyasi);

        } catch (IOException e) {
            System.err.println("❌ Hata: " + e.getMessage());
            e.printStackTrace();
        }
    }
}