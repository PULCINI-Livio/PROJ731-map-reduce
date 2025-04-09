import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MapReduce {
    public static List<Map.Entry<String, Integer>> mapReduce(String filepath) throws FileNotFoundException {
        ArrayList <String> inputs= new ArrayList<>();
        Scanner scanner = new Scanner(new File(filepath));
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }
        

        List<Map<String, Integer>> mapped = new ArrayList<>();
        for (String input : inputs) {
            mapped.add(Mapper.map(input));
        }

        Map<String, List<Integer>> grouped = Shuffler.shuffleAndSort(mapped);

        return Reducer.reduce(grouped);
    }
}