import java.io.Serializable;

public class MotOccurence implements Serializable {
    public String mot;
    public int count;

    public MotOccurence(String mot, int count) {
        this.mot = mot;
        this.count = count;
    }

    @Override
    public String toString() {
        return mot + " : " + count;
    }
}
