import java.util.Arrays;

public class Vertex {
    private final int index;
    private int [] neighbour = new int [4];
    // 0 -> upper (i-column) neighbour
    // 1 -> left (i-1) neighbour
    // 2 -> right (i+1) neighbour
    // 3 -> lower (i+column) neighbour
    private double [] weight = new double [4];

    public Vertex(int index) {
        this.index = index;
        //this.neighbour = new int [4];
        Arrays.fill(neighbour, -1);
        //this.weight = new double [4];
        Arrays.fill(weight, -1);
    }

    public void setNeighbour(int position, int nIndex, double w) {
        neighbour[position] = nIndex;
        weight[position] = w;
    }

    public void removeNeighbour(int position) {
        neighbour[position] = -1;
        weight[position] = -1;
    }

    public int getNeighbour(int position) {
        return neighbour[position];
    }

    public boolean hasNeighbour(int position) {
        return (neighbour[position] != -1);
    }

    public double getWeight(int position) {
        return weight[position];
    }
}
