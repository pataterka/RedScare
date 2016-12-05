import edu.princeton.cs.algs4.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Pati on 05.12.2016.
 */
public class RedScare {

    public static void main(String[] args) {


        List<String> allFiles = getInputs();
        List<String> results = new ArrayList<>();

        for (int i = 0; i < allFiles.size(); i++) {
            // System.out.print(allFiles.get(i));
            ReaderWriter rw = new ReaderWriter("data/" + allFiles.get(i));


            results.add(allFiles.get(i) + "\t" + rw.getN() + "\t" + alternate(rw) + "\t" + few(rw) + "\t" + none(rw) + "\t" + many(rw) + "\t" + some(rw));


        }
        results.forEach(System.out::println);
        results.forEach(line -> {

            if (Integer.parseInt(line.split("\t")[1])< 500){
                return ;
            }
            line = line.replace("_", "\\_");
            line = line.replace("\t", " & ");
            System.out.println(line + "\\\\");
        });


    }

    public static String none(ReaderWriter rw) {

        if (rw.getP1Graph().isEmpty()) return "-";

        if (rw.getP1Graph().isDirected()) {

            BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(rw.getP1Graph().get(Graph.class), rw.getSource());
            if (bfs.hasPathTo(rw.getSink())) {
                return String.valueOf(bfs.distTo(rw.getSink()));
            }
        } else {

            BreadthFirstPaths bfs = new BreadthFirstPaths(rw.getP1Graph().get(Digraph.class), rw.getSource());
            if (bfs.hasPathTo(rw.getSink())) {
                return String.valueOf(bfs.distTo(rw.getSink()));
            }
        }
        return "-";
    }

    public static String some(ReaderWriter rw) {

        if (!rw.getP2Graph().isEmpty() && !rw.getP2Graph().isDirected()) {

            try {
                for (Integer red : rw.getRedVertices()) {

                    FlowNetwork g = rw.getP2Graph().get(FlowNetwork.class);
                    Iterator<FlowEdge> iterator = g.edges().iterator();
                    FlowNetwork gCopy = new FlowNetwork(g.V());
                    while (iterator.hasNext()) {
                        gCopy.addEdge(iterator.next());
                    }

                    FlowEdge e = new FlowEdge(red, rw.getP2newSink(), 2);
                    gCopy.addEdge(e);

                    FordFulkerson ff = new FordFulkerson(gCopy,
                            rw.getP2newSource(), rw.getP2newSink());

                    if (Math.round(ff.value()) == 2) {
                        return "true";
                    }

                }
            } catch (RuntimeException e) {

            }
        } else {
            return "?";
        }
        return "false";
    }


    public static String many(ReaderWriter rw) {
        if (rw.getP1Graph().isDirected()) {

            BellmanFordSP bf = new BellmanFordSP(rw.getP3Graph().get(EdgeWeightedDigraph.class), rw.getSource());
            if (!bf.hasNegativeCycle()) {
                if (bf.hasPathTo(rw.getSink())) {
                    Iterator<DirectedEdge> iterator = bf.pathTo(rw.getSink()).iterator();
                    int countRed = getCountRed(rw, iterator);
                    return String.valueOf(countRed);
                } else {
                    return "-";
                }
            }


        }

        return "?";

    }

    private static int getCountRed(ReaderWriter rw, Iterator<DirectedEdge> iterator) {
        Set<Integer> redSet = new HashSet<>();

        while (iterator.hasNext()) {
            DirectedEdge next = iterator.next();
            int nodeIndex = next.from();
            if (rw.getRedVertices().contains(nodeIndex) && !redSet.contains(nodeIndex)) {
                redSet.add(nodeIndex);
            }
            nodeIndex = next.to();
            if (rw.getRedVertices().contains(nodeIndex) && !redSet.contains(nodeIndex)) {
                redSet.add(nodeIndex);

            }
        }
        return redSet.size();
    }

    private static int getCountRedUndir(ReaderWriter rw, Iterator<Edge> iterator) {
        Set<Integer> redSet = new HashSet<>();

        while (iterator.hasNext()) {
            Edge next = iterator.next();
            int nodeIndex = next.either();
            if (rw.getRedVertices().contains(nodeIndex) && !redSet.contains(nodeIndex)) {
                redSet.add(nodeIndex);
            }
            nodeIndex = next.other(nodeIndex);
            if (rw.getRedVertices().contains(nodeIndex) && !redSet.contains(nodeIndex)) {
                redSet.add(nodeIndex);

            }
        }
        return redSet.size();
    }

    public static String few(ReaderWriter rw) {
        if (rw.getP4Graph().isEmpty()) {
            return "-";
        }
        if (rw.getP4Graph().isDirected()) {
            DijkstraSP d = new DijkstraSP(rw.getP4Graph().get(EdgeWeightedDigraph.class), rw.getSource());

            if (d.hasPathTo(rw.getSink())) {
                Iterator<DirectedEdge> iterator = d.pathTo(rw.getSink()).iterator();
                int countRed = getCountRed(rw, iterator);
                return String.valueOf(countRed);
            }
        } else {
            DijkstraUndirectedSP d = new DijkstraUndirectedSP(rw.getP4Graph().get(EdgeWeightedGraph.class), rw.getSource());

            if (d.hasPathTo(rw.getSink())) {
                Iterator<Edge> iterator = d.pathTo(rw.getSink()).iterator();
                int countRed = getCountRedUndir(rw, iterator);
                return String.valueOf(countRed);
            }

        }

        return "-";

    }

    public static String alternate(ReaderWriter rw) {
        if (rw.getP5Graph().isEmpty()) return "false";

        if (rw.getP5Graph().isDirected()) {

            BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(rw.getP5Graph().get(Graph.class), rw.getSource());
            if (bfs.hasPathTo(rw.getSink())) {
                return "true";
            }
        } else {

            BreadthFirstPaths bfs = new BreadthFirstPaths(rw.getP5Graph().get(Digraph.class), rw.getSource());
            if (bfs.hasPathTo(rw.getSink())) {
                return "true";
            }
        }
        return "false";
    }


    public static List<String> getInputs() {
        try {
            Path p = Paths.get(RedScare.class.getResource("data").toURI());
            return Files.list(p)
                    .map((Path x) -> x.toFile().getName())
                    .sorted()
                    .collect(Collectors.toList());
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(null, e);
        }
    }
}
