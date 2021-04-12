package com.shpp.p2p.cs.aogorodnik.assignment10;

/**
 * An object of this class represents single expression
 */
public class Expression {

    private String exp;

    /**
     * Constructor. Takes array of arguments, checks if
     * the expression in args[0] is valid, removes extra spaces.
     * Binds variables
     */
    public Expression (String args[]) {
        exp = args[0].replaceAll("\\s+",""); // Remove spaces
        ExpValidator validator = new ExpValidator(exp);
    }

}
