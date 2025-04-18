import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class MapReduce {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    
    public static List<Map.Entry<String, Integer>> mapReduce(String filepath) throws FileNotFoundException, IOException, InterruptedException, ExecutionException {
        ArrayList<String> inputs = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filepath), "utf-8")) {
            while (scanner.hasNextLine()) {
                inputs.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filepath);
            throw e;
        }

        // Mapper : Traiter les lignes en parallèle
        ExecutorService mapPool = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Map<String, Integer>>> mapFutures = new ArrayList<>();

        for (String input : inputs) {
            mapFutures.add(mapPool.submit(() -> Mapper.map(input)));
        }

        // Attente et collecte des résultats du map
        List<Map<String, Integer>> mapped = new ArrayList<>();
        for (Future<Map<String, Integer>> future : mapFutures) {
            mapped.add(future.get());
        }

        // Shuffler : Regroupement des résultats
        Map<String, List<Integer>> grouped = Shuffler.shuffleAndSort(mapped);

        // Réduction : Utilisation du Reducer parallèle pour l'étape de réduction
        List<Map.Entry<String, Integer>> result = ReducerParallel.reduce(grouped);

        // Fermer le pool après utilisation
        mapPool.shutdown();
        mapPool.awaitTermination(1, TimeUnit.MINUTES);

        return result;
    }
}
