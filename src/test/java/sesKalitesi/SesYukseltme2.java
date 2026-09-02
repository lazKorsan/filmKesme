package sesKalitesi;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class SesYukseltme2 {

    public static void main(String[] args) {
        // Giriş ve çıkış dosya yolları
        String girisDosyasi = "C:\\Users\\user\\Desktop\\film\\cucumberframeworkAppiumTestleri.mp4";
        String cikisDosyasi = "C:\\Users\\user\\Desktop\\film\\cucumberframeworkAppiumTestleri26.mp4";

        // Başlangıç zamanını kaydet
        long baslangicZamani = System.currentTimeMillis();
        System.out.println("⏱️ İşlem başlangıcı: " + new java.util.Date());

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

            // Bitiş zamanını hesapla
            long bitisZamani = System.currentTimeMillis();
            long gecenSureMs = bitisZamani - baslangicZamani;

            // Milisaniyeyi dakika ve saniyeye çevir
            long dakika = TimeUnit.MILLISECONDS.toMinutes(gecenSureMs);
            long saniye = TimeUnit.MILLISECONDS.toSeconds(gecenSureMs) - TimeUnit.MINUTES.toSeconds(dakika);
            long milisaniye = gecenSureMs % 1000;

            System.out.println("✅ İşlem tamamlandı!");
            System.out.println("🎬 Çıktı dosyası: " + cikisDosyasi);
            System.out.println("⏱️ Bitiş zamanı: " + new java.util.Date());
            System.out.println("⏱️ Toplam geçen süre: " + dakika + " dakika " + saniye + " saniye " + milisaniye + " milisaniye");

            // Sadece dakika olarak gösterim (virgüllü)
            double dakikaOlarak = gecenSureMs / 1000.0 / 60.0;
            System.out.println(String.format("📊 (%.2f dakika)", dakikaOlarak));

        } catch (Exception e) {
            // Hata durumunda da geçen süreyi göster
            long hataZamani = System.currentTimeMillis();
            long gecenSureMs = hataZamani - baslangicZamani;
            long dakika = TimeUnit.MILLISECONDS.toMinutes(gecenSureMs);
            long saniye = TimeUnit.MILLISECONDS.toSeconds(gecenSureMs) - TimeUnit.MINUTES.toSeconds(dakika);

            System.err.println("❌ Bir hata oluştu: " + e.getMessage());
            System.err.println("⏱️ Hata oluşana kadar geçen süre: " + dakika + " dakika " + saniye + " saniye");
            e.printStackTrace();
        }
    }

}