import edu.princeton.cs.algs4.Graph;

/**
 * Created by Pati on 05.12.2016.
 */
public class MyGraph {
    boolean directed;
    boolean empty;
    Object graph;

    public MyGraph(boolean directed, boolean empty, Object graph) {
        this.directed = directed;
        this.empty = empty;
        this.graph = graph;
    }


    public boolean isDirected() {
        return directed;
    }

    public boolean isEmpty(){
        return empty;
    }

    public <T extends Object> T get (Class T){
        return (T) graph;
    }
}
