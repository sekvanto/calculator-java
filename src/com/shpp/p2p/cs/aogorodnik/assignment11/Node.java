package com.shpp.p2p.cs.aogorodnik.assignment11;

/**
 * Represents a single node of an abstract syntax tree
 */
public class Node implements Constants {
    String value;
    nodeType type;

    Node left;
    Node right;

    Node(String value, nodeType type) {
        this.value = value;
        this.type = type;
        right = null;
        left = null;
    }

}