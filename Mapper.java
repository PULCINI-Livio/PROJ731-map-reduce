import java.util.Map;
import java.util.HashMap;

public class Mapper {
    public static Map<String, Integer> map(String input) {
        Map<String, Integer> wordCount = new HashMap<>();
        String[] tokens = input.split("(?<=\\p{Punct})|(?=\\p{Punct})|\\s+");
        
        for (String token : tokens) {
            token = token.toLowerCase().trim(); // Convertir en minuscules et supprimer les espaces
            
            if (!token.isEmpty()) { // Ignorer les chaînes vides
                wordCount.put(token, wordCount.getOrDefault(token, 0) + 1);
            }
            
        }
        return wordCount;
    }
}