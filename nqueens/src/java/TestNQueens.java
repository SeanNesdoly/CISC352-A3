/*
 * A program that solves the N Queen's Problem using the minimum conflicts algorithm.
 *
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 18th, 2017.
 * Last Modified: March , 2017.
 */
package nqueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.IOException;
import java.util.Collections;

public class TestNQueens {

    private static Random randomGenerator = new Random();

    /* Increments squares on diagonal of newly placed queen and checks for new
    conflicts. If another queen is found, this queen is added to queensInConflict.*/
    public static void incrementAndAdd(NQueens instance, int i, int j) {
        int numQueens = instance.numQueens;

        // if there are queens in the same row, add them to queensInConflict
        // also make sure that each queen's value in allQueens is updated
        // (this queen hasn't been placed yet so it won't be added)
        ArrayList<Integer> row = instance.queensInRows.get(i);
        for (int columnIndex : row) {
            Queen queen = new Queen(i,columnIndex);
            if (!instance.queensInConflict.contains(queen)) {
                instance.queensInConflict.add(queen);
                instance.allQueens[i][columnIndex]++;
            }
        }

        // propogate conflict down rows
        int l = i;
        int m = j;
        while (l < numQueens && m >=0) {
            if (instance.allQueens[l][m] > 0) {
                System.out.println("adds queen here");
                Queen queen = new Queen(l,m);
                if (!instance.queensInConflict.contains(queen)) {
                    instance.queensInConflict.add(queen);
                    instance.allQueens[l][m]++; // increment number of conflicts for that queen
                }
            }
            instance.diagonalArray[l][m]++;
            l++;
            m--;
        }
        l = i+1;
        m = j+1;
        while (l < numQueens && m < numQueens) {
            if (instance.allQueens[l][m] > 0) {
                Queen queen = new Queen(l,m);
                if (!instance.queensInConflict.contains(queen)) {
                    instance.queensInConflict.add(queen);
                    instance.allQueens[l][m]++; // increment number of conflicts for that queen
                }
            }
            instance.diagonalArray[l][m]++;
            l++;
            m++;
        }

        // propogate conflict up rows
        l = i-1;
        m = j-1;
        while (l >=0 && m >=0) {
            if (instance.allQueens[l][m] > 0) {
                Queen queen = new Queen(l,m);
                if (!instance.queensInConflict.contains(queen)) {
                    instance.queensInConflict.add(queen);
                    instance.allQueens[l][m]++; // increment number of conflicts for that queen
                }
            }
            instance.diagonalArray[l][m]++;
            l--;
            m--;
        }
        l = i-1;
        m = j+1;
        while (l >= 0 && m < numQueens) {
            if (instance.allQueens[l][m] > 0) {
                Queen queen = new Queen(l,m);
                if (!instance.queensInConflict.contains(queen)) {
                    instance.queensInConflict.add(queen);
                    instance.allQueens[l][m]++; // increment number of conflicts for that queen
                }
            }
            instance.diagonalArray[l][m]++;
            l--;
            m++;
        }
    }

     /* Decrements squares on diagonal of newly placed queen and checks for new
    conflicts. If another queen is found and this queen has no other conflicts,
    this queen is removed from queensInConflict.*/
    public static void decrementAndRemove(NQueens instance, int i, int j) {
        int numQueens = instance.numQueens;

        // if there are queens in the same row, remove them from queensInConflict
        // (the queen's old position was already removed so it won't be added)
        ArrayList<Integer> row = instance.queensInRows.get(i);
        for (int columnIndex : row) {
            Queen queen = new Queen(i,columnIndex);
            instance.queensInConflict.remove(queen);
        }

        // propogate conflict down rows
        int l = i;
        int m = j;
        while (l < numQueens && m >=0) {

            --instance.diagonalArray[l][m];

            // if we decrement the square and it's still greater than 0, there might be a queen there
            if (instance.allQueens[l][m] > 0) {
                Queen queen = new Queen(l,m);
                instance.queensInConflict.remove(queen);
                instance.allQueens[l][m]--; // decrement number of conflicts for that queen
            }
            l++;
            m--;
        }
        l = i+1;
        m = j+1;
        while (l < numQueens && m < numQueens) {
            --instance.diagonalArray[l][m];

            if (instance.allQueens[l][m] > 0) {
                Queen queen = new Queen(l,m);
                instance.queensInConflict.remove(queen);
                instance.allQueens[l][m]--;
            }
            l++;
            m++;
        }

        // propogate conflict up rows
        l = i-1;
        m = j-1;
        while (l >=0 && m >=0) {
            --instance.diagonalArray[l][m];

            if (instance.allQueens[l][m] > 0) {
                Queen queen = new Queen(l,m);
                instance.queensInConflict.remove(queen);
                instance.allQueens[l][m]--;
            }
            l--;
            m--;
        }
        l = i-1;
        m = j+1;
        while (l >= 0 && m < numQueens) {
            --instance.diagonalArray[l][m];

            if (instance.allQueens[l][m] > 0) {
                Queen queen = new Queen(l,m);
                instance.queensInConflict.remove(queen);
                instance.allQueens[l][m]--;
            }
            l--;
            m++;
        }
    }


    /* Given the number of queens to be placed, uses a greedy algorithm to assign
    them to positions on a grid. */
    public static void performInitialAssignment(NQueens instance) {
        int numQueens = instance.numQueens;
        int numConflicts;
        int maxConflicts;
        // for each row, maintain list of positions with the fewest number of conflicts seen so far
        ArrayList<Integer> positionArray = new ArrayList<>();
        int numPossiblePositions;
        for (int i = 0; i < numQueens; i++) { // for each row
            positionArray.clear();
            maxConflicts = numQueens+1;
            for (int j = 0; j < numQueens; j++) { // for each col
                if (instance.columnArray[j] != 1) {
                    numConflicts = instance.diagonalArray[i][j];
                    if (numConflicts < maxConflicts) {
                        maxConflicts = numConflicts;
                        positionArray.clear();
                        positionArray.add(j); // append index of position with lower conflict num
                    }
                    else if (numConflicts == maxConflicts)
                        positionArray.add(j);
                }
            }

            // randomly choose conflicting queen
            numPossiblePositions = positionArray.size();
            int randomIndex = randomGenerator.nextInt(numPossiblePositions);
            int chosenColumn = positionArray.get(randomIndex);
            instance.columnArray[chosenColumn]++; // update column array
            instance.allQueens[i][chosenColumn] = 1; // add queen to initial solution

            positionArray.remove(randomIndex);
            incrementAndAdd(instance,i,chosenColumn);
            instance.queensInRows.get(i).add(chosenColumn); // add column index to row
        }
        System.out.println(Arrays.toString(instance.columnArray));
        System.out.println(Arrays.deepToString(instance.diagonalArray));

    }



    public static void outputResult(NQueens instance) {

        System.out.println(Arrays.deepToString(instance.allQueens));
        System.out.println("queens in conflict: ");
        for (Queen queen : instance.queensInConflict)
            System.out.println("row: " + queen.row + ", col: " + queen.column);
        System.out.println("queens in rows: ");
        for (int i = 0; i < instance.numQueens; i++) {
            ArrayList<Integer> row = instance.queensInRows.get(i);
            for (int col : row)
                System.out.println("row: " + i + ", col: " + col);
        }

    }


    /* Repairs initial queen assignment by randomly selecting a queen in conflict
    and moving it to a column where it conflicts with the fewest other queens,
    breaking ties randomly. */
    public static boolean repairSolution(NQueens instance) {
        boolean restart = false; // will return true if repair exceeds 100 moves
        int numSteps = 0;
        Queen victimQueen;
        int randomIndex;
        int chosenColumn;
        int numConflictingQueens;
        while (!instance.queensInConflict.isEmpty() && numSteps <= 100) {
            numSteps++;
            numConflictingQueens = instance.queensInConflict.size();
            randomIndex = randomGenerator.nextInt(numConflictingQueens);
            victimQueen = instance.queensInConflict.get(randomIndex);
            chosenColumn = victimQueen.column;

            // want to remove col index from row in queensInRows (not queen at index column???)
            boolean status = instance.queensInRows.get(victimQueen.row).remove((Integer)chosenColumn);
            System.out.println("status: " + status);
            decrementAndRemove(instance,victimQueen.row,chosenColumn);
            instance.allQueens[victimQueen.row][chosenColumn] = 0; // no queen now

            int numConflicts;
            int numQueens = instance.numQueens;
            int maxConflicts = numQueens+1;
            int rowConflicts;
            ArrayList<Integer> positionArray = new ArrayList<>();
            //for every row in column
            for (int i = 0; i < numQueens; i++) { // for each row in column
                if (i != victimQueen.row) {
                    rowConflicts = instance.queensInRows.get(i).size();
                    numConflicts = rowConflicts + instance.diagonalArray[i][chosenColumn];

                    if (numConflicts < maxConflicts) {
                        maxConflicts = numConflicts;
                        positionArray.clear();
                        positionArray.add(i); // append index of row with lowest conflict num

                    }
                    else if (numConflicts == maxConflicts)
                        positionArray.add(i);
                }
            }
            // randomly choose conflicting queen
            int numPossiblePositions = positionArray.size();
            randomIndex = randomGenerator.nextInt(numPossiblePositions);
            int chosenRow = positionArray.get(randomIndex);
            System.out.println("moving queen at (" + victimQueen.row + "," + chosenColumn + ") to (" + chosenRow + "," + chosenColumn + ")");

            // update arrays with position of new queen

            incrementAndAdd(instance,chosenRow,chosenColumn);
            instance.allQueens[chosenRow][chosenColumn]++; // increment position in allQueens array
            instance.queensInRows.get(chosenRow).add(chosenColumn); // add column index to row

            System.out.println("after repair: ");
            System.out.println("queens in conflict: ");
            for (Queen queen : instance.queensInConflict)
                System.out.println("row: " + queen.row + ", col: " + queen.column);
            System.out.println("all queens: ");
            System.out.println(Arrays.deepToString(instance.allQueens));
            System.out.println(Arrays.toString(instance.columnArray));
            System.out.println("diagonal: ");
            System.out.println(Arrays.deepToString(instance.diagonalArray));
            System.out.println("queens in rows: ");
            for (int i = 0; i < instance.numQueens; i++) {
                ArrayList<Integer> row = instance.queensInRows.get(i);
                for (int col : row)
                    System.out.println("row: " + i + ", col: " + col);
            }
            System.out.println("\n");

        }
        //if (numSteps == 101) {
            //restart = true;
        return restart;

    }

    public static void main(String[] args) {
        try {
            TextFile file = new TextFile();

            ArrayList<String> solutions = new ArrayList<>();
            for (String line : file.getContents()) {
                int input = Integer.parseInt(line);
                NQueens instance = new NQueens(input);

                boolean restart = true;
                while (restart) {
                    performInitialAssignment(instance);
                    System.out.println("initial solution: ");
                    outputResult(instance);
                    restart = repairSolution(instance);
                    System.out.println("final solution: ");
                    outputResult(instance);
                }

                String board = "";
                if (instance.numQueens < 256) {
                    String row = String.join("", Collections.nCopies(instance.numQueens, "x "));
                    row += "\n";
                    for(int i = 0; i < instance.queensInRows.size(); i++) {
                        int q = instance.queensInRows.get(i).get(0);
                        String qInRow = row.substring(0,2*q) + "q " + row.substring(2*q+2);
                        board += qInRow;
                    }
                }

                String s = "[";
                for (int i = 0; i < instance.queensInRows.size(); i++) {
                    s += instance.queensInRows.get(i).get(0) + 1;
                    if (i < instance.queensInRows.size()-1)
                        s += ",";
                }
                s += "]\n";
                solutions.add(board + s);
            }

            String out = "";
            for (String s : solutions)
                out += s + "\n";

            file.writeFile(out);
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

}
