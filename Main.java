import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filepath = "data/rousseau.txt";
        String outputFile = "output/resultat_MapReduce.txt";
        String statsCSV = "output/statistics.csv";

        long startTime = System.nanoTime(); // Début du chronométrage

        try {
            // MapReduce
            List<Map.Entry<String, Integer>> result = MapReduce.mapReduce(filepath);

            long endTime = System.nanoTime(); // Fin
            long duration = (endTime - startTime) / 1_000_000; // en ms
            int distinctWords = result.size();

            // Taille du fichier source
            long fileSize = Files.size(Paths.get(filepath));

            // Écrire le résultat principal
            ResultWriter.writeToFile(result, outputFile);

            // Ajouter les statistiques dans le CSV
            boolean fileExists = new File(statsCSV).exists();

            try (FileWriter fw = new FileWriter(statsCSV, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {

                if (!fileExists) {
                    // Écrire l'en-tête si le fichier n'existe pas
                    out.println("fileName,time,words,size");
                }

                // Écrire une ligne de stats
                out.printf("%s,%d,%d,%d%n",
                        filepath,
                        duration,
                        distinctWords,
                        fileSize);
            }

            // Affichage console
            System.out.println("Temps d'exécution du MapReduce : " + duration + " ms");
            System.out.println("Nombre de mots différents trouvés : " + distinctWords);
            System.out.println("Taille du fichier : " + fileSize + " octets");

        } catch (FileNotFoundException e) {
            System.err.println("Erreur : fichier non trouvé → " + filepath);
        } catch (Exception e) {
            System.err.println("Erreur inattendue : " + e.getMessage());
        }
    }
}
