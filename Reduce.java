public class Reduce implements Runnable{
    private int threadNumber;

    public Reduce(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        System.out.println("Reducer " + threadNumber + " est en cours d'exécution.");
        try {
            // Simuler une tâche longue
            Thread.sleep(1000); // Chaque thread dort pendant 1 seconde
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Reducer " + threadNumber + " a terminé.");
    }

}
