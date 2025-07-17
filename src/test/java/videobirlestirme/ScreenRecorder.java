package videobirlestirme;

import java.io.IOException;

public class ScreenRecorder {
    public static void main(String[] args) {
        String outputPath = "C:\\Users\\Hp\\OneDrive\\Desktop\\sdlc\\output.mp4";
        int recordDuration = 10; // Kayıt süresi (saniye cinsinden)

        try {
            // FFmpeg komutunu oluştur
            String ffmpegCommand = String.format(
                    "ffmpeg -f gdigrab -framerate 30 -i desktop -t %d -c:v libx264 -preset ultrafast %s",
                    recordDuration, outputPath
            );

            // FFmpeg komutunu çalıştır
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", ffmpegCommand);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Kayıt işlemi tamamlanana kadar bekle
            process.waitFor();

            System.out.println("Video kaydı tamamlandı: " + outputPath);
        } catch (IOException | InterruptedException e) {
            System.err.println("Hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}