package videocutter;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.util.concurrent.TimeUnit; // Bu import'u ekleyin

public class FilmKesme {
    public static void main(String[] args) {
        try {
            // Kendi video dosyanızın yolunu buraya yazın
            String girisDosyasi = "C:\\Users\\Hp\\OneDrive\\Desktop\\video_kesmekliklik\\kesilecek.mp4";

            // Kesilmiş videonun nereye kaydedileceğini buraya yazın
            String cikisDosyasi = "C:\\Users\\Hp\\OneDrive\\Desktop\\video_kesmekliklik\\kesilen.mp4";

            FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg\\bin\\ffmpeg.exe");

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(girisDosyasi)
                    .overrideOutputFiles(true)
                    .addOutput(cikisDosyasi)
                    .setStartOffset(120, TimeUnit.SECONDS)    // 2. dakikadan başla (5dk - 3dk = 2dk)
                    .setDuration(180, TimeUnit.SECONDS)       // 3 dakika al
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            executor.createJob(builder).run();

            System.out.println("Video başarıyla kesildi!");

        } catch (Exception e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
            e.printStackTrace();

            /*
            String girisDosyasi = "C:\Users\Hp\maven_video_kesme\\kesilecek.mp4"; // video dosyanızın gerçek adını yazın
            String cikisDosyasi = "C:\\Users\\Hp\\maven_video_kesme\\kesilmis_video.mp4";

             */
        }
    }
}