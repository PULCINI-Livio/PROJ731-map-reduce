import java.rmi.server.UnicastRemoteObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class AiguilleurImpl extends UnicastRemoteObject implements Aiguilleur {

    private List<Machine> machines;
    private Random rand;
    private int machineIndex;

    public AiguilleurImpl(List<Machine> machines) throws RemoteException {
        super();
        this.machines = machines;
        this.rand = new Random();
        this.machineIndex = 0;
    }

    public int dirigerCommande(int valeur) throws RemoteException {
        Machine machine = machines.get(rand.nextInt(machines.size()));
        System.out.println("Aiguilleur : envoi de la commande à une machine...");
        return machine.traiter(valeur);
    }

    public List<MotOccurence> faisDuMapReduce(String filepath) throws RemoteException, FileNotFoundException {
        ArrayList<List<String>> chunks = prepareTexte(filepath);
        List<Map<String, Integer>> mapRes = new ArrayList<>();

        // Créer un pool de threads avec ExecutorService pour exécuter les tâches en parallèle
        ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(machines.size());

        // Liste de futures pour récupérer les résultats des tâches
        List<Future<List<Map<String, Integer>>>> futures = new ArrayList<>();

        // Pour chaque chunk, créer un callable qui sera exécuté dans un thread séparé
        for (List<String> chunk : chunks) {
            // Utiliser l'index cyclique pour distribuer les chunks aux machines
            Machine machine = machines.get(machineIndex);
            System.out.println("La machine " + machine.getNom() + " travaille...");

            // Créer un callable pour chaque tâche à exécuter
            Callable<List<Map<String, Integer>>> task = () -> {
                return machine.multiMachinesMap(chunk);
            };

            // Soumettre le callable au pool de threads
            futures.add(executor.submit(task));

            // Passer à la machine suivante (cyclique)
            machineIndex = (machineIndex + 1) % machines.size();
        }

        // Récupérer les résultats des futures
        for (Future<List<Map<String, Integer>>> future : futures) {
            try {
                List<Map<String, Integer>> allMap = future.get(); // Attendre le résultat
                mapRes.addAll(allMap);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Regrouper les résultats par clé
        Map<String, List<Integer>> grouped = Shuffler.shuffleAndSort(mapRes);

        // Réduction des résultats
        List<Map.Entry<String, Integer>> reduced = Reducer.reduce(grouped);

        // Conversion vers objets sérialisables
        List<MotOccurence> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : reduced) {
            result.add(new MotOccurence(entry.getKey(), entry.getValue()));
        }

        // Arrêter le pool de threads
        executor.shutdown();

        return result;
    }

    public ArrayList<List<String>> prepareTexte(String filepath) throws RemoteException, FileNotFoundException {
        ArrayList<String> texte = cutter(filepath);
        return chunker(texte);
    }

    public ArrayList<String> cutter(String filepath) throws RemoteException, FileNotFoundException {
        ArrayList<String> res = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filepath), "utf-8")) {
            while (scanner.hasNextLine()) {
                res.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filepath);
            throw e;
        }
        return res;
    }

    public ArrayList<List<String>> chunker(ArrayList<String> inputs) throws RemoteException {
        int totalLines = inputs.size();
        int chunkSize = (int) Math.ceil((double) totalLines / machines.size());

        ArrayList<List<String>> chunks = new ArrayList<>();
        for (int i = 0; i < totalLines; i += chunkSize) {
            int end = Math.min(i + chunkSize, totalLines);
            chunks.add(new ArrayList<>(inputs.subList(i, end)));
        }
        return chunks;
    }
}
