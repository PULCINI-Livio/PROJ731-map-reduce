import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Utilisateur {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Aiguilleur aiguilleur = (Aiguilleur) registry.lookup("AiguilleurService");

            int valeur = 5;
            int resultat = aiguilleur.dirigerCommande(valeur);

            System.out.println("Resultat retourne par la machine via l aiguilleur : " + resultat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
