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

        // Découper la liste en morceaux de taille "chunkSize"
        int totalLines = inputs.size();
        int chunkSize = (int) Math.ceil((double) totalLines / NUM_THREADS);

        ExecutorService mapPool = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Map<String, Integer>>> mapFutures = new ArrayList<>();

        for (int i = 0; i < totalLines; i += chunkSize) {
            int start = i;
            int end = Math.min(i + chunkSize, totalLines);
            List<String> chunk = inputs.subList(start, end);

            // Chaque tâche traite un chunk complet
            Callable<Map<String, Integer>> task = () -> {
                Map<String, Integer> localMap = new HashMap<>();
                for (String line : chunk) {
                    Map<String, Integer> mappedLine = Mapper.map(line);
                    for (Map.Entry<String, Integer> entry : mappedLine.entrySet()) {
                        localMap.merge(entry.getKey(), entry.getValue(), Integer::sum);
                    }
                }
                return localMap;
            };

            mapFutures.add(mapPool.submit(task));
        }

        // Collecte des résultats
        List<Map<String, Integer>> mapped = new ArrayList<>();
        for (Future<Map<String, Integer>> future : mapFutures) {
            mapped.add(future.get());
        }

        // Shuffle + Reduce
        Map<String, List<Integer>> grouped = Shuffler.shuffleAndSort(mapped);
        List<Map.Entry<String, Integer>> result = ReducerParallel.reduce(grouped);

        // Clean up
        mapPool.shutdown();
        mapPool.awaitTermination(1, TimeUnit.MINUTES);

        return result;
    }
}
