/*


package videocutter;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;

public class VideoIndirici {
    private String indirmeDizini;

    public VideoIndirici(String indirmeDizini) {
        this.indirmeDizini = indirmeDizini;
    }

    public String videoIndir(String videoUrl) {
        try {
            YoutubeDLRequest request = new YoutubeDLRequest(videoUrl, indirmeDizini);
            
            // İndirme seçeneklerini ayarlama
            request.setOption("format", "best"); // En iyi kalitede video
            request.setOption("ignore-errors", true);
            request.setOption("no-warnings", true);
            
            // İndirme işlemini başlat
            System.out.println("Video indiriliyor...");
            YoutubeDLResponse response = YoutubeDL.execute(request);
            
            System.out.println("İndirme tamamlandı!");
            System.out.println("Çıktı dizini: " + indirmeDizini);
            System.out.println("Exit code: " + response.getExitCode());
            
            return response.getOut();

        } catch (Exception e) {
            System.out.println("Video indirme hatası: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}


// yeni class da indirme örneği
// ublic static void main(String[] args) {
//    String indirmeDizini = "C:\\Users\\Hp\\OneDrive\\Desktop\\video_kesmekliklik";
//    String videoUrl = "https://www.youtube.com/watch?v=VIDEO_ID";
//
//    VideoIndirici indirici = new VideoIndirici(indirmeDizini);
//    String sonuc = indirici.videoIndir(videoUrl);
//
//    if (sonuc != null) {
//        System.out.println("Video başarıyla indirildi!");
//    } else {
//        System.out.println("Video indirme başarısız!");
//    }
//}

// ==============================
// gelişmiş özellikler eklenebilir
//request.setOption("format", "bestvideo+bestaudio"); // En iyi video ve ses kalitesi
//   request.setOption("extract-audio", true); // Sadece ses indirmek için
//   request.setOption("audio-format", "mp3"); // Ses formatını belirtme
//   request.setOption("output-template", "%(title)s.%(ext)s"); // Çıktı dosya adı formatı

// filimkesmeye entegrasyon örneği
// public static void main(String[] args) {
//    // Video indirme
//    String indirmeDizini = "C:\\Users\\Hp\\OneDrive\\Desktop\\video_kesmekliklik";
//    String videoUrl = "https://www.youtube.com/watch?v=VIDEO_ID";
//
//    VideoIndirici indirici = new VideoIndirici(indirmeDizini);
//    String indirilenDosya = indirici.videoIndir(videoUrl);
//
//    if (indirilenDosya != null) {
//        // Video kesme işlemi
//        String cikisDosyasi = indirmeDizini + "\\kesilen_video.mp4";
//
//        try {
//            FFmpeg ffmpeg = new FFmpeg("C:\\ffmpeg\\bin\\ffmpeg.exe");
//            FFmpegBuilder builder = new FFmpegBuilder()
//                    .setInput(indirilenDosya)
//                    .overrideOutputFiles(true)
//                    .addOutput(cikisDosyasi)
//                    .setStartOffset(120, TimeUnit.SECONDS)
//                    .setDuration(180, TimeUnit.SECONDS)
//                    .done();
//
//            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
//            executor.createJob(builder).run();
//
//            System.out.println("Video başarıyla indirildi ve kesildi!");
//        } catch (Exception e) {
//            System.out.println("Video kesme hatası: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}

// pom dosyasına sonra eklenecek
// <?xml version="1.0" encoding="UTF-8"?>
//<project xmlns="http://maven.apache.org/POM/4.0.0"
//         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
//         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
//    <modelVersion>4.0.0</modelVersion>
//
//    <groupId>org.example</groupId>
//    <artifactId>filmKesme</artifactId>
//    <version>1.0-SNAPSHOT</version>
//
//    <properties>
//        <maven.compiler.source>21</maven.compiler.source>
//        <maven.compiler.target>21</maven.compiler.target>
//        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
//        <javafx.version>21</javafx.version>
//    </properties>
//
//    <dependencies>
//        <!-- JavaFX dependencies -->
//        <dependency>
//            <groupId>org.openjfx</groupId>
//            <artifactId>javafx-controls</artifactId>
//            <version>${javafx.version}</version>
//        </dependency>
//        <dependency>
//            <groupId>org.openjfx</groupId>
//            <artifactId>javafx-fxml</artifactId>
//            <version>${javafx.version}</version>
//        </dependency>
//        <dependency>
//            <groupId>org.openjfx</groupId>
//            <artifactId>javafx-media</artifactId>
//            <version>${javafx.version}</version>
//        </dependency>
//
//        <!-- FFmpeg wrapper for Java -->
//        <dependency>
//            <groupId>net.bramp.ffmpeg</groupId>
//            <artifactId>ffmpeg</artifactId>
//            <version>0.7.0</version>
//        </dependency>
//
//
//        <!-- SLF4J API for logging -->
//        <dependency>
//            <groupId>org.slf4j</groupId>
//            <artifactId>slf4j-api</artifactId>
//            <version>2.0.9</version>
//        </dependency>
//        <dependency>
//            <groupId>org.slf4j</groupId>
//            <artifactId>slf4j-simple</artifactId>
//            <version>2.0.9</version>
//        </dependency>
//    </dependencies>
//
//
//    <build>
//        <plugins>
//            <plugin>
//                <groupId>org.openjfx</groupId>
//                <artifactId>javafx-maven-plugin</artifactId>
//                <version>0.0.8</version>
//                <configuration>
//                    <mainClass>org.example.Main</mainClass>
//                </configuration>
//            </plugin>
//        </plugins>
//    </build>
//
//</project>

 */