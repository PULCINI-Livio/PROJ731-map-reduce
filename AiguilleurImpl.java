import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

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
}
