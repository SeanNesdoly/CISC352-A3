package nqueens;
/*
 * This class creates an instance of the NQueens problem given a chess board size as input.
 * Three arrays are updated throughout the placement of queens on the board in order to count
 * the number of conflicts for a given square in O(1) time. When each queen on the board has 0
 * conflicts, a solution is found.
 *
 * The THRESHOLD constant defines the number of repairs performed on a given chess board before
 * all queens are removed and added back in (via a greedy algorithm). This is to prevent the
 * algorithm from getting stuck. Different values of n may require different THRESHOLD levels and
 * should be adjusted as necessary.
 *
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 25th, 2017.
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Arrays;

public class NQueens {

    public int n; // number of queens on the chess board
    public int[] queens; // entry i gives a Queen at [row=queens[i],col=i]
    public int[] numQueensInRow; // keep track of the number of queens in a given row
    public int[] numQueensInD1; // keep track of the number of queens on each diagonal (top right to bottom left)
    public int[] numQueensInD2; // keep track of the number of queens on each diagonal (top left to bottom right)

    public static final int THRESHOLD = 100; // number of iterations before we create a new board
    private static Random rand = new Random();

    public NQueens(int _n) {
        n = _n;
        queens = new int[n];
        numQueensInRow = new int[n];
        numQueensInD1 = new int[2*n-1];
        numQueensInD2 = new int[2*n-1];;
    }

    // create an initial board configuration by placing queens one column at a time while using the
    // minimum-conflicts heuristic to select a row (a greedy approach)
    public void createInitialBoard() {
        for (int col = 0; col < n; col++) {
            int row = computeMinConflictRow(col);
            queens[col] = row;
            addQueenConstraint(row, col);
        }
    }

    // perform a repair on the board by randomly selecting a queen that is in conflict & moving
    // it to a new row within its column. A minimum-conflicts heuristic is used to compute the new row
    public void repair() {
        int col = rand.nextInt(n);
        int row = queens[col];

        // find a queen that is in conflict
        boolean foundQueenInConflict = countConflicts(row, col) > 0;
        while (!foundQueenInConflict) {
            col = rand.nextInt(n);
            row = queens[col];

            foundQueenInConflict = countConflicts(row, col) > 0;
        }

        // move the queen that is in conflict
        removeQueenConstraint(row, col); // remove old queen constraints

        // select a new row using the min-conflicts heuristic
        int newRow = computeMinConflictRow(row, col);
        queens[col] = newRow;

        addQueenConstraint(newRow, col); // add in new queen constraints
    }

    // a solution has been found if every queen on the board has 0 conflicts
    public boolean isSolution() {
        int row;
        for (int col = 0; col < n; col++) {
            row = queens[col];
            if (countConflicts(row, col) > 0)
                return false;
        }

        return true;
    }

    // add a queen by incrementing the required arrays that keep track of conflicts
    public void addQueenConstraint(int row, int col) {
        numQueensInRow[row]++;
        numQueensInD1[row+col]++;
        numQueensInD2[n-col-1+row]++;
    }

    // remove a queen by decrementing the required arrays that keep track of conflicts
    public void removeQueenConstraint(int row, int col) {
        numQueensInRow[row]--;
        numQueensInD1[row+col]--;
        numQueensInD2[n-col-1+row]--;
    }

    public int computeMinConflictRow(int col) {
        // compute minimum value in column
        int minVal = countConflicts(0, col);
        int currVal;
        for (int i = 0; i < n; i++) {
            currVal = countConflicts(i, col);
            if (currVal < minVal)
                minVal = currVal;
        }

        // collect all rows with the minimum value
        ArrayList<Integer> minRows = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (countConflicts(i, col) == minVal)
                minRows.add(i);
        }

        // randomly select a row with the minimum value
        return minRows.get(rand.nextInt(minRows.size()));
    }

    // restriction: cannot return the current row!
    public int computeMinConflictRow(int row, int col) {
        // compute minimum value in column
        int minVal = Integer.MAX_VALUE;
        int currVal;
        for (int i = 0; i < n; i++) {
            currVal = countConflicts(i, col);
            if (i != row && currVal < minVal)
                minVal = currVal;
        }

        // collect all rows with the minimum value
        ArrayList<Integer> minRows = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i != row && countConflicts(i, col) == minVal)
                minRows.add(i);
        }

        // randomly select a row with the minimum value
        return minRows.get(rand.nextInt(minRows.size()));
    }

    // computes the number of conflicts at the given row & column
    public int countConflicts(int row, int col) {
        return (numQueensInRow[row]-1) + (numQueensInD1[row+col]-1) + (numQueensInD2[n-col-1+row]-1);
    }

    // returns an array where the i'th element is the row index of a queen at column i (0-indexed)
    @Override
    public String toString() {
        return Arrays.toString(queens).replaceAll("\\s+","");
    }

    // returns an array where the i'th element is the column index of a queen at row i (1-indexed).
    // Note that this method may only be called on a FINAL solution (and NOT a partial solution)!
    public String printColIndices() {
        int[] swapped = new int[n];
        for (int i = 0; i < n; i++) {
            swapped[queens[i]] = i+1;
        }

        return Arrays.toString(swapped).replaceAll("\\s+","");
    }

    // convience method to print the current state of the chess board
    public String printBoard() {
        ArrayList<String> rows = new ArrayList<>();
        for (int i = 0; i < n; i++)
            rows.add(String.join("", Collections.nCopies(n, "x ")));

        int row;
        for (int i = 0; i < n; i++) {
            row = queens[i];
            String rowStr = rows.get(row);
            rowStr = rowStr.substring(0,2*i) + "q " + rowStr.substring(2*i+2);
            rows.set(row, rowStr);
        }

        String board = "";
        for (String r : rows)
            board += r + "\n";

        return board;
    }

}
