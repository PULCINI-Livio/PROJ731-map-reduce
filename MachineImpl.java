import java.util.Map;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Map<String, Integer>> multiMachinesMap(List<String> inputs) throws RemoteException {
        // On récupère l'ensemble des maps dans une liste
        List<Map<String, Integer>> listOfMaps = new ArrayList<>();

        for (String input : inputs) {
            Map<String, Integer> result = Mapper.map(input);
            listOfMaps.add(result);
        }

        return listOfMaps;
    }
}

