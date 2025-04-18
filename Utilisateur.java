import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

public class Utilisateur {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Aiguilleur aiguilleur = (Aiguilleur) registry.lookup("AiguilleurService");
            
            String filepath = "data/rousseau.txt";

            List<Map.Entry<String, Integer>> resultat = aiguilleur.faisDuMapReduce(filepath);

            System.out.println("Resultat retourne par la machine via l aiguilleur : " + resultat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
