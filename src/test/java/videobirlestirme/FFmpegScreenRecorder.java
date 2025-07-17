package videobirlestirme;

import java.io.IOException;

public class FFmpegScreenRecorder {
    public static void main(String[] args) {
        try {
            // Kayıt dosyasının tam yolu
            String outputPath = "C:\\Users\\Hp\\OneDrive\\Desktop\\sdlc\\output.mp4";

            // FFmpeg komutu: 30 FPS ile ekranı kaydet
            String[] command = {
                    "ffmpeg",
                    "-f", "gdigrab", // Windows için ekran yakalama
                    "-i", "desktop",
                    "-framerate", "30",
                    "-vcodec", "libx264",
                    outputPath  // Tam yol belirtildi
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // 10 saniye kayıt yap (örnek amaçlı)
            Thread.sleep(10000);

            // FFmpeg'i sonlandır
            process.destroy();
            System.out.println("Kayıt tamamlandı: " + outputPath);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}