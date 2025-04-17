import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filepath = "data/rousseau.txt";
        String outputFile = "output/resultat_MapReduce.txt";

        long startTime = System.nanoTime(); // ⏱ Début du chronométrage

        try {
            List<Map.Entry<String, Integer>> result = MapReduce.mapReduce(filepath);

            long endTime = System.nanoTime(); // ⏱ Fin du chronométrage
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;

            // Affichage des résultats
            /*for (Map.Entry<String, Integer> entry : result) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }*/

            ResultWriter.writeToFile(result, outputFile);

            System.out.printf("Temps total de traitement : %.3f secondes%n", durationInSeconds);

        } catch (FileNotFoundException e) {
            System.err.println("Error: The file " + filepath + " was not found.");
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
