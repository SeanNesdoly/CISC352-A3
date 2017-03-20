/*
 * The main class for running the following 2 problems:
 *      1. N-Queens
 *      2. AlphaBeta Pruning
 *
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 19th, 2017
 */

public class Main {
    
    public static void main(String[] args) {
        int mode = (args.length > 0 ? Integer.parseInt(args[0]) : 0);
        if (mode == 1) {
            System.out.println("Running N-Queens\n============================");
        } else if (mode == 2) {
            System.out.println("Running AlphaBeta Pruning\n============================");
        } else {
            System.out.println("Invalid argument. Enter the desired problem number you would like to run (./gradlew -Pmode=n):\n\t1=N-Queens problem\n\t2=AlphaBeta Pruning");
        }
    }
    
}
