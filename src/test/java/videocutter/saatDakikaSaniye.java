package videocutter;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.util.concurrent.TimeUnit;

public class saatDakikaSaniye {
    public static void main(String[] args) {
        try {
            // Kendi video dosyanızın yolunu buraya yazın
            String girisDosyasi = "C:\\Users\\Hp\\OneDrive\\Desktop\\video_kesmekliklik\\kesilecek.mp4";
            String cikisDosyasi = "C:\\Users\\Hp\\OneDrive\\Desktop\\video_kesmekliklik\\kesilen.mp4";

            // Başlangıç zamanını ayarlayın (saat, dakika, saniye)
            int baslangicSaat = 0;   // Örnek: 0 saat
            int baslangicDakika = 44; // Örnek: 2 dakika
            int baslangicSaniye = 40; // Örnek: 0 saniye

            // Süreyi ayarlayın (saat, dakika, saniye)
            int sureSaat = 0;    // Örnek: 0 saat
            int sureDakika = 3;  // Örnek: 3 dakika
            int sureSaniye = 0;  // Örnek: 0 saniye

            // Zamanları saniyeye çevir
            long baslangicSaniyeCinsinden = (baslangicSaat * 3600L) + (baslangicDakika * 60L) + baslangicSaniye;
            long sureSaniyeCinsinden = (sureSaat * 3600L) + (sureDakika * 60L) + sureSaniye;

            FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg\\bin\\ffmpeg.exe");

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(girisDosyasi)
                    .overrideOutputFiles(true)
                    .addOutput(cikisDosyasi)
                    .setStartOffset(baslangicSaniyeCinsinden, TimeUnit.SECONDS)
                    .setDuration(sureSaniyeCinsinden, TimeUnit.SECONDS)
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            executor.createJob(builder).run();

            System.out.println("Video başarıyla kesildi!");
            System.out.printf("Başlangıç: %02d:%02d:%02d%n", baslangicSaat, baslangicDakika, baslangicSaniye);
            System.out.printf("Süre: %02d:%02d:%02d%n", sureSaat, sureDakika, sureSaniye);

        } catch (Exception e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}