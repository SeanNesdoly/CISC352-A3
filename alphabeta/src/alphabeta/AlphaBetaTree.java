package alphabeta;

/*
 * Implementation of the AlphaBeta tree data structure.
 *
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 22nd, 2017
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class AlphaBetaTree {

    public static int graphCount = 0; // keep track of the number of graph instances run on a single execution

    public Vertex root; // root vertex of the tree
    public Map<String, Vertex> V; // mapping of vertex names to vertex objects for parsing

    private int score; // computed score of the game
    private int leaves_touched = 0; // numbers of leaves examined during the alphabeta tree traversal

    public AlphaBetaTree(String graph) {
        V = new HashMap<>(); // vertex name to Vertex object mapping
        createGraph(graph);

        // alpha = -infinity, beta = +infinity for initial recursive call
        score = (int) alpha_beta(root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

        graphCount++; // update graph counter for multi-graph runs
    }

    // AlphaBeta Pruning algorithm implementation
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
                    return alpha; // prune branch
            }
            return alpha;
        }

        // not a leaf vertex && not a MAX vertex implies that this must be a MIN vertex
        for (Vertex child: current.children) {
            beta = Math.min(beta, alpha_beta(child, alpha, beta));

            if (beta <= alpha)
                return beta; // prune branch
        }
        return beta;
    }

    // Creates a graph from an input string. The first set parsed is the vertex set, where each
    // vertex is defined as being of type MAX or MIN. The leaf vertices are not included in the
    // vertex set and are identified by a number. The second set parsed is the edge set. From the
    // vertex & edge sets, a tree is built.
    private void createGraph(String graph) {
        Scanner s = new Scanner(graph);

        // parse vertex set
        String vertex_set = s.next();
        Scanner scanV = new Scanner(vertex_set.substring(2, vertex_set.length()-2)).useDelimiter("\\),\\(");

        // parse root vertex
        String rstr = scanV.next();
        String[] rtokens = rstr.split(",");
        root = new Vertex(rtokens[0], rtokens[1]);
        V.put(rtokens[0], root);
        root.isRoot = true;

        // parse the rest of the vertex set
        while (scanV.hasNext()) {
            String entry = scanV.next();
            String[] tokens = entry.split(",");
            String name = tokens[0];
            String type = tokens[1];

            Vertex v = new Vertex(name, type);
            V.put(name, v);
        }

        // parse edge set
        String edge_set = s.next();
        Scanner scanE = new Scanner(edge_set.substring(2, edge_set.length()-2)).useDelimiter("\\),\\(");

        while (scanE.hasNext()) {
            String entry = scanE.next();
            String[] tokens = entry.split(",");
            String parent = tokens[0];
            String child = tokens[1];

            // add in the edge by adding a child to the parent vertex
            if (Vertex.isLeafVertex(child)) {
                Vertex leaf = V.get(parent).addLeafVertex(child);
                V.put(child, leaf);
            } else {
                V.get(parent).children.add(V.get(child));
            }
        }

        // clean up resources
        scanE.close();
        scanV.close();
        s.close();
    }

    // convenience method to print the score & number of leaf nodes examined by the alphabeta algorithm for a graph instance
    @Override
    public String toString() {
        return "Graph " + graphCount + ": Score: " + score + "; Leaf Nodes Examined: " + leaves_touched;
    }

}
