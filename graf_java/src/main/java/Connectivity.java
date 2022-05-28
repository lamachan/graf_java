import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Connectivity {
    private int [] colour;
    private static int WHITE = 0;
    private static int GRAY = 1;
    private static int BLACK = 2;

    public Connectivity(Graph g) {
        colour = new int [g.getGraphSize()];
        Arrays.fill(colour, WHITE);
    }

    // not sure where to check the connectivity
    private void bfs(Graph g, int startVertex) {
        //Arrays.fill(colour, WHITE);
        colour[startVertex] = GRAY;

        Queue<Integer> q = new LinkedList<>();
        q.add(startVertex);

        while(!q.isEmpty()) {
            int currentVertex = q.remove();
            for(int i = 0; i < 4; i++) {
                int neighbour = g.getVertex(currentVertex).getNeighbour(i);
                if((neighbour != -1) && (colour[neighbour] == WHITE)) {
                    colour[neighbour] = GRAY;
                    q.add(neighbour);
                }
            }
            colour[currentVertex] = BLACK;
        }
    }

    public boolean isConnected(Graph g) {
        // used to check connectivity
        bfs(g, 0);
        for(int i = 0; i < g.getGraphSize(); i++) {
            if(colour[i] != BLACK) {
                return false;
            }
        }
        return true;
    }

    public boolean isConnected(Graph g, int startVertex, int finishVertex) {
        // used to check if 2 chosen vertices are connected (by splitGraph)
        bfs(g, startVertex);
        if(colour[finishVertex] != BLACK) {
            return false;
        }
        return true;
    }
}
