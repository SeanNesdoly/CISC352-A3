# Assignment 3

>CISC 352: Artificial Intelligence  
>Sean Nesdoly & Mary Hoekstra  
>March 19th, 2017  

This repository implements the algorithms required for solving the following two problems that are outlined in the assignment 3 documentation for CISC352:
1. The **N-Queens puzzle** is the problem of placing **n** chess queens on an **n-by-n** chessboard so that no two queens threaten each other. This problem is solved using an **iterative repair algorithm** where conflicts are resolved using the **minimum conflicts** heuristic.
2. The **Alpha-Beta Pruning algorithm** is a search algorithm that seeks to decrease the number of nodes that are evaluated by the **minimax algorithm** in its search tree. This is used in the context of a machine playing implementation of **zero-sum games**; the "minimax" refers to each player **minimizing** the **maximum** payoff possible for the other.

---

### Build Process

Compiled `JAR` files have been included under the root project directory for each problem. Note that Java version 1.8 was used.

If required to build a project, there are two methods that may be used:
- *gradle* build tool **not** installed: run the `gradlew` executable or the `graddlew.bat` batch file (for Windows)
- *gradle* build tool **is** installed: from the project root directory, run the `gradle build` command

### Running the N-Queens Problem
Using the compiled `JAR` file:
```bash
cd path-to-CISC352-Assignment3/nqueens
java -Xmx6g -jar nqueens.jar
```

Using the `gradle` executable:
```bash
cd path-to-CISC352-Assignment3/nqueens
./gradlew -q # if gradle is NOT installed
gradle -q # if gradle is installed
```

### Running Alpha-Beta Pruning
Using the compiled `JAR` file:
```bash
cd path-to-CISC352-Assignment3/alphabeta
java -jar alphabeta.jar
```

Using the `gradle` executable:
```bash
cd path-to-CISC352-Assignment3/alphabeta
./gradlew -q # if gradle is NOT installed
gradle -q # if gradle is installed
```

### Program Input/Output

In each projects root directory there is a folder named `src/`. The specified input & output files are contained within this folder. Edit the files as desired, but do not move them to another directory. Both programs are coded to read from & write to the directory `src/`.

Here are the important components of the project structure for reference:

```bash
├── README
├── TechnicalDocument.pdf
├── alphabeta
│   ├── alphabeta.jar # JAR file
│   ├── gradlew     # Unix executable
│   ├── gradlew.bat # Windows executable
│   └── src
│       ├── alphabeta
│       │   ├── AlphaBeta.java
│       │   ├── Main.java
│       │   └── TextFile.java
│       ├── alphabeta.txt       # input file
│       └── alphabeta_out.txt   # output file
└── nqueens
    ├── nqueens.jar # JAR file
    ├── gradlew     # Unix executable
    ├── gradlew.bat # Windows executable
    └── src
        ├── java
        │   ├── Main.java
        │   ├── NQueens.java
        │   └── TextFile.java
        ├── nqueens.txt     # input file
        └── nqueens_out.txt # output file

```
