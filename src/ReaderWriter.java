import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Graph;

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
    private int sink;
    private String vName;
    private String childName;
    private String sign;
    private MyGraph p1Graph;
    private MyGraph p3Graph;

    //public ArrayList<String> vertices = new ArrayList<>();
    private Set<Integer> redVertices = new HashSet<>();


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

    public MyGraph getP3Graph() {
        return p3Graph;
    }

    public Set<Integer> getRedVertices() {
        return redVertices;
    }

    public int getSource() {
        return source;
    }

    public int getSink() {
        return sink;
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
            if (!sc.hasNext()){
                break;
            }
            String next = sc.next();
            if (next.equals("*")) {
                redVertices.add(i);
                if (!sc.hasNext()){
                    break;
                }
                vName = sc.next();
            } else {
                vName = next;
            }
        }

        source = nameToIndex.get(sourceName);
        sink = nameToIndex.get(sinkName);

        if (sc.hasNext()) {
            sign = sc.next();
            if (sign.equals("--")) {
                Graph g = new Graph(n);
                p1Graph = new MyGraph(false, g);
                p3Graph = new MyGraph(false, null);
                childName = sc.next();
                for (; ; ) {
                    if (!canIgnore(nameToIndex.get(vName), nameToIndex.get(childName))) {
                        g.addEdge(nameToIndex.get(vName), nameToIndex.get(childName));

                    }

                    if (sc.hasNext()) {
                        vName = sc.next();
                        sc.next();
                        childName = sc.next();
                    } else break;

                }

            } else {
                Digraph g1 = new Digraph(n);
                EdgeWeightedDigraph g3 = new EdgeWeightedDigraph(n);

                p1Graph = new MyGraph(true, g1);
                p3Graph = new MyGraph(true, g3);
                childName = sc.next();

                for (; ; ) {
                    if (!canIgnore(nameToIndex.get(vName), nameToIndex.get(childName))) {
                        g1.addEdge(nameToIndex.get(vName), nameToIndex.get(childName));

                    }
                    DirectedEdge g3edge = new DirectedEdge(nameToIndex.get(vName), nameToIndex.get(childName),
                            redVertices.contains(nameToIndex.get(childName))? -1 : 0);

                    g3.addEdge(g3edge);

                    if (sc.hasNext()) {
                        vName = sc.next();
                        sc.next();
                        childName = sc.next();
                    } else break;
                }

            }
        }
        else {
            p1Graph = new MyGraph(false, new Graph(0));
        }
    }
}
