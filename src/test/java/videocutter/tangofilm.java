package videocutter;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.util.concurrent.TimeUnit; // Bu import'u ekleyin

public class tangofilm {
    public static void main(String[] args) {
        try {
            // Kendi video dosyanızın yolunu buraya yazın
            String girisDosyasi = "C:\\Users\\Hp\\OneDrive\\Desktop\\tango\\kesilecek.mp4";

            // Kesilmiş videonun nereye kaydedileceğini buraya yazın
            String cikisDosyasi = "C:\\Users\\Hp\\OneDrive\\Desktop\\tango\\kesilen.mp4";

            FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg\\bin\\ffmpeg.exe");

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(girisDosyasi)
                    .overrideOutputFiles(true)
                    .addOutput(cikisDosyasi)
                    .setStartOffset(00, TimeUnit.SECONDS)    // 2. dakikadan başla (5dk - 3dk = 2dk)
                    .setDuration(05, TimeUnit.SECONDS)       // 3 dakika al
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            executor.createJob(builder).run();

            System.out.println("Video başarıyla kesildi!");

        } catch (Exception e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
            e.printStackTrace();

            /*
            kurulum için youtube da şu videodan destek alındı
            https://www.youtube.com/watch?v=iS9Lz8Vg2f4&t=125s
            String girisDosyasi = "C:\Users\Hp\maven_video_kesme\\kesilecek.mp4"; // video dosyanızın gerçek adını yazın
            String cikisDosyasi = "C:\\Users\\Hp\\maven_video_kesme\\kesilmis_video.mp4";

             */
            /*
            git add .
            git commit -m "Dosyaları ekledim"
            git push origin main
             */
        }
    }
}