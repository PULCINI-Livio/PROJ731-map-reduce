import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader implements Runnable {
    private final String filepath;
    private final List<String> inputs = new ArrayList<>();
    private Exception exception = null;

    public FileReader(String filepath) {
        this.filepath = filepath;
    }

    // Méthode appelée lorsque le thread démarre
    @Override
    public void run() {
        try (Scanner scanner = new Scanner(new File(filepath), "UTF-8")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    inputs.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            exception = e; // stocke l'exception pour traitement ultérieur
        }
    }

    // Accès aux lignes lues
    public List<String> getInputs() throws FileNotFoundException {
        if (exception != null) {
            throw new FileNotFoundException("Fichier non trouvé: " + filepath);
        }
        return inputs;
    }
}
