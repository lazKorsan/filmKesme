package birlestirme;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class videoMix5 {

    public static void main(String[] args) {
        try {
            // 1. Birleştirilecek videoların yolları
            String video1 = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\video1.mp4";
            String video2 = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\video2.mp4";
            String video3 = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\video3.mp4";
            String cikisDosyasi = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\birlesmis_video.mp4";

            // 2. FFmpeg yolu (FilmKesme dosyanızdaki ile aynı olmalı)
            FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg.exe");

            // 3. FFmpeg'in okuyabilmesi için geçici bir liste dosyası (.txt) oluşturuyoruz
            // Bu dosya içeriği şu şekilde olmalı: file 'yol/video1.mp4'
            File listFile = new File("C:\\Users\\user\\Desktop\\video_kesmekliklik\\liste.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(listFile))) {
                writer.write("file '" + video1 + "'");
                writer.newLine();
                writer.write("file '" + video2 + "'");
            }

            // 4. Birleştirme (Concat) İşlemi
            // NOT: Videoların çözünürlükleri ve fps değerleri aynıysa 'copy' modu en hızlısıdır.
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(listFile.getAbsolutePath())
                    .setFormat("concat") // Birleştirme formatı
                    .addExtraArgs("-safe", "0") // Dosya yollarındaki özel karakterler için
                    .overrideOutputFiles(true)
                    .addOutput(cikisDosyasi)
                    .addExtraArgs("-c", "copy") // Yeniden kodlamadan (render almadan) hızlıca birleştirir
                    .done();

            System.out.println("🎬 Videolar birleştiriliyor, lütfen bekleyin...");

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            executor.createJob(builder).run();

            // 5. İşlem bitince geçici txt dosyasını silelim
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