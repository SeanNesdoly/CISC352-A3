package nqueens;

/*
 * The main class for running the N-Queens problem.
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

                solution += nq;
            }

            System.out.println(solution);
            file.writeFile(solution);
        } catch (Exception ex) {
            System.err.println(ex);
        }

        // check max heap value allocated by jvm
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;
        //System.out.println("\n\nMax Memory:" + runtime.maxMemory() / mb);
    }

}
