import java.util.Map;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Machine extends Remote {
    int traiter(int valeur) throws RemoteException;

    Map<String, Integer> machineMap(String input) throws RemoteException;
}
