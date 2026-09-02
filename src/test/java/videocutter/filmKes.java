package videocutter;

import net.bramp.ffmpeg.FFmpeg;

import java.io.IOException;

public class filmKes {
    public static void main(String[] args) throws IOException {
        // FFmpeg yolunu belirtin
        String ffmpegPath = "C:\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg.exe";
        FFmpeg ffmpeg = new FFmpeg(ffmpegPath);

        // Video kesme işlemi için gerekli parametreleri ayarlayın
        String inputFile = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\kesilecek.mp4";
        String outputFile = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\enumListKullanimi.mp4";
        String startTime = "00:00:01"; // Başlama zamanı
        String duration = "00:00:20";   // Süre

        // FFmpeg komutunu oluşturun
        String command = String.format("-i %s -ss %s -t %s -c copy %s", inputFile, startTime, duration, outputFile);


    }
}
