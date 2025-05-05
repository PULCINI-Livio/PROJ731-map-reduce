import java.util.Map;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class MachineImpl extends UnicastRemoteObject implements Machine {
    private String nom;

    public MachineImpl(String nom) throws RemoteException {
        super();
        this.nom = nom;
    }

    public int traiter(int valeur) throws RemoteException {
        System.out.println("Machine " + nom + " traite la valeur " + valeur);
        return valeur * 2;
    }

    public Map<String, Integer> machineMap(String input) throws RemoteException {
        Map<String, Integer> result = Mapper.map(input);
        return result;
    }
}

