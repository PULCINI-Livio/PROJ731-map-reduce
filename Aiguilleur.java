import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Aiguilleur extends Remote {
    int dirigerCommande(int valeur) throws RemoteException;

    List<Map.Entry<String, Integer>> faisDuMapReduce(String filepath) throws RemoteException;
}
