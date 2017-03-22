/*
 * An NQueens object containing the number of queens to be placed, an array of 
 * column conflicts, and an array of diagonal conflicts.
 * 
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 18th, 2017.
 * Last Modified: March , 2017.
 */
package nqueens;


public class NQueens {

    public int numQueens;
    public int[] columnArray;
    public int[][] diagonalArray;
 
    
    public NQueens(int n) {
        numQueens = n;
        columnArray = new int[numQueens];
        diagonalArray = new int[numQueens][numQueens];
    }
    
    
}
