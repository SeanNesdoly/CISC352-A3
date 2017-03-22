# Assignment 3

>CISC 352: Artificial Intelligence  
>Sean Nesdoly & Mary Hoekstra  
>March 19th, 2017  

This repository implements the algorithms required for solving the following two problems that are outlined in the assignment 3 documentation for CISC352:
1. The **N-Queens puzzle** is the problem of placing **n** chess queens on an **n-by-n** chessboard so that no two queens threaten each other. This problem is solved using an **iterative repair algorithm** where conflicts are resolved using the **minimum conflicts** heuristic.
2. The **Alpha-Beta Pruning algorithm** is a search algorithm that seeks to decrease the number of nodes that are evaluated by the **minimax algorithm** in its search tree. This is used in the context of a machine playing implementation of **zero-sum games**; the "minimax" refers to each player **minimizing** the **maximum** payoff possible for the other.

---

### Running the N-Queens Problem
```bash
cd path/to/CISC352-Assignment3
./gradlew -Pmode=1 -q # quiet build
```

### Running Alpha-Beta Pruning
```bash
cd path/to/CISC352-Assignment3
./gradlew -Pmode=2 -q # quiet build
```

### Program Input/Output

Here is the project structure:

```
├── README.md
├── TechnicalDocument.md
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
└── src
    ├── io
    │   ├── alphabeta.txt
    │   ├── alphabeta_out.txt
    │   ├── nqueens.txt
    │   └── nqueens_out.txt
    └── java
        ├── AlphaBeta.java
        ├── Main.java
        ├── NQueens.java
        └── TextFile.java
```

The folder `src/io/` contains the specified input & output files for both the **N-Queens** problem and **AlphaBeta Pruning**. Edit the files as desired, *but do not move them to another directory*. The program is coded to read & write exact file names from `src/io/`. If *absolutely* necessary, you may change the constants contained within `TextFile.java`, but be sure to recompile.

### Build Process
To build and run a problem without installing the *gradle* build tool, run the `gradlew` executable from the command line. If the *gradle* build tool is installed, you may run `gradle` instead. Here, **n=1** runs *NQueens* and **n=2** runs *AlphaBeta Pruning*:

```bash
cd path/to/CISC352-Assignment3
./gradlew -Pmode=n -q
gradle -Pmode=n -q # alternative to above (requires gradle build tool)
```

For Windows operating systems, there is an equivalent `gradlew.bat` batch file in the project root directory that may be executed in a similar manner.

Alternatively, you may run a problem by building and running the compiled `.jar` file:

```bash
cd path/to/CISC352-Assignment3
gradle build
java -Xmx6g -jar build/libs/CISC352-Assignment3.jar n
```
