import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class ConvertToUtf8 {
    public static void main(String[] args) throws IOException {
        // Chemins des fichiers
        Path inputPath = Paths.get("data/rousseau.txt");
        Path outputPath = Paths.get("convertedToUft8/output-utf8.txt");

        // Charset source (ex: ISO-8859-1) et destination (UTF-8)
        Charset sourceCharset = Charset.forName("ISO-8859-1");
        Charset targetCharset = Charset.forName("UTF-8");

        // Lire avec encodage source
        try (BufferedReader reader = Files.newBufferedReader(inputPath, sourceCharset);
             BufferedWriter writer = Files.newBufferedWriter(outputPath, targetCharset)) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        }

        System.out.println("Conversion terminée en UTF-8 !");
    }
}
