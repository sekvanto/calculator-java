package com.shpp.p2p.cs.aogorodnik.assignment10;

/**
 * An object of this class represents
 * an expression validator
 */
public class ExpValidator {

    private boolean valid;

    /**
     * Constructor. Takes a string and sets valid
     * to true if this string was valid
     */
    public ExpValidator(String exp) {
        if (validate(exp)) valid = true;
    }

    public boolean isValid() {
        return valid;
    } /* IMPORTANT: allow only 1-letter variables */

    /**
     * This method takes a string, and checks if
     * it's a valid expression. Returns true if it is one
     */
    private boolean validate(String exp) {
        return true; // dummy
    }

    public static boolean isOperator(char sym) {
        String operators = "+-*/^";
        return operators.contains(String.valueOf(sym));
    }

}