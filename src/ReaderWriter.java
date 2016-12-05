import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Pati on 05.12.2016.
 */
public class ReaderWriter {
    private Scanner sc;
    public int n;
    public int m;
    public int r;
    public String source;
    public String sink;
    public String vName;
    public String childName;
    public String sign;
    public List<String> edgesFrom = new ArrayList<>();
    public List<String> edgesTo = new ArrayList<>();


    public ArrayList<String> vertices = new ArrayList<>();
    public ArrayList<String> redVertices = new ArrayList<>();
    //public Graph g;

    public ReaderWriter(String filePath) {
        try {
            sc = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find the file specified. Exiting...");
            System.exit(0);
        }

        initialize();
    }

    public void initialize() {


        n = sc.nextInt();
        m = sc.nextInt();
        r = sc.nextInt();
        source = sc.next();
        sink = sc.next();

        vName = sc.next();
        Map <String, Integer> nameToIndex = new HashMap<>();

        for (int i = 0; i < n; i++) {
            nameToIndex.put(vName, i);

            vertices.add(vName);
            String next = sc.next();
            if (next.equals("*")) {
                redVertices.add(vName);
                vName = sc.next();
            } else {
                vName = next;
            }
        }

        if (sc.hasNext()) {
            sign = sc.next();
            if (sign.equals("--")) {
                Graph g = new Graph(n);
                childName = sc.next();
                for (;;){
                    g.addEdge(nameToIndex.get(vName), nameToIndex.get(childName));
                    if (sc.hasNext()){
                        vName = sc.next();
                        sc.next();
                        childName = sc.next();
                    }
                    else break;
                }

            }
            else {
                Digraph g = new Digraph(n);
                childName = sc.next();
                for (;;){
                    g.addEdge(nameToIndex.get(vName), nameToIndex.get(childName));
                    if (sc.hasNext()){
                        vName = sc.next();
                        sc.next();
                        childName = sc.next();
                    }
                    else break;
                }

            }
        }
    }}
