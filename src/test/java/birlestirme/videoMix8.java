package birlestirme;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class videoMix8 {

    public static void main(String[] args) {
        try {
            String video1 = "C:\\Users\\user\\Desktop\\ply\\v1.mp4";
            String video2 = "C:\\Users\\user\\Desktop\\ply\\v2.mp4";
            String output = "C:\\Users\\user\\Desktop\\ply\\b1.mp4";

            FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg.exe");

            System.out.println("🎬 Videolar birleştiriliyor...");

            // Zorlayıcı filtre zinciri: SAR, piksel formatı ve ses kanalları eşitlendi
            String filterComplex =
                    "[0:v]scale=1920:1080:force_original_aspect_ratio=decrease,pad=1920:1080:(ow-iw)/2:(oh-ih)/2,setsar=1,fps=30,format=yuv420p,setpts=PTS-STARTPTS[v0];" +
                            "[0:a]aformat=sample_rates=44100:channel_layouts=stereo,asetpts=PTS-STARTPTS[a0];" +
                            "[1:v]scale=1920:1080:force_original_aspect_ratio=decrease,pad=1920:1080:(ow-iw)/2:(oh-ih)/2,setsar=1,fps=30,format=yuv420p,setpts=PTS-STARTPTS[v1];" +
                            "[1:a]aformat=sample_rates=44100:channel_layouts=stereo,asetpts=PTS-STARTPTS[a1];" +
                            "[v0][a0][v1][a1]concat=n=2:v=1:a=1[outv][outa]";

            FFmpegBuilder builder = new FFmpegBuilder()
                    .addInput(video1)
                    .addInput(video2)
                    .overrideOutputFiles(true)
                    .addOutput(output)
                    .addExtraArgs("-filter_complex", filterComplex)
                    .addExtraArgs("-map", "[outv]")
                    .addExtraArgs("-map", "[outa]")
                    .addExtraArgs("-c:v", "libx264")
                    .addExtraArgs("-pix_fmt", "yuv420p") // Oynatıcı uyumluluğu için zorunlu
                    .addExtraArgs("-c:a", "aac")
                    .addExtraArgs("-b:a", "192k")       // Sabit ses bit hızı
                    .addExtraArgs("-preset", "medium")
                    .addExtraArgs("-crf", "23")
                    .addExtraArgs("-movflags", "+faststart") // Web ve mobil oynatım optimizasyonu
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