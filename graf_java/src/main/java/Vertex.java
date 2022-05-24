import java.util.Arrays;

public class Vertex {
    private final int index;
    private int [] neighbour;
    private double [] weight;

    public Vertex(int index) {
        this.index = index;
        this.neighbour = new int [4];
        Arrays.fill(neighbour, -1);
        this.weight = new double [4];
        Arrays.fill(weight, -1);
    }

    public void addUpperNeighbour() {
    }
}
