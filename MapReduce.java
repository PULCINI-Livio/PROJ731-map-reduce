import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.*;

public class MapReduce {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public static List<Map.Entry<String, Integer>> mapReduce(String filepath) throws Exception {
        List<String> inputs = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filepath), "utf-8")) {
            while (scanner.hasNextLine()) {
                inputs.add(scanner.nextLine());
            }
        }

        // Découpage en morceaux
        List<List<String>> chunks = splitIntoChunks(inputs, NUM_THREADS);

        ExecutorService mapPool = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Map<String, Integer>>> futures = new ArrayList<>();

        for (List<String> chunk : chunks) {
            futures.add(mapPool.submit(() -> {
                Map<String, Integer> localMap = new HashMap<>();
                for (String line : chunk) {
                    Map<String, Integer> mapResult = Mapper.map(line);
                    for (Map.Entry<String, Integer> entry : mapResult.entrySet()) {
                        localMap.merge(entry.getKey(), entry.getValue(), Integer::sum);
                    }
                }
                return localMap;
            }));
        }

        mapPool.shutdown();
        mapPool.awaitTermination(1, TimeUnit.MINUTES);

        // Collecte des résultats du mapping
        List<Map<String, Integer>> mapped = new ArrayList<>();
        for (Future<Map<String, Integer>> future : futures) {
            mapped.add(future.get());
        }

        // Shuffle
        Map<String, List<Integer>> grouped = Shuffler.shuffleAndSort(mapped);

        // Réduction
        return Reducer.reduce(grouped);
    }

    private static List<List<String>> splitIntoChunks(List<String> lines, int numChunks) {
        List<List<String>> chunks = new ArrayList<>();
        int chunkSize = (int) Math.ceil((double) lines.size() / numChunks);
        for (int i = 0; i < lines.size(); i += chunkSize) {
            chunks.add(lines.subList(i, Math.min(lines.size(), i + chunkSize)));
        }
        return chunks;
    }
}
