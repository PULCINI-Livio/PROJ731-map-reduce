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
            long duration = (endTime - startTime) / 1_000_000; // Convertir en millisecondes

            // Affichage des résultats
            /*for (Map.Entry<String, Integer> entry : result) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }*/

            ResultWriter.writeToFile(result, outputFile);

            // Afficher le temps d'exécution
            System.out.println("Temps d'exécution du MapReduce : " + duration + " ms");

        } catch (FileNotFoundException e) {
            System.err.println("Error: The file " + filepath + " was not found.");
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
