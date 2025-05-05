import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class ServeurPrincipal {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);

            List<Machine> machines = new ArrayList<>();
            machines.add(new MachineImpl("M1"));
            machines.add(new MachineImpl("M2"));
            machines.add(new MachineImpl("M3"));

            Aiguilleur aiguilleur = new AiguilleurImpl(machines);
            registry.rebind("AiguilleurService", aiguilleur);

            System.out.println("Serveur prêt avec n machines !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
