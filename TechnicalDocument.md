# Assignment 3 Technical Document

>CISC 352: Artificial Intelligence  
>Sean Nesdoly 13sn50 10135490  
>Mary Hoekstra 13meh9 10129863  
>March 19th, 2017  

#### Codebase Contributions
- Mary Hoekstra wrote the solution to the N-Queens problem
- Sean Nesdoly wrote an implementation of the AlphaBeta pruning algorithm

## N-Queens Problem
In this program, the min-conflicts iterative repair method is used to solve the NQueens problem.

First, the number of queens to be placed on the board is read in from the input file and an `NQueens` object is created. Each `NQueens` object contains **four** arrays; when combined, these data structures are intended to keep track of the current placement of queens and the number of conflicts in order to minimize the number of O(n) operations that occur. The attributes of the `NQueens` class are as follows:
```java
public int n; // number of queens on the chess board
public int[] queens; // entry i gives a Queen at [row=queens[i],col=i]
public int[] numQueensInRow; // keep track of the number of queens in a given row
public int[] numQueensInD1; // keep track of the # of queens on each north-east diagonal
public int[] numQueensInD2; // keep track of the # of queens on each north-west diagonal

public static final int THRESHOLD = 100; // number of iterations before we create a new board
```

Once the `NQueens` object is initialized, a **greedy heuristic** is used to place the queens on the board; this attempts to create an initial configuration with the minimum number of moves required to produce a solution. The **createInitialBoard()** method iterates through every column, placing a queen on a row with the minimum number of conflicts. If there are multiple positions with the fewest number of conflicts, the position is randomly chosen from that set. The greedy algorithm is given below:
```java
// create an initial board configuration by placing queens one column at a time while using the
// minimum-conflicts heuristic to select a row (a greedy approach)
public void createInitialBoard() {
    for (int col = 0; col < n; col++) {
        int row = computeMinConflictRow(col);
        queens[col] = row;
        addQueenConstraint(row, col);
    }
}
```

After an initial board configuration is constructed using a greedy approach, the queens are iteratively moved to new positions in a **repair()** method until a solution is found. The number of times that an instance of a board is repaired is capped by the **THRESHOLD** variable in the `NQueens` class. If a particular board is repaired more than this value, then a new initial configuration is constructed using the **createInitialBoard()** method. This approach attempts to reduce the continued repair of a worst-case board configuration (i.e. all queens initially on the same row).

To **repair** the board, a queen must be picked that is currently in conflict with one or more queens. To do this, queens are selected at random until one is found that is in conflict. The queen picked is then moved to another row within its column. This row is selected using the minimum-conflicts heuristic. Similar to the greedy assignment of queens during the initial board construction, if there are multiple positions with the  fewest number of conflicts, one is randomly selected. The **repair()** method is given below:
```java
// perform a repair on the board by randomly selecting a queen that is in conflict & moving
// it to a new row within its column. A minimum-conflicts heuristic is used to compute the new row
public void repair() {
    int col = rand.nextInt(n);
    int row = queens[col];

    // find a queen that is in conflict
    boolean foundQueenInConflict = countConflicts(row, col) > 0;
    while (!foundQueenInConflict) {
        col = rand.nextInt(n);
        row = queens[col];

        foundQueenInConflict = countConflicts(row, col) > 0;
    }

    // move the queen that is in conflict
    removeQueenConstraint(row, col); // remove old queen constraints

    // select a new row using the min-conflicts heuristic
    int newRow = computeMinConflictRow(row, col);
    queens[col] = newRow;

    addQueenConstraint(newRow, col); // add in new queen constraints
}
```

In the above code there are two method calls that update the required arrays to reflect an addition or removal of a queen from the board. Either operation must ensure that subsequent computations on the number of conflicts for a given queen is up-to-date. To actually store the number of conflicts, we keep track of the number of queens in each **row**, the number of queens in each **left diagonal**, and the number of queens in each **right diagonal**. Doing so allows for a constant time **O(1)** calculation of the number of conflicting queens at a given row and column in the chess board. The code for each of the operations described above is given in the following methods:
```java
// add a queen by incrementing the required arrays that keep track of conflicts
public void addQueenConstraint(int row, int col) {
    numQueensInRow[row]++;
    numQueensInD1[row+col]++;
    numQueensInD2[n-col-1+row]++;
}

// remove a queen by decrementing the required arrays that keep track of conflicts
public void removeQueenConstraint(int row, int col) {
    numQueensInRow[row]--;
    numQueensInD1[row+col]--;
    numQueensInD2[n-col-1+row]--;
}

// computes the number of conflicts at the given row & column
public int countConflicts(int row, int col) {
    return (numQueensInRow[row]-1) + (numQueensInD1[row+col]-1) + (numQueensInD2[n-col-1+row]-1);
}
```

The **computeMinConflictRow(int row, int col)** method computes the row within a column that has the fewest number of conflicts. The method cannot return the current row, as that would result in the queen not moving positions! An almost-identical method exists without this row restriction, which allows for any row within a specified column to be selected. The greedy placement of queens to build the initial board configuration uses this method. The *min-conflicts heuristic* is implemented as follows:
```java
public int computeMinConflictRow(int row, int col) {
    // compute minimum value in column
    int minVal = Integer.MAX_VALUE;
    int currVal;
    for (int i = 0; i < n; i++) {
        currVal = countConflicts(i, col);
        if (i != row && currVal < minVal)
            minVal = currVal;
    }

    // collect all rows with the minimum value
    ArrayList<Integer> minRows = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        if (i != row && countConflicts(i, col) == minVal)
            minRows.add(i);
    }

    // randomly select a row with the minimum value
    return minRows.get(rand.nextInt(minRows.size()));
}
```

After a **repair** is applied to a board, it is checked to see if it is a solution. A board configuration is a solution if all queens have **zero** conflicts. As the number of conflicts for a given queen can be computed in **O(1)** time, the total complexity for computing whether or not a board configuration is a solution can be computed in **O(n)** time. Determining whether a board configuration is a solution is given in the following two methods:
```java
// a solution has been found if every queen on the board has 0 conflicts
public boolean isSolution() {
    int row;
    for (int col = 0; col < n; col++) {
        row = queens[col];
        if (countConflicts(row, col) > 0)
            return false;
    }

    return true;
}
```

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
