import java.util.*;
import java.util.concurrent.*;

public class ReducerParallel {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public static List<Map.Entry<String, Integer>> reduce(Map<String, List<Integer>> grouped)
            throws InterruptedException, ExecutionException {

        ExecutorService reducePool = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Map.Entry<String, Integer>>> futures = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : grouped.entrySet()) {
            final String key = entry.getKey();
            final List<Integer> values = entry.getValue();

            Callable<Map.Entry<String, Integer>> task = () -> {
                int sum = values.stream().mapToInt(Integer::intValue).sum();
                return new AbstractMap.SimpleEntry<>(key, sum);
            };

            futures.add(reducePool.submit(task));
        }

        List<Map.Entry<String, Integer>> result = new ArrayList<>();
        for (Future<Map.Entry<String, Integer>> future : futures) {
            result.add(future.get());
        }

        reducePool.shutdown();
        reducePool.awaitTermination(1, TimeUnit.MINUTES);

        result.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return result;
    }
}
