# Assignment 3 Technical Document

>CISC 352: Artificial Intelligence  
>Sean Nesdoly 13sn50 10135490  
>Mary Hoekstra 13meh9 10129863  
>March 19th, 2017  

#### Codebase Contributions
- Mary Hoekstra wrote the solution to the N-Queens problem
- Sean Nesdoly wrote an implementation of the AlphaBeta pruning algorithm


## N-Queens Problem
TODO: write up

## Alpha-Beta Pruning
The *Alpha-Beta Pruning algorithm* is a search algorithm that seeks to decrease the number of vertices that are evaluated by the **minimax** algorithm in its search tree. This is often used in the context of two-player machine playing *zero-sum games*. The term "minimax" refers to the goal of each player: to **minimize** their opponents **maximum** possible score. This algorithm yields the same solution as the *minimax* algorithm. In addition, it has the possibility of reducing the number of vertices visited during the tree traversal. This is accomplished by *pruning* branches of the tree that cannot possibly influence the final solution.

#### Data Structures

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

#### Algorithm Implementation
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
