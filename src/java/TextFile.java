/*
 * A utility class that handles input & output from a text file.
 * Customized to handle the required file formats for CISC352 Assignment 3.
 *
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 20th, 2017
 */

 import java.util.Scanner;
 import java.io.IOException;
 import java.io.FileWriter;
 import java.io.PrintWriter;
 import java.io.FileNotFoundException;
 import java.io.FileReader;
 import java.util.ArrayList;

public class TextFile {

    private String in; // input file name
    private String out; // output file name

    private static final String SOURCEDIR = "./src/io/"; // source directory for input & output files
    
    public static final int NQUEENS = 1;
    public static final int ALPHABETA = 2;

    public TextFile(int mode) {
        if (mode == 1) {
            in = SOURCEDIR + "nqueens.txt";
            out = SOURCEDIR + "nqueens_out.txt";
        } else if (mode == 2) {
            in = SOURCEDIR + "alphabeta.txt";
            out = SOURCEDIR + "alphabeta_out.txt";
        }
    }

    /*
     * Reads in the contents of the input file from the source directory.
     * Each line of text is added as an element to an ArrayList.
     *
     * @return a list of strings, where each element is a line of text from the input file
     */
    public ArrayList<String> readFile() throws FileNotFoundException {
        ArrayList<String> lines;
        
        // select the input file from the source directory
        try (Scanner sf = new Scanner(new FileReader(in))) {
            lines = new ArrayList<>();
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
        try (FileWriter fw = new FileWriter(out); PrintWriter output = new PrintWriter(fw)) {
            output.print(outTxt);
        }
     }
}
