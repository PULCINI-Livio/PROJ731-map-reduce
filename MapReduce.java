import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MapReduce {
    public static List<Map.Entry<String, Integer>> mapReduce(String filepath) throws FileNotFoundException, IOException {
        ArrayList <String> inputs= new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filepath), StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
               inputs.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filepath);
            throw e;
        }

        List<Map<String, Integer>> mapped = new ArrayList<>();
        for (String input : inputs) {
            mapped.add(Mapper.map(input));
        }

        Map<String, List<Integer>> grouped = Shuffler.shuffleAndSort(mapped);

        return Reducer.reduce(grouped);
    }
}