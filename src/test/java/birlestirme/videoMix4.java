package birlestirme;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;
import java.io.IOException;

public class videoMix4 {

    public static void main(String[] args) {
        try {
            String video1 = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\video1.mp4";
            String video2 = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\video2.mp4";
            String cikisDosyasi = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\birlesmis_video.mp4";

            FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg.exe");
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);

            System.out.println("🎬 Videolar birleştiriliyor (concat filter)...");

            // concat filter ile birleştirme
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(video1)
                    .setInput(video2)
                    .overrideOutputFiles(true)
                    .addOutput(cikisDosyasi)
                    // filter_complex ile concat
                    .addExtraArgs("-filter_complex", "[0:v][0:a][1:v][1:a]concat=n=2:v=1:a=1[outv][outa]")
                    .addExtraArgs("-map", "[outv]")
                    .addExtraArgs("-map", "[outa]")
                    .setVideoCodec("libx264")
                    .setAudioCodec("aac")
                    .setVideoFrameRate(30)
                    .setVideoResolution(1920, 1080)
                    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                    .done();

            executor.createJob(builder).run();

            System.out.println("✅ Başarılı! Çıktı: " + cikisDosyasi);

        } catch (IOException e) {
            System.err.println("❌ Hata: " + e.getMessage());
            e.printStackTrace();
        }
    }
}