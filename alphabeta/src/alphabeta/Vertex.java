package alphabeta;

/*
 * A vertex with a branching factor in the range 2<=b<=1024.
 * May be classified as a MAX or MIN vertex. Leaf vertices are
 * defined by identifiers that contain a number and have an empty type attribute.
 *
 * CISC 352 Assignment 3
 * Sean Nesdoly & Mary Hoekstra
 * March 22nd, 2017
 */

import java.util.*;

public class Vertex {

    String v; // vertex identifier
    String type; // MAX or MIN vertex
    ArrayList<Vertex> children; // child vertices
    boolean isRoot = false; // denotes the root vertex
    boolean isLeaf = false; // leaf vertices are identified by numbers
    double leafValue; // value of a leaf

    public Vertex(String _v, String _t) {
        v = _v;

        type = _t;
        if (type.isEmpty()) { // empty type field denotes a leaf node
            isLeaf = true;
            leafValue = Double.parseDouble(v);
        }

        if (!isLeaf)
            children = new ArrayList<>();
    }

    // adds in a leaf vertex as a child of this vertex; returns the created leaf
    public Vertex addLeafVertex(String _v) {
        Vertex leaf = new Vertex(_v, ""); // empty type argument creates a leaf vertex
        children.add(leaf);

        return leaf;
    }

    // a leaf vertex contains a number in its name
    public static boolean isLeafVertex(String _v) {
        return (_v.matches(".*\\d+.*"));
    }

    public boolean isMaxVertex() {
        return type.equals("MAX");
    }

    public boolean isMinVertex() {
        return type.equals("MIN");
    }

    // number of children
    public int branchFactor() {
        return (isLeaf ? 0 : children.size());
    }

    @Override
    public String toString() {
        String out = (isRoot ? "root=" : "") + v + " (" + (isLeaf ? "leaf" : type) + ") ";

        if (!isLeaf) {
            out += "c=";
            out = children.stream().map((c) -> c.v + ",").reduce(out, String::concat);
            out = out.substring(0, out.length()-1);
        }

        return out;
    }

}
