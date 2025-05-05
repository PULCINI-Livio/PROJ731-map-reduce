import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Machine extends Remote {
    String getNom() throws RemoteException;
    int traiter(int valeur) throws RemoteException;
    public Map<String, Integer> machineMap(String input) throws RemoteException;
    public List<Map<String, Integer>> multiMachinesMap(List<String> inputs) throws RemoteException ;
}
