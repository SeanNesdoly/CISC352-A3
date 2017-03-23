package alphabeta;

/*
 * Implementation of the AlphaBeta tree data structure.
 *
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 22nd, 2017
 */

import java.util.*;

public class AlphaBetaTree {

    public static int graphCount = 0; // keep track of the number of graph instances run on a single execution

    public Vertex root; // root vertex of the tree
    public Map<String, Vertex> T; // the graph: maps a vertex name to the vertex object

    private int score;
    private int leaves_touched = 0;

    public AlphaBetaTree(String graph) {
        T = new HashMap<>();
        createGraph(graph);

        score = (int) alpha_beta(root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

        graphCount++; // update graph counter
    }

    private double alpha_beta(Vertex current, double alpha, double beta) {
        if (current.isRoot) {
            alpha = Double.NEGATIVE_INFINITY;
            beta = Double.POSITIVE_INFINITY;
        }

        if (current.isLeaf) {
            leaves_touched++;
            return current.leafValue;
        }

        if (current.isMaxVertex()) {
            for (Vertex child : current.children) {
                alpha = Math.max(alpha, alpha_beta(child, alpha, beta));

                if (alpha >= beta)
                    return alpha;
            }

            return alpha;
        }

        // not a leaf, not a MAX vertex --> has to be a MIN vertex
        for (Vertex child: current.children) {
            beta = Math.min(beta, alpha_beta(child, alpha, beta));

            if (beta <= alpha)
                return beta;
        }

        return beta;
    }

    private void createGraph(String graph) {
        Scanner s = new Scanner(graph);

        // parse vertex set
        String vertex_set = s.next();
        System.out.println("v set: " + vertex_set);

        Scanner scanV = new Scanner(vertex_set.substring(2, vertex_set.length()-2)).useDelimiter("\\),\\(");

        // parse root vertex
        String rstr = scanV.next();
        String[] rtokens = rstr.split(",");
        root = new Vertex(rtokens[0], rtokens[1]);
        T.put(rtokens[0], root);
        root.isRoot = true;

        // parse the rest of the vertex set
        while (scanV.hasNext()) {
            String entry = scanV.next();
            String[] tokens = entry.split(",");
            String name = tokens[0];
            String type = tokens[1];

            Vertex v = new Vertex(name, type);
            T.put(name, v);

            System.out.println(name + ":" + type);
        }

        // parse edge set
        String edge_set = s.next();
        System.out.println("\ne set: " + edge_set);
        System.out.println("\n=======Tree=======");

        Scanner scanE = new Scanner(edge_set.substring(2, edge_set.length()-2)).useDelimiter("\\),\\(");
        while (scanE.hasNext()) {
            String entry = scanE.next();
            String[] tokens = entry.split(",");
            String parent = tokens[0];
            String child = tokens[1];

            // add in the edge by adding a child to the parent vertex
            if (Vertex.isLeafVertex(child)) {
                Vertex leaf = T.get(parent).addLeafVertex(child);
                T.put(child, leaf);
            } else {
                T.get(parent).children.add(T.get(child));
            }
        }

        for (Vertex v : T.values())
            System.out.println(v);

        // clean up resources
        scanE.close();
        scanV.close();
        s.close();
    }

    // convenience method to print the score & number of leaf nodes examined for a graph instance
    @Override
    public String toString() {
        return "Graph " + graphCount + ": Score: " + score + "; Leaf Nodes Examined: " + leaves_touched;
    }

}
