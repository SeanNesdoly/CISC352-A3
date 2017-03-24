package alphabeta;

/*
 * The main driver class for running the AlphaBeta Pruning algorithm.
 *
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 22nd, 2017
 */

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Run AlphaBeta Pruning
        System.out.println("Running AlphaBeta Pruning\n============================");

        try {
            TextFile file = new TextFile();

            String out = "";
            for (String line : file.getContents()) {
                // ignore blank lines between input graphs
                if (!line.isEmpty()) {
                    AlphaBetaTree abt = new AlphaBetaTree(line);
                    out += abt + "\n\n";
                    System.out.println(abt);
                }
            }

            file.writeFile(out);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}
