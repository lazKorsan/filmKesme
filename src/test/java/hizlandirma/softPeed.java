package hizlandirma;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class softPeed {

    public static void main(String[] args) {
        try {
            String inputVideo = "C:\\Users\\user\\Desktop\\ply\\b1.mp4";
            String outputVideo = "C:\\Users\\user\\Desktop\\ply\\v1_hizli.mp4";

            FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg.exe");

            System.out.println("⚡ Video hızlandırılıyor (17dk 55sn -> 14dk 50sn)...");

            FFmpegBuilder builder = new FFmpegBuilder()
                    .addInput(inputVideo)
                    .overrideOutputFiles(true)
                    .addOutput(outputVideo)
                    // 1075 saniyeyi 890 saniyeye düşürmek için filtreler
                    .addExtraArgs("-filter_complex",
                            "[0:v]setpts=0.82790697*PTS[v];[0:a]atempo=1.207865[a]")
                    .addExtraArgs("-map", "[v]")
                    .addExtraArgs("-map", "[a]")
                    .addExtraArgs("-c:v", "libx264")
                    .addExtraArgs("-c:a", "aac")
                    .addExtraArgs("-preset", "medium")
                    .addExtraArgs("-crf", "23")
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            executor.createJob(builder).run();

            System.out.println("✅ Başarılı! Hızlandırılmış video: " + outputVideo);

        } catch (Exception e) {
            System.err.println("❌ Hata: " + e.getMessage());
            e.printStackTrace();
        }
    }
}