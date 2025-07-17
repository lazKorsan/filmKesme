package tango;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class videoBirlestir {
    public static void main(String[] args) {
        // Esneklik açısından giriş dosyalarını bir listede tutmak daha iyidir.
        List<String> girisDosyalari = new ArrayList<>();
        girisDosyalari.add("C:\\Users\\Hp\\OneDrive\\Desktop\\tango\\account1.mp4");
        girisDosyalari.add("C:\\Users\\Hp\\OneDrive\\Desktop\\tango\\hesap2.mp4");

        String cikisDosyasi = "C:\\Users\\Hp\\OneDrive\\Desktop\\tango\\account.mp4";

        try {
            birlestirVideolari(girisDosyalari, cikisDosyasi);
            System.out.println("Videolar başarıyla birleştirildi!");
        } catch (IOException | InterruptedException e) {
            System.err.println("Video birleştirme sırasında bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * FFmpeg kullanarak birden fazla video dosyasını tek bir dosyada birleştirir.
     * Not: FFmpeg'in sistemde kurulu ve PATH'e eklenmiş olması gerekmektedir.
     *
     * @param girisDosyalari Birleştirilecek video dosyalarının yollarını içeren liste.
     * @param cikisDosyasi Birleştirilmiş videonun kaydedileceği dosya yolu.
     * @throws IOException G/Ç hatası oluşursa.
     * @throws InterruptedException İşlem kesintiye uğrarsa.
     */
    public static void birlestirVideolari(List<String> girisDosyalari, String cikisDosyasi) throws IOException, InterruptedException {
        // FFmpeg'e verilecek girdi dosyalarının listesi için geçici bir metin dosyası oluşturulur.
        Path dosyaListesiYolu = Files.createTempFile("ffmpeg-list-", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaListesiYolu.toFile()))) {
            for (String girisDosyasi : girisDosyalari) {
                // FFmpeg'in concat demuxer'ı belirli bir format gerektirir.
                // 'file' anahtar kelimesi ve dosya yolu tırnak içinde olmalıdır.
                writer.write("file '" + girisDosyasi.replace("\\", "/") + "'\n");
            }
        }

        System.out.println("FFmpeg için geçici dosya listesi oluşturuldu: " + dosyaListesiYolu);

        // FFmpeg komutunu oluştur.
        // -f concat: concat demuxer'ını kullan.
        // -safe 0: Güvenli olmayan dosya yollarına izin ver (listede mutlak yollar kullanıldığında gereklidir).
        // -i ...: Girdi olarak kullanılacak dosya listesi.
        // -c copy: Video ve ses akışlarını yeniden kodlamadan kopyala (bu çok daha hızlıdır ve kaliteyi korur).
        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg",
                "-f", "concat",
                "-safe", "0",
                "-i", dosyaListesiYolu.toAbsolutePath().toString(),
                "-c", "copy",
                cikisDosyasi
        );

        // FFmpeg'in çıktılarını/hatalarını görebilmek için hata akışını yönlendir.
        processBuilder.redirectErrorStream(true);

        System.out.println("FFmpeg komutu çalıştırılıyor: " + String.join(" ", processBuilder.command()));

        Process process = processBuilder.start();

        // İşlemden gelen çıktıyı oku ve konsola yazdır.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        int exitCode = process.waitFor();

        // Geçici dosyayı sil.
        Files.delete(dosyaListesiYolu);
        System.out.println("Geçici dosya silindi.");

        if (exitCode != 0) {
            // Çıkış kodu 0 değilse bir hata oluşmuştur.
            throw new RuntimeException("FFmpeg işlemi " + exitCode + " hata koduyla sonlandı.");
        }
    }
}
