/*
 * An NQueens object containing the number of queens to be placed, an array of 
 * column conflicts, an array of diagonal conflicts, and an array to hold the 
 * solution.
 * 
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 18th, 2017.
 * Last Modified: March , 2017.
 */
package nqueens;

import java.util.ArrayList;


public class NQueens {

    public int numQueens;
    public int[] columnArray;
    public int[][] diagonalArray;
    public int[][] totalConflictArray;
    public int[][] allQueens; // an entry i,j corresponds to the number of conflicts a queen on i,j has
    public ArrayList<ArrayList<Integer>> queensInRows; // each entry, i, contains a list of the column indices of queens in row i
    
    public NQueens(int n) {
        numQueens = n;
        columnArray = new int[numQueens];
        diagonalArray = new int[numQueens][numQueens];
        totalConflictArray = new int[numQueens][numQueens];
        allQueens = new int[numQueens][numQueens]; 
        queensInRows = new ArrayList<>(numQueens);
        while (queensInRows.size() < numQueens)
            queensInRows.add(new ArrayList<>());
        
    }
    
    
}
