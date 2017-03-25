# Assignment 3 Technical Document

>CISC 352: Artificial Intelligence  
>Sean Nesdoly 13sn50 10135490  
>Mary Hoekstra 13meh9 10129863  
>March 19th, 2017  

#### Codebase Contributions
- Mary Hoekstra wrote the solution to the N-Queens problem
- Sean Nesdoly wrote an implementation of the AlphaBeta pruning algorithm

## N-Queens Problem
In this program, the min-conflicts iterative repair method is used to solve the NQueens Problem.

First, the number of queens is read in from the input file and an NQueens object is created. Each NQueens object has a variety of data structures as attributes. These data structures are intended to keep track of the number of conflicts and minimize the number of O(n) procedures.

*numQueens* is the grid size.
*columnArray* keeps track of the number of queens in a column. It is used in the pre-processing phase to ensure that no two queens are placed on the same column.
*diagonalArray* is a two-dimensional array used to keep track of diagonal conflicts. Whenever a queen is added to a square, the conflict is propagated through all the diagonals.
*allQueens* is a two-dimensional array used to keep track of the position of queens and how many other queens they are in conflict with. An entry i,j in the array corresponds to how many total conflicts a queen on square i,j has.
*queensInRows* is an ArrayList of ArrayLists. There are numQueens lists in the ArrayList, each corresponding to a row. Each list, i, contains the column indices of the queens in a row i. This is used in the repair method when a queen may be moved to row containing other queens. This avoids needing to iterate through an entire row to find all the queens in that row.
*queensInConflict* is an ArrayList to keep track of all the queens in conflict at any given time. It holds Queen objects, which contain attributes for the row and column indices.

Once the NQueens object is initialized, a greedy heuristic is used to place the queens on the board. The method *performInitialAssignment* iterates through every row, placing a queen in the position with the minimum number of conflicts. It keeps track of the best positions seen so far in *positionArray*. If there are multiple positions with the fewest number of conflicts (the size of *positionArray* is greater than 1), a position is randomly chosen from the array and the queen is placed there.
Every time a queen is placed, the column position in *columnArray* is incremented and *incrementAndAdd* is called. *incrementAndAdd* updates the *diagonalArray* by incrementing the position of the newly placed queen and propagating this increment across all diagonals. If one of the diagonal positions being incremented contains a queen, this queen is added to the *queensInConflict* array.

After an initial solution is found, *repairInitialSolution* is called. This method randomly chooses a queen from the *queensInConflict* array and moves it along the column to the position with the fewest conflicts. First, *decrementAndRemove* is called to remove the queen from its current position in the *allQueens* and*queensInRows* arrays and update the *diagonalArray* accordingly. Then, in a manner similar to *incrementAndAdd*, the entry in *diagonalArray* is decremented and the decrement is propagated across all the diagonals. If a queen that was in conflict before is now no longer in conflict, it is removed from *queensInConflict*. Using the same strategy as in *performInitialAssignment*, each row of the column is then iterated through, keeping track of the best positions seen so far in *positionArray*. If there are multiple positions with the fewest conflicts, a position is chosen randomly. Next, *incrementAndAdd* is called with the new queen position to update the *diagonalArray* and add new queens to *queensInConflict* if necessary.

If there are still queens in conflict (*queensInConflict* is non-empty), the repair method is called again. If the number of repair steps exceeds 100, a new initial assignment is made and the process is started over again.


## Alpha-Beta Pruning
The *Alpha-Beta Pruning algorithm* is a search algorithm that seeks to decrease the number of vertices that are evaluated by the **minimax** algorithm in its search tree. This is often used in the context of two-player machine playing *zero-sum games*. The term "minimax" refers to the goal of each player: to **minimize** their opponents **maximum** possible score. This algorithm yields the same solution as the *minimax* algorithm. In addition, it has the possibility of reducing the number of vertices visited during the tree traversal. This is accomplished by *pruning* branches of the tree that cannot possibly influence the final solution.

### Data Structures

A `Vertex` class was created to implement the tree data structure required for the *AlphaBeta Pruning* algorithm. This class contains the following attributes:
```java
String v; // vertex identifier
String type; // type of vertex (MAX or MIN)
ArrayList<Vertex> children; // child vertices
boolean isRoot = false; // denotes the root vertex
boolean isLeaf = false; // leaf vertices are identified by numbers
double leafValue; // value of a leaf vertex
```
The `AlphaBetaTree` class contains the root vertex of the tree. The tree may be traversed when given only the root vertex, as each vertex contains an **ArrayList** of its child vertices.

The *createGraph(String graph)* method takes an input string from the *alphabeta.txt* input file and parses it accordingly to construct a tree. Firstly, the vertex set is parsed and created into instances of the `Vertex` class. These vertices are all added to a `HashMap` that maps vertex string identifiers to `Vertex` objects:  
```java
public Map<String, Vertex> V; // mapping of vertex names to vertex objects for parsing
```
This auxiliary data structure is created to keep track of the set of vertices in the tree for parsing of the set of edges. For each vertex encountered in the string representation of the edge set, a constant time look-up in the map **V** will return the previously instantiated `Vertex` object. The first vertex in an edge entry is assumed to be the parent vertex. Thus, the second vertex is added as a child of the first vertex by adding it to the **children** `ArrayList` (an attribute of the `Vertex` class). When the entire edge set has been parsed, a tree will have been constructed. A depth-first search through each vertex and their list of children, starting at the root, will effectively traverse the entire tree. This functionality is all that is required for the *AlphaBeta Pruning* algorithm.

After the graph has been constructed, each level of the tree must be sorted in lexicographical order. This is to assert that the graph created from the input is not dependent on the order in which the edge set is given in the input string. To do so, a breadth-first search is applied to the tree, where the children of a given vertex are all sorted in lexicographical order (based on their name). The only exception to this ordering is the leaves of the tree. The recursive breadth-first search algorithm is given below:
```java
private void sortGraph(Vertex curr) {
    if (curr.children.get(0).isLeaf)
        return;

    Comparator<Vertex> vcomp = (Vertex a, Vertex b) -> {
        return a.v.compareTo(b.v);
    };

    Collections.sort(curr.children, vcomp);

    for (Vertex child : curr.children) {
        sortGraph(child);
    }
}
```
It should be noted that the sort occurs after the tree has been fully constructed. This is faster than keeping the children of a vertex sorted on each insertion during the construction of the graph. Sorting after the tree is constructed has a complexity of **O(n+log(n))**; in comparison, maintaining order during tree construction has a complexity of **O(n\*log(n))**.

### Algorithm Implementation
The recursive algorithm implementation is modeled after the pseudocode given in the assignment documentation. It is given below:
```java
private double alpha_beta(Vertex current, double alpha, double beta) {
    if (current.isRoot) {
        alpha = Double.NEGATIVE_INFINITY;
        beta = Double.POSITIVE_INFINITY;
    }

    if (current.isLeaf) {
        leaves_touched++;
        return current.leafValue;
    }

    if (current.isMaxVertex()) {
        for (Vertex child : current.children) {
            alpha = Math.max(alpha, alpha_beta(child, alpha, beta));

            if (alpha >= beta)
                return alpha; // prune branch
        }
        return alpha;
    }

    // not a leaf vertex && not a MAX vertex implies that this must be a MIN vertex
    for (Vertex child: current.children) {
        beta = Math.min(beta, alpha_beta(child, alpha, beta));

        if (beta <= alpha)
            return beta; // prune branch
    }
    return beta;
}
```
It should be noted that the *alpha* and *beta* values are of type *double* to allow for comparison with Java's infinity attribute in the `Double` class. The object-oriented flavour of this implementation allows for a simple instantiation of an `AlphaBetaTree` object given a string representation of a graph as input. The constructor of the `AlphaBetaTree` class constructs the graph from the string input and proceeds to run the recursive *alpha_beta* method on it. This code is given below:
```java
public AlphaBetaTree(String graph) {
    V = new HashMap<>(); // vertex name to Vertex object mapping
    createGraph(graph);
    sortGraph(root); // assert lexicographical order on each level of the tree

    // alpha = -infinity, beta = +infinity for initial recursive call
    score = (int) alpha_beta(root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

    graphCount++; // update graph counter for multi-graph runs
}
```
The `AlphaBetaTree` attribute *leaves_touched* is used to keep track of the number of leaf nodes visited during a run of the *alpha_beta* algorithm. The leaf node base case in the method is used to evaluate a leaf node and increment the *leaves_touched* attribute. The final solution to the minimax problem is stored in the *score* attribute.

Finally, a simple loop in the **main** method of the `Main.java` driver class continues to create `AlphaBetaTree` object instances given a graph from the input file. Each solution is written to the *alphabeta_out.txt* file, separated by lines.
```java
TextFile file = new TextFile();

String out = "";
for (String line : file.getContents()) {
    // ignore blank lines between input graphs
    if (!line.isEmpty()) {
        AlphaBetaTree abt = new AlphaBetaTree(line);
        out += abt + "\n\n";
        System.out.println(abt); // to output stream for logging
    }
}

file.writeFile(out);
```
