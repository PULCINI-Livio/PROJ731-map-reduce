import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.List;

public class ResultWriter {

    public static void writeToFile(List<Map.Entry<String, Integer>> result, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, Integer> entry : result) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
            System.out.println("Résultat sauvegardé dans le fichier : " + filename);
        } catch (IOException e) {
            System.err.println("Erreur d'écriture dans le fichier : " + e.getMessage());
        }
    }
}
