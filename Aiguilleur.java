import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Aiguilleur extends Remote {
    int dirigerCommande(int valeur) throws RemoteException;

    List<Map.Entry<String, Integer>> faisDuMapReduce(String filepath) throws FileNotFoundException, RemoteException;

    ArrayList<List<String>> prepareTexte(String filepath) throws FileNotFoundException, RemoteException;

    ArrayList<String> cutter(String filepath) throws FileNotFoundException;

    ArrayList<List<String>> chunker(ArrayList<String> inputs) throws RemoteException;



}
