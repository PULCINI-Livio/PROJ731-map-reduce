import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filepath = "data/rousseau.txt";
        String outputFile = "resultat_rousseau.txt";

        try {
            List<Map.Entry<String, Integer>> result = MapReduce.mapReduce(filepath);

            // Affichage dans la console (optionnel)
            for (Map.Entry<String, Integer> entry : result) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            // Sauvegarde dans un fichier texte
            ResultWriter.writeToFile(result, outputFile);

        } catch (FileNotFoundException e) {
            System.err.println("Error: The file " + filepath + " was not found.");
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
