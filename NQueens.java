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

public class NQueens {

    private static Random randomGenerator = new Random();
    private static ArrayList<Integer> queensInConflict = new ArrayList<>();
 
    
    public static int getInput() {
        // error checking for if n<3 or n>1000000000
        // does input need to be even?
        return 4;
    }
    
    /* Given an array of counters for column conflicts and an array of counters
    for diagonal conflicts, finds the number of conflicts for placing a queen on 
    on a particular square in the grid. */
    public static int checkConflicts(int i, int j, int numQueens, int[][] columnArray, int[][] diagonalArray) {
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
    
    /* Given the number of queens to be placed, uses a greedy algorithm to assign 
    them to positions on a grid. */
    public static int[] performInitialAssignment(int numQueens) {
        int[] solution = new int[numQueens];
        int[][] columnArray = new int[numQueens][numQueens];
        int[][] diagonalArray = new int[numQueens][numQueens];
        //int randomColumnIndex = randomGenerator.nextInt(numQueens); 
        int randomColumnIndex = 1;
        solution[0] = randomColumnIndex+1; // randomly place first queen
        columnArray[0][randomColumnIndex] = 1;
        
        int l = 0;
        int m = randomColumnIndex;
        while (l < numQueens && m >=0) {
            diagonalArray[l][m]++;
            l++;
            m--;
        }
        l = 1;
        m = randomColumnIndex+1;
        while (l < numQueens && m < numQueens) {
            diagonalArray[l][m]++;
            l++;
            m++;
        }
                
        
        diagonalArray[0][randomColumnIndex] = 1;
        int numConflicts = 0;
        int maxConflicts; // will always be below 4
        // for each row, maintain list of positions with the fewest number of conflicts seen so far
        ArrayList<Integer> positionArray = new ArrayList<>(); 
        int numPossiblePositions;
        for (int i = 1; i < numQueens; i++) { // for each row
            positionArray.clear();
            maxConflicts = 4;
            for (int j = 0; j < numQueens; j++) { // for each col
              
                numConflicts = 0;
                // is there a queen in column j?
                if (columnArray[i-1][j] == 1) {
                    columnArray[i][j] = 1;
                    numConflicts++;      
                }

                numConflicts = numConflicts + diagonalArray[i][j];
                
                if (numConflicts < maxConflicts) {
                    maxConflicts = numConflicts;
                    positionArray.clear();
                    positionArray.add(j); // append index of position with lower conflict num
                }
                else if (numConflicts == maxConflicts) 
                    positionArray.add(j);
            }
            if (numConflicts > 0)
                queensInConflict.add(i);
            numPossiblePositions = positionArray.size();
 
            int chosenPosition = positionArray.get(randomGenerator.nextInt(numPossiblePositions));
            columnArray[i][chosenPosition] = 1;
            
            l = i;
            m = chosenPosition;
            while (l < numQueens && m >=0) {
                diagonalArray[l][m]++;
                l++;
                m--;
            }
            l = i+1;
            m = chosenPosition+1;
            while (l < numQueens && m < numQueens) {
                diagonalArray[l][m]++;
                l++;
                m++;
            }
                 
            solution[i] = chosenPosition + 1;
            
        }
        System.out.println(Arrays.deepToString(columnArray));
        System.out.println(Arrays.deepToString(diagonalArray));
    
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
        int[] initialQueenPlacement = performInitialAssignment(input);
        outputResult(initialQueenPlacement);
    }
    
}
