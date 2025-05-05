import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface Aiguilleur extends Remote {
    int dirigerCommande(int valeur) throws RemoteException;
    
    List<MotOccurence> faisDuMapReduce(String filepath) throws FileNotFoundException, RemoteException;

    ArrayList<List<String>> prepareTexte(String filepath) throws FileNotFoundException, RemoteException;

    ArrayList<String> cutter(String filepath) throws FileNotFoundException, RemoteException;

    ArrayList<List<String>> chunker(ArrayList<String> inputs) throws RemoteException;



}
