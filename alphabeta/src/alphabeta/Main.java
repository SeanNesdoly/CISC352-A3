package alphabeta;

/*
 * The main class for running the AlphaBeta Pruning algorithm.
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
            System.out.print(file.getContents().get(0));
            file.writeFile("hey bud2");
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}
