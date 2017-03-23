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
cd path-to-CISC352-Assignment3/nqueens
./gradlew -q # or "gradle -q" (if gradle is installed)
```

### Running Alpha-Beta Pruning
```bash
cd path-to-CISC352-Assignment3/alphabeta
./gradlew -q # or "gradle -q" (if gradle is installed)
```

### Program Input/Output

Here is the project structure:

```bash
├── README
├── TechnicalDocument.pdf
├── alphabeta
│   ├── build.gradle
│   ├── gradle
│   │   └── wrapper
│   │       ├── gradle-wrapper.jar
│   │       └── gradle-wrapper.properties
│   ├── gradlew
│   ├── gradlew.bat
│   └── src
│       ├── alphabeta
│       │   ├── AlphaBeta.java
│       │   ├── Main.java
│       │   └── TextFile.java
│       ├── alphabeta.txt       # input file
│       └── alphabeta_out.txt   # output file
└── nqueens
    ├── build.gradle
    ├── gradle
    │   └── wrapper
    │       ├── gradle-wrapper.jar
    │       └── gradle-wrapper.properties
    ├── gradle.properties
    ├── gradlew
    ├── gradlew.bat
    └── src
        ├── java
        │   ├── Main.java
        │   ├── NQueens.java
        │   └── TextFile.java
        ├── nqueens.txt     # input file
        └── nqueens_out.txt # output file

```

In each problem's root project directory, the folder `src/` contains the specified input & output files. Edit the files as desired, but do not move them to another directory. The program is coded to read & write exact file names from the directory `src/`.

### Build Process
To build and run a problem without installing the *gradle* build tool, run the `gradlew` executable from the command line. If the *gradle* build tool is installed, you may run `gradle` instead:

```bash
cd path-to-CISC352-Assignment3/nqueens
./gradlew -q
gradle -q # alternative to above (requires gradle build tool)

cd path-to-CISC352-Assignment3/nqueens
./gradlew -q # or: gradle -q
```

For Windows operating systems, there is an equivalent `gradlew.bat` batch file in each project root directory that may be executed in a similar manner.

---

Alternatively, you may run a problem by building and executing the compiled `.jar` file.  
**N-Queens:**
```bash
cd path-to-CISC352-Assignment3/nqueens
gradle build
java -Xmx6g -jar build/libs/nqueens.jar
```
**AlphaBeta Pruning:**
```bash
cd path-to-CISC352-Assignment3/alphabeta
gradle build
java -jar build/libs/alphabeta.jar
```
