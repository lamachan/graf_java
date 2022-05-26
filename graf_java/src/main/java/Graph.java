import java.util.Random;

public class Graph implements GeneratedGraph, ReadGraph {
    private final int rows;
    private final int columns;
    private Vertex [] v;
    private final double weightLower;
    private final double weightUpper;
    private final Random r = new Random();

    public Graph(int rows, int columns, int w1, int w2) {
        // generated randomly
        this.rows = rows;
        this.columns = columns;
        this.weightLower = w1;
        this.weightUpper = w2;
        v = new Vertex[rows * columns];
        for(int i = 0; i < getGraphSize(); i++)
            v[i] = new Vertex(i);
    }

    // maybe differentiate the generated graph from the read graph with interfaces???
    public Graph(int rows, int columns) {
        // read from a file
        this.rows = rows;
        this.columns = columns;
        // not sure what to do with the weight range yet
        // how to determine it? needed for the rainbow edges
        // maybe find the min and max weight and round to the closest integer (floor for min, ceiling for max)
        this.weightLower = -1;
        this.weightUpper = -1;
        v = new Vertex[rows * columns];
        for(int i = 0; i < getGraphSize(); i++)
            v[i] = new Vertex(i);
    }

    @Override
    public int getGraphSize() {
        return rows * columns;
    }

    @Override
    public int getCurrentRow(int index) {
        return index / columns;
    }

    @Override
    public int getCurrentColumn(int index) {
        return index % columns;
    }

    private double getRandomWeight() {
        return weightLower + (weightUpper - weightLower) * r.nextDouble();
    }

    @Override
    public void generateGraph() {
        double w = 0.0;
        for(int i = 0; i < getGraphSize(); i++) {
            if((getCurrentRow(i) != 0) && (v[i].hasNeighbour(0))) {
                w = getRandomWeight();
                v[i].setNeighbour(0, i - columns, w);
                v[i - columns].setNeighbour(3, i, w);
            }
            if((getCurrentColumn(i) != 0) && (v[i].hasNeighbour(1))) {
                w = getRandomWeight();
                v[i].setNeighbour(1, i - 1, w);
                v[i - 1].setNeighbour(2, i, w);
            }
            if((getCurrentColumn(i) != (columns - 1)) && (v[i].hasNeighbour(2))) {
                w = getRandomWeight();
                v[i].setNeighbour(2, i + 1, w);
                v[i + 1].setNeighbour(1, i, w);
            }
            if((getCurrentRow(i) != (rows - 1)) && (v[i].hasNeighbour(3))) {
                w = getRandomWeight();
                v[i].setNeighbour(3, i + columns, w);
                v[i + columns].setNeighbour(0, i, w);
            }
        }
    }

    @Override
    public void splitGraph() {
        // to be implemented
    }

    @Override
    public void readGraph() {
        // to be implemented
    }

    @Override
    public void writeGraph() {
        // to be implemented
    }
}
