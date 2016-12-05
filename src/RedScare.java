import edu.princeton.cs.algs4.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Pati on 05.12.2016.
 */
public class RedScare {

    public static void main(String[] args) {


        List<String> allFiles = getInputs();
        for (int i = 0; i < allFiles.size(); i++) {
            System.out.print(allFiles.get(i));
            ReaderWriter rw = new ReaderWriter("data/" + allFiles.get(i));
            //none(rw);

            System.out.println("\t"+ none(rw)+" " +many(rw));
        }

    }

    public static String none(ReaderWriter rw) {
        try {
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


        } catch (RuntimeException e) {

        }
        return "-";
    }

    public static String many(ReaderWriter rw) {
        if (rw.getP1Graph().isDirected()) {

            BellmanFordSP bf = new BellmanFordSP(rw.getP3Graph().get(EdgeWeightedDigraph.class), rw.getSource());
            if (!bf.hasNegativeCycle()) {
                if (bf.hasPathTo(rw.getSink())) {
                    Iterable<DirectedEdge> path = bf.pathTo(rw.getSink());
                    Iterator<DirectedEdge> iterator = path.iterator();
                    int countRed = 0;
                    while (iterator.hasNext()) {
                        if (rw.getRedVertices().contains(iterator.next())) {
                            countRed++;
                        }
                    }
                    return String.valueOf(countRed);
                } else {
                    return "-";
                }
            }


        }

        return "?";

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
