import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Shuffler {
    public static Map<String, List<Integer>> shuffleAndSort(List<Map<String, Integer>> mapped) {
        Map<String, List<Integer>> grouped = new HashMap<>();
        for (Map<String, Integer> map : mapped) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                grouped.putIfAbsent(entry.getKey(), new ArrayList<>());
                grouped.get(entry.getKey()).add(entry.getValue());
            }
        }
        return grouped;
    }
}