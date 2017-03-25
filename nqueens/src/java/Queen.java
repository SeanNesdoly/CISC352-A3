package nqueens;

/*
 * Wrapper class for a Queen on an n-by-n instance of the NQueens problem.
 *
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 25th, 2017.
 */

public class Queen {

    public int row;
    public int col;

    public Queen(int rowInd, int colInd) {
        row = rowInd;
        col = colInd;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject instanceof Queen) {
            Queen otherQueen = (Queen)otherObject;
            return otherQueen.row == row && otherQueen.col == col;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.row;
        hash = 89 * hash + this.col;
        return hash;
    }
}
