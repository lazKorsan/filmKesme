package videocutter;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class VideoMixerDynamic {

    public static void main(String[] args) {
        // 1. Birleştirilecek videoların listesi (İstediğin kadar ekleyebilirsin)
        List<String> videoListesi = Arrays.asList(
                "C:/Users/user/Desktop/video_kesmekliklik/video1.mp4",
                "C:/Users/user/Desktop/video_kesmekliklik/video2.mp4",
                "C:/Users/user/Desktop/video_kesmekliklik/video3.mp4"
        );

        String cikisDosyasi = "C:/Users/user/Desktop/video_kesmekliklik/toplu_birlesmis_video.mp4";
        String ffmpegPath = "C:/ffmpeg-master-latest-win64-gpl/bin/ffmpeg.exe";

        try {
            FFmpeg ffmpeg = new FFmpeg(ffmpegPath);

            // 2. Geçici liste dosyasını listeden oluştur
            File listFile = new File("C:/Users/user/Desktop/video_kesmekliklik/liste.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(listFile))) {
                for (String videoYolu : videoListesi) {
                    writer.write("file '" + videoYolu.replace("\\", "/") + "'");
                    writer.newLine();
                }
            }

            System.out.println("🎬 " + videoListesi.size() + " video birleştiriliyor...");

            // 3. FFmpeg Yapılandırması
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(listFile.getAbsolutePath())
                    .setFormat("concat")
                    .addExtraArgs("-safe", "0")
                    .overrideOutputFiles(true)
                    .addOutput(cikisDosyasi)
                    .setVideoCodec("libx264")
                    .setAudioCodec("aac")
                    .setVideoFrameRate(30)
                    .setVideoResolution(1920, 1080)
                    .addExtraArgs("-preset", "medium")
                    .addExtraArgs("-crf", "23")
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            executor.createJob(builder).run();

            // 4. Geçici dosyayı temizle
            if (listFile.exists()) listFile.delete();

            System.out.println("✅ İşlem Başarılı! Çıktı: " + cikisDosyasi);

        } catch (IOException e) {
            System.err.println("❌ Hata: " + e.getMessage());
        }
    }
}
