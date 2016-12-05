import edu.princeton.cs.algs4.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by Pati on 05.12.2016.
 */
public class ReaderWriter {
    private Scanner sc;
    private int n;
    private int m;
    private int r;
    private int source;
    private int p2newSource;
    private int p2newSink;
    private int sink;
    private String vName;
    private String childName;
    private String sign;
    private Set<Integer> redVertices = new HashSet<>();
    private MyGraph p1Graph;
    private MyGraph p2Graph;
    private MyGraph p3Graph;
    private MyGraph p4Graph;


    public ReaderWriter(String filePath) {
        try {
            // sc = new Scanner(new File(filePath));
            sc = new Scanner(new File(ReaderWriter.class.getResource(filePath).toURI()));
        } catch (FileNotFoundException | URISyntaxException e) {
            System.out.println("Error: Could not find the file specified. Exiting...");
            System.exit(0);
        }

        initialize();
    }


    public boolean canIgnore(int v, int w) {
        if (redVertices.contains(v) && v != source && v != sink)
            return true;
        else if (redVertices.contains(w) && w != source && w != sink)
            return true;
        else return false;
//        return redVertices.contains(v) && v!= source && v!= sink
//                || redVertices.contains(w) && w!= source && w!= sink
    }

    public MyGraph getP1Graph() {
        return p1Graph;
    }

    public MyGraph getP2Graph() {
        return p2Graph;
    }

    public MyGraph getP3Graph() {
        return p3Graph;
    }

    public MyGraph getP4Graph() {
        return p4Graph;
    }

    public Set<Integer> getRedVertices() {
        return redVertices;
    }

    public int getSource() {
        return source;
    }

    public int getP2newSource() {
        return p2newSource;
    }

    public int getSink() {
        return sink;
    }

    public int getP2newSink() {
        return p2newSink;
    }

    public void initialize() {

        n = sc.nextInt();
        m = sc.nextInt();
        r = sc.nextInt();
        String sourceName = sc.next();
        String sinkName = sc.next();


        Map<String, Integer> nameToIndex = new HashMap<>();
        vName = sc.next();

        for (int i = 0; i < n; i++) {

            nameToIndex.put(vName, i);

            //vertices.add(vName);
            if (!sc.hasNext()) {
                break;
            }
            String next = sc.next();
            if (next.equals("*")) {
                redVertices.add(i);
                if (!sc.hasNext()) {
                    break;
                }
                vName = sc.next();
            } else {
                vName = next;
            }
        }

        source = nameToIndex.get(sourceName);
        sink = nameToIndex.get(sinkName);
        p2newSource = n;
        p2newSink = n + 1;

        if (sc.hasNext()) {
            sign = sc.next();
            if (sign.equals("--")) {

                Graph g = new Graph(n);
                p1Graph = new MyGraph(false, false, g);

                FlowNetwork g2 = new FlowNetwork(n + 2);
                p2Graph = new MyGraph(false, false, g2);

                p3Graph = new MyGraph(false, false, null);

                EdgeWeightedGraph g4 = new EdgeWeightedGraph(n);
                p4Graph = new MyGraph(false, false, g4);


                childName = sc.next();
                for (; ; ) {
                    if (!canIgnore(nameToIndex.get(vName), nameToIndex.get(childName))) {
                        g.addEdge(nameToIndex.get(vName), nameToIndex.get(childName));


                    }

                    FlowEdge g2edge1 = new FlowEdge(nameToIndex.get(vName),
                            nameToIndex.get(childName), 1);
                    FlowEdge g2edge2 = new FlowEdge(nameToIndex.get(childName),
                            nameToIndex.get(vName), 1);
                    g2.addEdge(g2edge1);
                    g2.addEdge(g2edge2);

                    Edge g4edge = new Edge(nameToIndex.get(vName),
                            nameToIndex.get(childName),
                            redVertices.contains(nameToIndex.get(childName)) ? 1 : 0);
                    g4.addEdge(g4edge);

                    if (sc.hasNext()) {
                        vName = sc.next();
                        sc.next();
                        childName = sc.next();
                    } else break;

                }


                FlowEdge e1 = new FlowEdge(p2newSource, source, 1);
                FlowEdge e2 = new FlowEdge(p2newSource, sink, 1);
                g2.addEdge(e1);
                g2.addEdge(e2);


            } else {
                Digraph g1 = new Digraph(n);
                EdgeWeightedDigraph g3 = new EdgeWeightedDigraph(n);
                EdgeWeightedDigraph g4 = new EdgeWeightedDigraph(n);

                p1Graph = new MyGraph(true, false, g1);
                p2Graph = new MyGraph(false, true, new FlowNetwork(0));
                p3Graph = new MyGraph(true, false, g3);
                p4Graph = new MyGraph(true, false, g4);
                childName = sc.next();

                for (; ; ) {
                    if (!canIgnore(nameToIndex.get(vName), nameToIndex.get(childName))) {
                        g1.addEdge(nameToIndex.get(vName), nameToIndex.get(childName));

                    }
                    DirectedEdge g3edge = new DirectedEdge(nameToIndex.get(vName), nameToIndex.get(childName),
                            redVertices.contains(nameToIndex.get(childName)) ? -1 : 0);

                    DirectedEdge g4edge = new DirectedEdge(nameToIndex.get(vName), nameToIndex.get(childName),
                            redVertices.contains(nameToIndex.get(childName)) ? 1 : 0);

                    g3.addEdge(g3edge);
                    g4.addEdge(g4edge);

                    if (sc.hasNext()) {
                        vName = sc.next();
                        sc.next();
                        childName = sc.next();
                    } else break;
                }

            }
        } else {
            p1Graph = new MyGraph(false, true, new Graph(0));
            p2Graph = new MyGraph(false, true, new FlowNetwork(0));
            p4Graph = new MyGraph(false, true, new EdgeWeightedGraph(0));
        }
    }
}
