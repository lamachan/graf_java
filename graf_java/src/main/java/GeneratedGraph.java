public interface GeneratedGraph {
    public int getGraphSize();

    public int getCurrentRow(int index);

    public int getCurrentColumn(int index);

    public void generateGraph();

    public void splitGraph();

    public void writeGraph();

    public void printGraph();

}