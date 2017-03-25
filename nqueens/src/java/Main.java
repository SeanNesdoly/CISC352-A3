package nqueens;

/*
 * The main driver class for running the N-Queens problem.
 * Chess board sizes are read from the input file line-by-line. Each one is run as an
 * instance of the NQueens problem with solution arrays being written to both the
 * "nqueens_out.txt" file & the standard output stream.
 *
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 22nd, 2017
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        // Run N-Queens problem
        System.out.println("Running N-Queens\n============================");

        try {
            TextFile file = new TextFile();

            String solution = "";
            for (String line : file.getContents()) {
                if (line.isEmpty())
                    continue;

                int n = Integer.parseInt(line);
                NQueens nq = new NQueens(n);

                nq.createInitialBoard();
                int iterations = 0;
                boolean foundSolution = nq.isSolution();
                while (!foundSolution) {
                    if (iterations > NQueens.THRESHOLD) {
                        nq.createInitialBoard();
                        iterations = 0;
                        continue;
                    }

                    nq.repair();
                    foundSolution = nq.isSolution();

                    iterations++;
                }

                if (nq.n < 256)
                    solution += nq.printBoard();

                solution += nq.printColIndices() + "\n\n";
            }

            System.out.println(solution);
            file.writeFile(solution);
        } catch (Exception ex) {
            System.err.println(ex);
        }

        /*// Uncomment below code to check max heap value allocated by jvm
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;
        System.out.println("\n\nMax Memory:" + runtime.maxMemory() / mb);
        */
    }

}
