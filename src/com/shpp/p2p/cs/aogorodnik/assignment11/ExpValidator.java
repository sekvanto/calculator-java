package com.shpp.p2p.cs.aogorodnik.assignment11;

/**
 * An object of this class represents
 * an expression validator. Has a couple of public methods (see below).
 */
public class ExpValidator implements Constants {

    private boolean valid;

    /**
     * Constructor. Takes an expression and sets valid
     * to true if this expression was valid
     */
    public ExpValidator(String exp) {
        valid = false;

        checkSymbols(exp);
        checkOperators(exp);
        checkFunctions(exp);
        checkVariables(exp);

        valid = true;
    }

    public boolean isValid() {
        return valid;
    }

    /**
     * Check if string contains only valid symbols
     */
    private void checkSymbols(String exp) {
        if (exp.equals("")) {
            System.out.println("Error: Empty expression");
            System.exit(EXIT_FAILURE);
        }
        for (int i = 0; i < exp.length(); i++) {
            String sym = String.valueOf(exp.charAt(i));
            if (!isOperator(exp.charAt(i)) && !sym.matches("[A-Za-z0-9]") && !(exp.charAt(i) == '.') &&
                    !(exp.charAt(i) == '(') && !(exp.charAt(i) == ')')) {
                System.out.print("Error: Illegal symbol! Expression can contain only ");
                System.out.println("operators (+,-,*,/,^), parentheses, functions, variables and numbers (with dot for floating)");
                System.exit(EXIT_FAILURE);
            }
        }
    }

    /**
     * Takes a string and checks validity of operators
     */
    private static void checkOperators(String exp) {
        /* Check if start/end is valid */
        if ((isOperator(exp.charAt(0)) && exp.charAt(0) != '-') || isOperator(exp.charAt(exp.length() - 1))) {
            System.out.println("Error: Expression must start and end with a number/variable/parentheses/negation sign");
            System.exit(EXIT_FAILURE);
        }
        /* Check if no operators are near each other */
        for (int i = 0; i < exp.length(); i++) {
            if (isOperator(exp.charAt(i)) && isOperator(exp.charAt(i + 1))) {
                if (exp.charAt(i + 1) != '-') {
                    System.out.println("Error: Two or more operators near each other");
                    System.exit(EXIT_FAILURE);
                }
            }
        }
    }

    /**
     * Checks validity of variables in expression
     */
    private void checkVariables(String exp) {
        /* Check if letters are concatenated with numbers in illegal way */
        if (exp.matches(".*[^a-zA-Z0-9][0-9]+[a-zA-Z]+.*") || exp.matches("[0-9]+[a-zA-Z]+.*")) {
            System.out.println("Error: Number concatenated with a variable");
            System.exit(EXIT_FAILURE);
        }
    }

    /**
     * Checks validity of functions in expression
     */
    private void checkFunctions(String exp) {
        StringBuilder function = new StringBuilder();
        /* Check validity of functions */
        for (int i = 0; i < exp.length() - 1; i++) {
            if (isLetter(exp.charAt(i)) && (isLetter(exp.charAt(i+1)) || isNumber(exp.charAt(i+1)))) {
                while (!isValidSpecialSymbol(exp.charAt(i))) {
                    function.append(exp.charAt(i));
                    i++;
                    if (i == exp.length()) break;
                }
                if (!funmap.containsKey(function.toString())) {
                    System.out.println("Error: Invalid function: " + function.toString());
                    System.exit(EXIT_FAILURE);
                }
                function = new StringBuilder();
            }
        }
    }

    //////////////////////////////////
    ////                          ////
    ////    PUBLIC METHODS        ////
    ////                          ////
    //////////////////////////////////

    /**
     * Checks the validity of variable argument
     * initialization, e.g. "a=8"
     */
    public static void testArgumentValidity(String arg) {
        if (arg.equals("")) {
            System.out.println("Error: argument cannot be an empty string");
            System.exit(EXIT_FAILURE);
        }
        if (!arg.matches("[a-zA-Z]=(-)?[0-9]+(\\.[0-9]+)?")) {
            System.out.println("Error: invalid argument initialization");
            System.exit(EXIT_FAILURE);
        }
    }

    public static boolean isOperator(char sym) {
        String operators = "+-*/^";
        return operators.contains(String.valueOf(sym));
    }

    public static boolean isValidSpecialSymbol(char sym) {
        String symbols = "+-*/^().";
        return symbols.contains(String.valueOf(sym));
    }

    public static boolean isLetter(char sym) {
        String s = String.valueOf(sym);
        return s.matches("[A-Za-z]");
    }

    /* isDigit */
    public static boolean isNumber(char sym) {
        String s = String.valueOf(sym);
        return s.matches("[0-9]");
    }

    public static boolean isVariable(String str) { return str.matches("[A-Za-z]"); }
    public static boolean isNumber(String str) { return str.matches("(-)?[0-9]+(\\.[0-9]+)?"); }

}