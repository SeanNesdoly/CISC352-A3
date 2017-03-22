/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        return 4;
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
    public static int[] performInitialAssignment(NQueens instance) {
        int numQueens = instance.numQueens;
        int[] solution = new int[instance.numQueens];
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
            solution[i] = chosenPosition + 1; // add queen to solution           
            
            
            updateConflictArrays(instance,i,chosenPosition);
       
        }
        System.out.println(Arrays.toString(instance.columnArray));
        System.out.println(Arrays.deepToString(instance.diagonalArray));
    
        return solution;
    }
        
        
        
    public static void outputResult(int[] solution) {
        System.out.println(Arrays.toString(solution));
    }
               
    /*
    public static int[] repairSolution(int[] intialAssignment) {
        
    }
    */    
    
    
    
    public static void main(String[] args) {
        int input = getInput(); // will be array list 
        // for each input in file
        NQueens instance = new NQueens(input);
        int[] initialQueenPlacement = performInitialAssignment(instance);
        outputResult(initialQueenPlacement);
    }
    
}
