package dersnotlari;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class testng {

    // ÖNEMLİ: Bu yolu kendi Excel dosyanızın yolu ile değiştirin.
    private static final String EXCEL_DOSYA_YOLU = "C:\\Users\\Hp\\OneDrive\\Desktop\\tango\\kesim_listesi.xlsx";

    public static void main(String[] args) {
        System.out.println("Video kesme işlemi başlıyor...");
        System.out.println("Okunacak Excel dosyası: " + EXCEL_DOSYA_YOLU);

        try {
            exceldenOkuVeVideolariKes();
            System.out.println("Tüm işlemler başarıyla tamamlandı.");
        } catch (IOException e) {
            System.err.println("Excel dosyası okunurken veya yazılırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void exceldenOkuVeVideolariKes() throws IOException {
        // try-with-resources bloğu, dosya akışlarının otomatik olarak kapanmasını sağlar.
        try (FileInputStream fis = new FileInputStream(EXCEL_DOSYA_YOLU);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // İlk sayfayı al
            int sonSatir = sheet.getLastRowNum();

            // İlk satırın başlık olduğunu varsayarak 1'den başlıyoruz.
            // Eğer başlık yoksa i = 0 olarak değiştirin.
            for (int i = 1; i <= sonSatir; i++) {
                Row satir = sheet.getRow(i);
                if (satir == null || satir.getCell(0) == null) {
                    // Boş satırları atla
                    continue;
                }

                String sonucMesaji;
                try {
                    // Hücrelerden verileri oku
                    String dosyaYolu = satir.getCell(0).getStringCellValue();
                    double baslangicSaniyesi = satir.getCell(1).getNumericCellValue();
                    double bitisSaniyesi = satir.getCell(2).getNumericCellValue();

                    System.out.printf("\nSatır %d işleniyor: %s (%.2f sn -> %.2f sn)\n", i + 1, dosyaYolu, baslangicSaniyesi, bitisSaniyesi);

                    // Çıktı dosyasının adını oluştur
                    String ciktiDosyaYolu = ciktiDosyaAdiOlustur(dosyaYolu, baslangicSaniyesi, bitisSaniyesi);

                    // FFmpeg ile videoyu kes
                    boolean basarili = videoyuKes(dosyaYolu, ciktiDosyaYolu, baslangicSaniyesi, bitisSaniyesi);

                    sonucMesaji = basarili ? "Kesme Başarılı" : "HATA: FFmpeg işlemi başarısız oldu.";

                } catch (Exception e) {
                    sonucMesaji = "HATA: " + e.getMessage();
                    System.err.println("Satır " + (i + 1) + " işlenirken hata: " + e.getMessage());
                }

                // Sonucu 4. hücreye (D sütunu) yaz
                Cell sonucHucresi = satir.createCell(3, CellType.STRING);
                sonucHucresi.setCellValue(sonucMesaji);
            }

            // Değişiklikleri Excel dosyasına geri yaz
            try (FileOutputStream fos = new FileOutputStream(EXCEL_DOSYA_YOLU)) {
                workbook.write(fos);
            }
        }
    }

    /**
     * FFmpeg kullanarak bir videonun belirtilen aralığını keser.
     * @param girisDosyasi Kesilecek video
     * @param cikisDosyasi Kesilmiş videonun kaydedileceği yer
     * @param baslangicSaniyesi Kesimin başlayacağı saniye
     * @param bitisSaniyesi Kesimin biteceği saniye
     * @return İşlem başarılı ise true, değilse false döner.
     */
    private static boolean videoyuKes(String girisDosyasi, String cikisDosyasi, double baslangicSaniyesi, double bitisSaniyesi) throws IOException, InterruptedException {
        // Eğer çıktı dosyası zaten varsa, üzerine yazma sorunlarını önlemek için sil.
        Files.deleteIfExists(Paths.get(cikisDosyasi));

        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg",
                "-i", girisDosyasi, // Giriş dosyası
                "-ss", String.valueOf(baslangicSaniyesi), // Başlangıç zamanı
                "-to", String.valueOf(bitisSaniyesi), // Bitiş zamanı
                "-c", "copy", // Yeniden kodlama yapmadan kopyala (çok daha hızlı)
                cikisDosyasi // Çıktı dosyası
        );

        processBuilder.redirectErrorStream(true);
        System.out.println("FFmpeg komutu çalıştırılıyor: " + String.join(" ", processBuilder.command()));

        Process process = processBuilder.start();

        // FFmpeg'in çıktısını okuyarak olası hataları görebiliriz
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("FFMPEG: " + line);
            }
        }

        int exitCode = process.waitFor();
        return exitCode == 0;
    }

    /**
     * Kesilen video için özgün bir çıktı adı oluşturur.
     * Örn: "C:\video\egitim.mp4" -> "C:\video\egitim_cut_10.5_55.0.mp4"
     */
    private static String ciktiDosyaAdiOlustur(String orijinalDosyaYolu, double baslangic, double bitis) {
        Path path = Paths.get(orijinalDosyaYolu);
        String dosyaAdi = path.getFileName().toString();
        String dosyaUzantisi = "";
        int uzantiIndex = dosyaAdi.lastIndexOf('.');
        if (uzantiIndex > 0) {
            dosyaUzantisi = dosyaAdi.substring(uzantiIndex);
            dosyaAdi = dosyaAdi.substring(0, uzantiIndex);
        }

        String yeniDosyaAdi = String.format("%s_cut_%.1f_%.1f%s", dosyaAdi, baslangic, bitis, dosyaUzantisi)
                                      .replace(',', '.'); // Ondalık virgülünü noktaya çevir

        return path.getParent().resolve(yeniDosyaAdi).toString();
    }
}