package birlestirme;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class videoMix2 {

    public static void main(String[] args) {
        try {
            // 1. Birleştirilecek videoların yolları
            String video1 = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\video1.mp4";
            String video2 = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\video2.mp4";
            String cikisDosyasi = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\birlesmis_video.mp4";

            // 2. FFmpeg yolu
            FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg.exe");

            // 3. Geçici liste dosyası oluştur
            File listFile = new File("C:\\Users\\user\\Desktop\\video_kesmekliklik\\liste.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(listFile))) {
                writer.write("file '" + video1.replace("\\", "/") + "'");
                writer.newLine();
                writer.write("file '" + video2.replace("\\", "/") + "'");
            }

            System.out.println("🎬 Videolar birleştiriliyor (yeniden kodlanıyor)...");
            System.out.println("Bu işlem birkaç dakika sürebilir...");

            // 4. BİRLEŞTİRME - YENİDEN KODLAMA İLE (copy yerine)
            // Bu yöntem videolar farklı özelliklerde olsa bile düzgün çalışır
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(listFile.getAbsolutePath())
                    .setFormat("concat")
                    .addExtraArgs("-safe", "0")
                    .overrideOutputFiles(true)
                    .addOutput(cikisDosyasi)
                    .setVideoCodec("libx264")     // H.264 video codec
                    .setAudioCodec("aac")         // AAC audio codec
                    .setVideoFrameRate(30)         // Sabit FPS (videolarından birinin FPS'ine göre ayarla)
                    .setVideoResolution(1920, 1080) // Sabit çözünürlük
                    .setAudioBitRate(128000)       // 128k bitrate
                    .setVideoBitRate(2500000)      // 2.5M bitrate
                    .addExtraArgs("-preset", "medium") // Hız/kalite dengesi (fast, medium, slow)
                    .addExtraArgs("-crf", "23")    // Kalite (18-28 arası, düşük sayı daha iyi kalite)
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            executor.createJob(builder).run();

            // 5. Geçici dosyayı sil
            if (listFile.exists()) {
                listFile.delete();
            }

            System.out.println("✅ Başarılı! Yeni video burada: " + cikisDosyasi);

        } catch (IOException e) {
            System.err.println("❌ Bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}