import java.util.Random;

import java.io.FileNotFoundException;
import java.io.*;
import java.util.regex.*;

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

    public Vertex getVertex(int index) {
        return v[index];
    }
    @Override
    public void generateGraph() {
        double w = 0.0;
        for(int i = 0; i < getGraphSize(); i++) {
            if((getCurrentRow(i) != 0) && !(v[i].hasNeighbour(Vertex.UPPER))) {
                w = getRandomWeight();
                v[i].setNeighbour(Vertex.UPPER, i - columns, w);
                v[i - columns].setNeighbour(Vertex.LOWER, i, w);
            }
            if((getCurrentColumn(i) != 0) && !(v[i].hasNeighbour(Vertex.LEFT))) {
                w = getRandomWeight();
                v[i].setNeighbour(Vertex.LEFT, i - 1, w);
                v[i - 1].setNeighbour(Vertex.RIGHT, i, w);
            }
            if((getCurrentColumn(i) != (columns - 1)) && !(v[i].hasNeighbour(Vertex.RIGHT))) {
                w = getRandomWeight();
                v[i].setNeighbour(Vertex.RIGHT, i + 1, w);
                v[i + 1].setNeighbour(Vertex.LEFT, i, w);
            }
            if((getCurrentRow(i) != (rows - 1)) && !(v[i].hasNeighbour(Vertex.LOWER))) {
                w = getRandomWeight();
                v[i].setNeighbour(Vertex.LOWER, i + columns, w);
                v[i + columns].setNeighbour(Vertex.UPPER, i, w);
            }
        }
    }

    @Override
    public void splitGraph() {
        // to be implemented
    }

    @Override
    public void readGraph() {
        String filename = "graph.txt";
        FileReader file = null;     //try to read file
        try {
            file = new FileReader(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader buffer = new BufferedReader(file);
        String line = null;     //try to read first line
        try {
            line = buffer.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Pattern pattern= Pattern.compile("\\d+ \\d+");      //finding pattern for rows columns
        Matcher matcher = pattern.matcher(line);
        if(matcher.find()) {
            String[] parts = line.split(" ");
            int rows = Integer.parseInt(parts[0]);
            int columns = Integer.parseInt(parts[1]);
            if(rows*columns > 1000000 || rows*columns <= 0 ) {
                //error
            } else {
                Graph graph = new Graph(rows, columns);
                int size;
                int vertex;
                double weight;
                Pattern pattern2= Pattern.compile("\\d+");          //pattern for vertex
                Matcher matcher2;
                Pattern pattern3= Pattern.compile(":\\d+.\\d+");      //pattern for weight
                Matcher matcher3;
                for(int i = 0; i < getGraphSize(); i++){
                    try {
                        line = buffer.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    while ( line != null){
                        line = line.replace("\t", "");
                        line = line.replace("\n", "");
                        parts = line.split(" ");
                        for (int j = 0; j < parts.length; j++) {
                            matcher2 = pattern2.matcher(parts[j]);
                            vertex = Integer.parseInt(parts[j]);
                            matcher3 = pattern3.matcher(parts[j++]);
                            weight = Double.parseDouble(parts[j]);
                            if (matcher2.find() && matcher3.find()) {   //i - wierzcholek w grafie vertex - wiecholek sasiada
                                if (add_neighbour (graph, vertex, weight, i)  == -1)
                                    System.out.print("Error");  //Add errors
                            }
                            else
                                System.out.print("Error");      //Add errors

                        }
                    }
                }

            }

        } else
            System.out.print("Error");      //Add errors


    }


    //@Override
    public int add_neighbour (Graph graph, int vertex, double weight, int i) {
        if (vertex < 0 || vertex >= getGraphSize() || vertex == i || weight <= 0) {
            return -1;
        }
        if (vertex == (i - graph.columns)) {
            v[i].setNeighbour(Vertex.UPPER, vertex, weight);
            return 0;
        }
        if (vertex == (i - 1)) {
            v[i].setNeighbour(Vertex.LEFT, vertex, weight);
            return 0;
        }
        if (vertex == (i + 1)) {
            v[i].setNeighbour(Vertex.RIGHT, vertex, weight);
            return 0;
        }
        if (vertex == (i + graph.columns)) {
            v[i].setNeighbour(Vertex.LOWER, vertex, weight);
            return 0;
        }
        return -1;
    }

    @Override
    public void writeGraph() {
        String file = "file.txt";
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        writer.println(rows + " " + columns);
        for(int i = 0; i < getGraphSize(); i++) {
            writer.println("\t" + v[i]);
        }

        writer.close();
    }

    @Override
    public void printGraph() {
        System.out.println("rows = " + rows + " columns = " + columns);
        //System.out.println("w1 = " + weightLower + " w2 = " + weightUpper);
        for(int i = 0; i < getGraphSize(); i++) {
            System.out.println(v[i]);
        }
    }
}
