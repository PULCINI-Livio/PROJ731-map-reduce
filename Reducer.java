import java.util.*;

public class Reducer {
    public static List<Map.Entry<String, Integer>> reduce(Map<String, List<Integer>> grouped) {
        Map<String, Integer> reduced = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : grouped.entrySet()) {
            reduced.put(entry.getKey(), entry.getValue().stream().mapToInt(Integer::intValue).sum());
        }

        List<Map.Entry<String, Integer>> result = new ArrayList<>(reduced.entrySet());
        result.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return result;
    }
}
