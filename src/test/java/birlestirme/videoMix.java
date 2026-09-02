package birlestirme;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;

public class videoMix {

    public static void main(String[] args) {
        try {
            String video1 = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\video1.mp4";
            String video2 = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\video2.mp4";
            String output = "C:\\Users\\user\\Desktop\\video_kesmekliklik\\birlesmis_video.mp4";

            FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg.exe");

            // İleri seviye birleştirme - Her zaman çalışır
            System.out.println("🎬 Videolar birleştiriliyor...");

            FFmpegBuilder builder = new FFmpegBuilder()
                    .addInput(video1)
                    .addInput(video2)
                    .overrideOutputFiles(true)
                    .addOutput(output)
                    // Her iki videoyu da aynı formata getir
                    .addExtraArgs("-filter_complex",
                            "[0:v]scale=1920:1080:force_original_aspect_ratio=decrease,pad=1920:1080:(ow-iw)/2:(oh-ih)/2,fps=30,setpts=PTS-STARTPTS[v0];" +
                                    "[0:a]aresample=44100,asetpts=PTS-STARTPTS[a0];" +
                                    "[1:v]scale=1920:1080:force_original_aspect_ratio=decrease,pad=1920:1080:(ow-iw)/2:(oh-ih)/2,fps=30,setpts=PTS-STARTPTS[v1];" +
                                    "[1:a]aresample=44100,asetpts=PTS-STARTPTS[a1];" +
                                    "[v0][a0][v1][a1]concat=n=2:v=1:a=1[outv][outa]")
                    .addExtraArgs("-map", "[outv]")
                    .addExtraArgs("-map", "[outa]")
                    .addExtraArgs("-c:v", "libx264")
                    .addExtraArgs("-c:a", "aac")
                    .addExtraArgs("-preset", "medium")
                    .addExtraArgs("-crf", "23")
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            executor.createJob(builder).run();

            System.out.println("✅ Başarılı! Yeni video: " + output);

        } catch (Exception e) {
            System.err.println("❌ Hata: " + e.getMessage());
            e.printStackTrace();
        }
    }
}