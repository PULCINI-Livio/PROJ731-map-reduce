import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        String filepath = "data/LesMiserables.txt";
        System.out.println(filepath);
        List<Map.Entry<String, Integer>> result = MapReduce.mapReduce(filepath);
        for (Map.Entry<String, Integer> entry : result) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}