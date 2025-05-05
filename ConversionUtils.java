import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConversionUtils {
    public static List<Map.Entry<String, Integer>> toEntryList(List<MotOccurence> liste) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>();
        for (MotOccurence mo : liste) {
            entries.add(new AbstractMap.SimpleEntry<>(mo.mot, mo.count));
        }
        return entries;
    }
}
