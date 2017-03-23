package nqueens;

/*
 * A utility class that handles input & output from a text file.
 * Customized to handle the required file formats of the NQueens problem.
 *
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 22nd, 2017
 */

 import java.io.FileNotFoundException;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.util.ArrayList;
 import java.util.Scanner;

public class TextFile {

    private static final String SOURCEDIR = "./src/"; // source directory for input & output files
    private static final String IN = SOURCEDIR + "nqueens.txt"; // input file name
    private static final String OUT = SOURCEDIR + "nqueens_out.txt"; // output file name

    private ArrayList<String> contents;

    public TextFile() throws FileNotFoundException {
        this.contents = readFile();
    }

    // Returns the contents of the file. Note this is not a deep copy.
    public ArrayList<String> getContents() {
        return this.contents;
    }

    /*
     * Reads in the contents of the input file from the source directory.
     * Each line of text is added as an element to an ArrayList.
     *
     * @return a list of strings, where each element is a line of text from the input file
     */
    public ArrayList<String> readFile() throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();

        // select the input file from the source directory
        try (Scanner sf = new Scanner(new FileReader(IN))) {
            while (sf.hasNext()) {
                lines.add(sf.nextLine());
            }
        }

        return lines;
    }

    /*
     * Writes the specified text to the output file in the source directory.
     *
     * @param outTxt the text to write to file
     * @return void
     */
     public void writeFile(String outTxt) throws IOException {
        try (FileWriter fw = new FileWriter(OUT); PrintWriter output = new PrintWriter(fw)) {
            output.print(outTxt);
        }
     }
}
