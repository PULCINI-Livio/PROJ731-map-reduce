import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

public class Utilisateur {
    public static void main(String[] args) {
        String outputFile = "output/resultat_MapReduce.txt";
        long startTime = System.nanoTime(); // Début du chronométrage

        try {
            

            System.out.println("try connection");

            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Aiguilleur aiguilleur = (Aiguilleur) registry.lookup("AiguilleurService");

            String filepath = "data/Codes.txt";
            System.out.println("try map reduce");

            List<MotOccurence> res = aiguilleur.faisDuMapReduce(filepath);

            long endTime = System.nanoTime(); // Fin
            long duration = (endTime - startTime) / 1_000_000; // en ms

            List<Map.Entry<String,Integer>> resultat = ConversionUtils.toEntryList(res);


            // Écrire le résultat principal
            ResultWriter.writeToFile(resultat, outputFile);

            //System.out.println("Resultat retourne par la machine via l aiguilleur : ça marche");
            //System.out.println("Resultat retourne par la machine via l aiguilleur : " + resultat);
            System.out.println("Temps d'exécution du MapReduce : " + duration + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
