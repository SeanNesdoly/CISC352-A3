/*
 * The main class for running the following 2 problems:
 *      1. N-Queens
 *      2. AlphaBeta Pruning
 *
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 19th, 2017
 */

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // parse input argument
        int mode = 0;
        try {
            if (args.length != 1)
                throw new NumberFormatException();

            mode = Integer.parseInt(args[0]);

            if (mode < 1 || mode > 2)
                throw new NumberFormatException();
        } catch(NumberFormatException ex) {
            System.out.println("Invalid argument. Enter the desired problem number you would like to run:\n\t1=N-Queens problem\n\t2=AlphaBeta Pruning");
        }

        // Run N-Queens Problem
        if (mode == 1)
        {
            System.out.println("Running N-Queens\n============================");
            TextFile file = new TextFile(TextFile.NQUEENS);

            try {
                ArrayList<String> lines = file.readFile();
            } catch (IOException ex) {
                System.err.println(ex);
            }

        }

        // Run AlphaBeta Pruning
        if (mode == 2)
        {
            System.out.println("Running AlphaBeta Pruning\n============================");
            TextFile file = new TextFile(TextFile.ALPHABETA);

            try {
                ArrayList<String> lines = file.readFile();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }

        // check max heap value allocated by jvm
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;
        System.out.println("\n\nMax Memory:" + runtime.maxMemory() / mb);
    }

}
