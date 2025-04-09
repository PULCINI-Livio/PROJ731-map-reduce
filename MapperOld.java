public class MapperOld implements Runnable{
    private volatile int value;
    private int threadNumber;

    public MapperOld(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        System.out.println("Mapper " + threadNumber + " est en cours d'exécution.");
        try {
            // Simuler une tâche longue
            Thread.sleep(1000); // Chaque thread dort pendant 1 seconde
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Mapper " + threadNumber + " a terminé.");
    }

    public int getValue() {
        return value;
    }
}
