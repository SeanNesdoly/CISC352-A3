
package nqueens;

public class Queen {
    
    public int row;
    public int column;
    
    public Queen(int rowInd, int colInd) {
        row = rowInd;
        column = colInd;
    }
    
        
    @Override
    public boolean equals(Object otherObject) {
        if (otherObject instanceof Queen) {
            Queen otherQueen = (Queen)otherObject;
            return otherQueen.row == row && otherQueen.column == column;      
        }
        return false;     
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.row;
        hash = 89 * hash + this.column;
        return hash;
    }
}
