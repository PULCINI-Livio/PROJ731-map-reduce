import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filepath = "data/LesMiserables.txt"; // Fichier d'entrée

        // 1. Diviser la lecture du fichier en une tâche de thread
        FileReader reader = new FileReader(filepath);
        Thread readerThread = new Thread(reader);

        try {
            // Lancer le thread pour lire le fichier
            readerThread.start();
            readerThread.join();  // Attendre que le thread de lecture ait fini avant de continuer

            // 2. Paralléliser le mappage : créer des threads pour chaque ligne
            List<String> inputs = reader.getInputs(); // Supposons que la lecture du fichier produit une liste de lignes
            Map<String, Integer>[] mappedResults = new Map[inputs.size()];

            Thread[] mappingThreads = new Thread[inputs.size()];
            for (int i = 0; i < inputs.size(); i++) {
                final int index = i;
                mappingThreads[i] = new Thread(() -> {
                    mappedResults[index] = Mapper.map(inputs.get(index));  // Mapper chaque ligne
                });
                mappingThreads[i].start();
            }

            // Attendre que tous les threads de mappage aient terminé
            for (Thread t : mappingThreads) {
                t.join();
            }

            // 3. Regroupement des résultats
            Map<String, List<Integer>> groupedResults = Shuffler.shuffleAndSort(mappedResults);

            // 4. Paralléliser la réduction
            // Création d'un thread pour effectuer la réduction
            Thread reduceThread = new Thread(() -> {
                List<Map.Entry<String, Integer>> finalResults = Reducer.reduce(groupedResults);
                // Afficher les résultats
                for (Map.Entry<String, Integer> entry : finalResults) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
            });
            reduceThread.start();

            // Attendre que le thread de réduction ait terminé
            reduceThread.join();

        } catch (InterruptedException e) {
            System.err.println("Erreur lors de l'exécution des threads : " + e.getMessage());
        }
    }
}
