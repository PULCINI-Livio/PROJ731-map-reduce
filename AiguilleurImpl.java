import java.rmi.server.UnicastRemoteObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class AiguilleurImpl extends UnicastRemoteObject implements Aiguilleur {

    private List<Machine> machines;
    private Random rand;

    public AiguilleurImpl(List<Machine> machines) throws RemoteException {
        super();
        this.machines = machines;
        this.rand = new Random();
    }

    public int dirigerCommande(int valeur) throws RemoteException {
        Machine machine = machines.get(rand.nextInt(machines.size()));
        System.out.println("Aiguilleur : envoi de la commande à une machine...");
        return machine.traiter(valeur);
    }

    public List<Map.Entry<String, Integer>> faisDuMapReduce(String filepath) throws FileNotFoundException, RemoteException {
        // On sépare en chunks (liste de liste de string)
        ArrayList<List<String>> chunks = prepareTexte(filepath);
        
        List<Map<String, Integer>> mapRes = new ArrayList<>();

        for (List<String> chunk : chunks) {
            Machine machine = machines.get(rand.nextInt(machines.size()));
            List<Map<String, Integer>> allMap  = machine.multiMachinesMap(chunk);
            for (Map<String, Integer> eachMap : allMap ) {
                mapRes.add(eachMap);
            }
        }

        // Shuffler : Regroupement des résultats
        Map<String, List<Integer>> grouped = Shuffler.shuffleAndSort(mapRes);

        // Réduction : Utilisation du Reducer parallèle pour l'étape de réduction
        List<Map.Entry<String, Integer>> result = Reducer.reduce(grouped);
        
        return result;
    }

    /*
     * Prend en entrée le chemin du fichier .txt
     * 
     * Retourne une liste de chunks qui sont des listes de string prêtes à etre envoyé aux machines
     */
    public ArrayList<List<String>> prepareTexte(String filepath) throws FileNotFoundException, RemoteException{
        // Decoupage du fichier en liste de string
        ArrayList<String> texte = cutter(filepath);
        // Découpage de la liste de string en plusieurs chunks
        ArrayList<List<String>> res = chunker(texte);
        return res;
    }

    /*
     * Prend en entrée le chemin du fichier .txt
     * 
     * Retourne la liste de string qui correspond aux lignes du fichier .txt
     */
    public ArrayList<String> cutter(String filepath) throws FileNotFoundException{
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


    /*
     * Prend en entrée la liste de string qui correspond aux lignes du fichier .txt
     * 
     * Retourne une liste de chunks qui sont des listes de string
     */
    public ArrayList<List<String>> chunker(ArrayList<String> inputs) throws RemoteException {
        // Découper la liste en morceaux de taille "chunkSize"
        int totalLines = inputs.size();
        int chunkSize = (int) Math.ceil((double) totalLines / machines.size());

        ArrayList<List<String>> chunks = new ArrayList<>();

        for (int i = 0; i < totalLines; i += chunkSize) {
            int start = i;
            int end = Math.min(i + chunkSize, totalLines);
            List<String> chunk = inputs.subList(start, end);
            chunks.add(chunk);
        }
        return chunks;
    }
}
