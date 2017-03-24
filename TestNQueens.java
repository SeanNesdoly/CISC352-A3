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

public class TestNQueens {
    
    private static Random randomGenerator = new Random();
    private static ArrayList<Queen> queensInConflict = new ArrayList<Queen>();
    
    
    public static int getInput() {
        // error checking for if n<3 or n>1000000000?
        return 4;
    }
    
    /* Increments squares on diagonal of newly placed queen and checks for new 
    conflicts. If another queen is found, this queen is added to queensInConflict.*/
    public static void incrementAndAdd(NQueens instance, int i, int j) {
        int numQueens = instance.numQueens;
        
        // if there are queens in the same row, add them to queensInConflict
        // (this queen hasn't been placed yet so it won't be added)
        ArrayList<Integer> row = instance.queensInRows.get(i);
        for (int columnIndex : row) {
            Queen queen = new Queen(i,columnIndex);
            queensInConflict.add(queen);
        }
        
        // propogate down rows
        int l = i;
        int m = j;
        while (l < numQueens && m >=0) {
            if (instance.diagonalArray[l][m] > 0 && instance.allQueens[l][m] == 1) {
                Queen queen = new Queen(l,m);
                queensInConflict.add(queen);
                instance.allQueens[l][m]++; // increment number of conflicts for that queen
            }
            instance.diagonalArray[l][m]++;
            l++;
            m--;
        }
        l = i+1;
        m = j+1;
        while (l < numQueens && m < numQueens) {
            if (instance.diagonalArray[l][m] > 0 && instance.allQueens[l][m] == 1) {
                Queen queen = new Queen(l,m);
                queensInConflict.add(queen);
                instance.allQueens[l][m]++; // increment number of conflicts for that queen
            }
            instance.diagonalArray[l][m]++;
            l++;
            m++;
        }
        
        // propogate up rows
        l = i-1;
        m = j-1;
        while (l >=0 && m >=0) {
            if (instance.diagonalArray[l][m] > 0 && instance.allQueens[l][m] == 1) {
                Queen queen = new Queen(l,m);
                queensInConflict.add(queen);
                instance.allQueens[l][m]++; // increment number of conflicts for that queen
            }
            instance.diagonalArray[l][m]++;
            l--;
            m--;
        }
        l = i-1;
        m = j+1;
        while (l >= 0 && m < numQueens) {
            if (instance.diagonalArray[l][m] > 0 && instance.allQueens[l][m] == 1) {
                Queen queen = new Queen(l,m);
                queensInConflict.add(queen);
                instance.allQueens[l][m]++; // increment number of conflicts for that queen
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
        instance.columnArray[j]--;
        
        int square;
        // propogate down rows
        int l = i;
        int m = j;
        while (l < numQueens && m >=0) {
            square = instance.diagonalArray[l][m];
            --square;
            if ((l == i && m == j) || square == 0 && instance.allQueens[l][m] == m)
                queensInConflict.remove(l);
            l++;
            m--;
        }
        l = i+1;
        m = j+1;
        while (l < numQueens && m < numQueens) {
            square = instance.diagonalArray[l][m];
            --square;
            if (square == 0 && instance.allQueens[l][m] == m)
                queensInConflict.remove(l);
            l++;
            m++;
        }
        
        // propogate up rows
        l = i-1;
        m = j-1;
        while (l >=0 && m >=0) {
            square = instance.diagonalArray[l][m];
            --square;
            if (square == 0 && instance.allQueens[l][m] == m)
                queensInConflict.remove(l);
            l--;
            m--;
        }
        l = i-1;
        m = j+1;
        while (l >= 0 && m < numQueens) {
            square = instance.diagonalArray[l][m];
            --square;
            if (square == 0 && instance.allQueens[l][m] == m)
                queensInConflict.remove(l);
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
                numConflicts = 0;
                if (instance.columnArray[j] == 1) { // is there a queen in column j?
                    numConflicts++;  
                }
                numConflicts = numConflicts + instance.diagonalArray[i][j];
                instance.totalConflictArray[i][j] = numConflicts;
                
                if (numConflicts < maxConflicts) {
                    maxConflicts = numConflicts;
                    positionArray.clear();
                    positionArray.add(j); // append index of position with lower conflict num
                }
                else if (numConflicts == maxConflicts) 
                    positionArray.add(j);
            }

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
        for (Queen queen : queensInConflict) 
            System.out.println(queen.row + ", " + queen.column);
        for (int i = 0; i < instance.numQueens; i++) {
            ArrayList<Integer> row = instance.queensInRows.get(i);
            for (int col : row) 
                System.out.println("col " + col);
        }
            
    }
               
    
    /* Repairs initial queen assignment by randomly selecting a queen in conflict
    and moving it to a column where it conflicts with the fewest other queens,
    breaking ties randomly. */
    public static boolean repairSolution(NQueens instance) {
        boolean restart = false; // will return true if repair exceeds 100 moves
        int numSteps = 0; 
        
        // while array of queens in conflict is non-empty 
        int randomIndex;
        Queen victimQueen;
        int numConflictingQueens;
        while (!queensInConflict.isEmpty() && numSteps <= 100) {
            numSteps++;
            numConflictingQueens = queensInConflict.size();
            randomIndex = randomGenerator.nextInt(numConflictingQueens);
            victimQueen = queensInConflict.get(randomIndex);
            
            decrementAndRemove(instance,victimQueen.row,victimQueen.column);
            incrementAndAdd(instance,victimQueen.row,victimQueen.column);
        }
        if (numSteps == 100)
            return true;
        return false;
            
    }
        
    
    
    
    public static void main(String[] args) {
        int input = getInput(); // will be array list 
        // for each input in file
        boolean restart = true;
        while (restart) {
            NQueens instance = new NQueens(input);
            performInitialAssignment(instance);
            System.out.println("initial solution: ");
            outputResult(instance);
            //restart = repairSolution(instance);
            //System.out.println("final solution: ");
            //outputResult(instance);
            restart = false;
        }
        
    }
    
}
