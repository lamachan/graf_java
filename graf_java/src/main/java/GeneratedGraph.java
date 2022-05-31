package App;

import java.io.PrintWriter;

public interface GeneratedGraph {
    int getGraphSize();

    Vertex getVertex(int index);

    void generateGraph();

    void splitGraph();

    void writeGraph(PrintWriter writer);

    void printGraph();

}