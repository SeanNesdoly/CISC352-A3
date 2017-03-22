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

public class Main {

    public static void main(String[] args) {
        // Run N-Queens problem
        System.out.println("Running N-Queens\n============================");

        try {
            TextFile file = new TextFile();
            System.out.print(file.getContents().get(0));
            file.writeFile("hey bud1");
        } catch (IOException ex) {
            System.err.println(ex);
        }

        // check max heap value allocated by jvm
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;
        System.out.println("\n\nMax Memory:" + runtime.maxMemory() / mb);
    }

}
