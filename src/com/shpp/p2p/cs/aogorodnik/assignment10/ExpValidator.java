package com.shpp.p2p.cs.aogorodnik.assignment10;

/**
 * An object of this class represents
 * an expression validator
 */
public class ExpValidator implements Constants {

    private boolean valid;

    /**
     * Constructor. Takes a string and sets valid
     * to true if this string was valid
     */
    public ExpValidator(String exp) {
        valid = false;

        checkSymbols(exp);
        checkOperators(exp);
        checkLetters(exp);
        checkNumbers(exp);

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
            if (!isOperator(exp.charAt(i)) && !sym.matches("[A-Za-z0-9]") && !(exp.charAt(i) == '.')) {
                System.out.print("Error: Illegal symbol! Expression can contain only ");
                System.out.println("operators (+,-,*,/,^), variables and numbers (with dot for floating)");
                System.exit(EXIT_FAILURE);
            }
        }
    }

    /**
     * Takes a string and checks validity of operators
     */
    private void checkOperators(String exp) {
        if (isOperator(exp.charAt(0)) || isOperator(exp.charAt(exp.length() - 1))) {
            System.out.println("Error: Expression must start and end with a number/variable");
            System.exit(EXIT_FAILURE);
        }
        for (int i = 0; i < exp.length(); i++) {
            if (isOperator(exp.charAt(i)) && isOperator(exp.charAt(i + 1))) {
                if (exp.charAt(i + 1) != '-') {
                    System.out.println("Error: two or more operators near each other");
                    System.exit(EXIT_FAILURE);
                } else if (exp.charAt(i + 2) == '-') {
                    System.out.println("Error: three or more negations are not supported yet");
                    System.exit(EXIT_FAILURE);
                }
            }
        }
    }

    /**
     * Checks if letters in expression are valid variables
     */
    private void checkLetters(String exp) {
        for (int i = 0; i < exp.length() - 1; i++) {
            if ((isNumber(exp.charAt(i)) && isLetter(exp.charAt(i + 1))) ||
                (isLetter(exp.charAt(i)) && isNumber(exp.charAt(i + 1)))) {
                System.out.println("Error: numbers can't be concatenated with letters");
                System.exit(EXIT_FAILURE);
            }
        }
        for (int i = 0; i < exp.length() - 1; i++) {
            if (isLetter(exp.charAt(i)) && isLetter(exp.charAt(i + 1))) {
                System.out.println("Error: multiletter variables are not supported yet");
                System.exit(EXIT_FAILURE);
            }
        }
    }

    /**
     * Checks if numbers in expression are valid
     */
    private void checkNumbers(String exp) {
        /* Is set to true while parsing a number where one dot already was encountered */
        boolean dotFlag = false;
        for (int i = 0; i < exp.length(); i++) {
            /* Finished parsing one number, reset flag */
            if (isOperator(exp.charAt(i)))
                dotFlag = false;
            /* Encountered two dots in a number, error */
            if (dotFlag && exp.charAt(i) == '.') {
                System.out.println("Error: Number can't contain two or more dots");
                System.exit(EXIT_FAILURE);
            }
            /* Encountered first dot in a number */
            if (!dotFlag && exp.charAt(i) == '.')
                dotFlag = true;
        }
    }

    /**
     * Checks the validity of variable argument
     * initialization, e.g. "a=8"
     */
    public static void testArgumentValidity(String arg) {
        if (arg.equals("")) {
            System.out.println("Error: argument cannot be an empty string");
            System.exit(EXIT_FAILURE);
        }
        if (!arg.matches("[a-zA-Z]=[0-9]+(\\.[0-9]+)?")) {
            System.out.println("Error: invalid argument initialization");
            System.exit(EXIT_FAILURE);
        }
    }

    public static boolean isOperator(char sym) {
        String operators = "+-*/^";
        return operators.contains(String.valueOf(sym));
    }

    public static boolean isLetter(char sym) {
        String s = String.valueOf(sym);
        return s.matches("[A-Za-z]");
    }

    public static boolean isNumber(char sym) {
        String s = String.valueOf(sym);
        return s.matches("[0-9]");
    }

}