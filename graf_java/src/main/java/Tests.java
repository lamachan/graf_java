public class Tests {
    public static void main(String[] args) {
        // random graph generation test
        GeneratedGraph g = new Graph(3, 4, 0, 10);
        g.generateGraph();
        g.printGraph();
        Connectivity bfs = new Connectivity((Graph) g);
        if(bfs.isConnected((Graph) g)) {
            System.out.println("The graph is connected.");
        } else {
            System.out.println("The graph is disconnected.");
        }


    }
}
