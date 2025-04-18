import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Aiguilleur extends Remote {
    int dirigerCommande(int valeur) throws RemoteException;
}
