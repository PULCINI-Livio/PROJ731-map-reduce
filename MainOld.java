public class MainOld {
    public static void main(String[] args) {
        int numberOfThreads = 5;
        Thread[] threads = new Thread[numberOfThreads]; // Tableau pour garder une référence à tous les threads
        
        // Lancer tous les threads
        for (int i = 0; i < numberOfThreads; i++) {
            Runnable mapper = new Mapper(i);
            threads[i] = new Thread(mapper);
            threads[i].start();  // Démarrer chaque thread
        }
        
        // Attendre que tous les threads aient terminé
        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();  // Attendre la fin de chaque thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        Runnable reduce = new Reducer(1);
        Thread reducer = new Thread(reduce);
        reducer.start();
        try {
            reducer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Tous les threads sont terminés.");
    }
}
