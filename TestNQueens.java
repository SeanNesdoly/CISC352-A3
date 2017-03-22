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
    private static ArrayList<Integer> queensInConflict = new ArrayList<>();
    
    
    public static int getInput() {
        // error checking for if n<3 or n>1000000000?
        return 8;
    }
    
    /* Returns the column position with the fewest number of conflicts, breaking
    ties randomly.
    public static int checkConflicts(int i, int j) {
        int numConflicts = 0;
        // is there a queen in column j?
        if (columnArray[i-1][j] == 1) {
            columnArray[i][j] = 1;
            numConflicts++;      
        }
        // is there a queen on one or both of the diagonals?
        int diagonalConflicts = 0;
        if (j > 0 && (diagonalArray[i-1][j-1] > 0)) 
            diagonalConflicts++;
        if (j < numQueens-1 && (diagonalArray[i-1][j+1] > 0)) 
            diagonalConflicts++;
        diagonalArray[i][j] = diagonalConflicts;
        numConflicts = numConflicts + diagonalConflicts;
        return numConflicts;
    }
    
    */
    
    
    /* Given the index of the newly placed queen, updates the column conflict array
    and diagonal conflict array. */
    public static void updateConflictArrays(NQueens instance, int i, int j) {
        int numQueens = instance.numQueens;
        instance.columnArray[j] = 1;
            
        int l = i;
        int m = j;
        while (l < numQueens && m >=0) {
            instance.diagonalArray[l][m]++;
            l++;
            m--;
        }
        l = i+1;
        m = j+1;
        while (l < numQueens && m < numQueens) {
            instance.diagonalArray[l][m]++;
            l++;
            m++;
        }
    }
    
    
    /* Given the number of queens to be placed, uses a greedy algorithm to assign 
    them to positions on a grid. */
    public static void performInitialAssignment(NQueens instance) {
        int numQueens = instance.numQueens;
        int numConflicts = 0;
        int maxConflicts;
        // for each row, maintain list of positions with the fewest number of conflicts seen so far
        ArrayList<Integer> positionArray = new ArrayList<>(); 
        int numPossiblePositions;
        for (int i = 0; i < numQueens; i++) { // for each row
            positionArray.clear();
            maxConflicts = 4; // will always be below 4
            for (int j = 0; j < numQueens; j++) { // for each col
                numConflicts = 0;
                if (instance.columnArray[j] == 1) // is there a queen in column j?
                    numConflicts++;      
                numConflicts = numConflicts + instance.diagonalArray[i][j];
                
                if (numConflicts < maxConflicts) {
                    maxConflicts = numConflicts;
                    positionArray.clear();
                    positionArray.add(j); // append index of position with lower conflict num
                }
                else if (numConflicts == maxConflicts) 
                    positionArray.add(j);
            }

            numPossiblePositions = positionArray.size();
            int chosenPosition = positionArray.get(randomGenerator.nextInt(numPossiblePositions));
            if (numConflicts > 0)
                queensInConflict.add(chosenPosition); // update list of queens with conflicts
            instance.solution[i] = chosenPosition + 1; // add queen to solution           
            
            
            updateConflictArrays(instance,i,chosenPosition);
       
        }
        System.out.println(Arrays.toString(instance.columnArray));
        System.out.println(Arrays.deepToString(instance.diagonalArray));

    }
        
        
        
    public static void outputResult(NQueens instance) {
        System.out.println(Arrays.toString(instance.solution));
    }
               
    
    /* Repairs initial queen assignment by randomly selecting a queen in conflict
    and moving it to a column where it conflicts with the fewest other queens,
    breaking ties randomly. */
    public static boolean repairSolution(NQueens instance) {
        boolean restart = false; // will return true if repair exceeds 100 moves
        int numSteps = 0; 
        
        // while array of queens in conflict is non-empty 
        int randomIndex;
        int victimQueen;
        int numConflictingQueens;
        while (!queensInConflict.isEmpty() && numSteps <= 100) {
            numSteps++;
            numConflictingQueens = queensInConflict.size();
            randomIndex = randomGenerator.nextInt(numConflictingQueens);
            victimQueen = queensInConflict.get(randomIndex);
            // do stuff
            queensInConflict.remove(randomIndex);
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
            outputResult(instance);
            restart = repairSolution(instance);
            
            
            restart = false;
        }
        
    }
    
}
