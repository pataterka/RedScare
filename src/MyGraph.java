import edu.princeton.cs.algs4.Graph;

/**
 * Created by Pati on 05.12.2016.
 */
public class MyGraph {
    boolean directed;
    Object graph;

    public MyGraph(boolean directed, Object graph) {
        this.directed = directed;
        this.graph = graph;
    }

    public boolean isDirected() {
        return directed;
    }

    public <T extends Object> T get (Class T){
        return (T) graph;
    }
}
