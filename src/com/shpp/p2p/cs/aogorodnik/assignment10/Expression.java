package com.shpp.p2p.cs.aogorodnik.assignment10;

import java.util.ArrayList;

/**
 * An object of this class represents single expression
 */
public class Expression implements Constants {

    private final ArrayList<String> exp;

    /**
     * Constructor. Takes array of arguments, checks if
     * the expression in args[0] is valid, removes extra spaces.
     * Binds variables. Parses the expression to array
     */
    public Expression (String[] args) {
        /* Remove spaces */
        String strExp = args[0].replaceAll("\\s+","");
        /* Test validity */
        ExpValidator validator = new ExpValidator(strExp);
        if (!validator.isValid()) {
            System.out.println("Error: Something is wrong with exp syntax");
            System.exit(EXIT_FAILURE);
        }
        if (args.length > 1) {
            strExp = bindVariables(strExp, args);
        }
        else if (args[0].matches(".*[a-zA-Z].*")) {
            System.out.println("Error: A value should be assigned to all variables");
            System.exit(EXIT_FAILURE);
        }
        ExpValidator.checkOperators(strExp);
        exp = parseExp(strExp);
    }

    public ArrayList<String> getExp() {
        return exp;
    }

    /**
     * Takes String variable and array, where
     * str is an expression, args[1:] are optional
     * variables initialization in form "a=3"
     */
    private String bindVariables(String str, String[] args) {
        for (int i = 1; i < args.length; i++) {
            /* Remove spaces */
            String var = args[i].replaceAll("\\s+","");

            /* Test arg validity */
            ExpValidator.testArgumentValidity(var);

            /* Bind */
            String varName = var.split("=")[0];
            String varNum = var.split("=")[1];
            str = str.replaceAll(varName, varNum);
        }
        /* Check if there are unbounded variables */
        if (str.matches(".*[a-zA-Z].*")) {
            System.out.println("Error: A value should be assigned to all variables");
            System.exit(EXIT_FAILURE);
        }
        return str;
    }

    /**
     * Takes a string and parses it into array, example:
     * "5+8*2+-38.1"
     * [5], [+], [8], [*], [2], [+], [-38.1]
     */
    private ArrayList<String> parseExp(String str) {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder element = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            element.append(str.charAt(i));
            /* Test if next symbol is operator */
            if (i != str.length() - 1 && ExpValidator.isOperator(str.charAt(i + 1))) {
                result.add(element.toString());
                result.add(String.valueOf(str.charAt(i + 1)));
                element = new StringBuilder();
                i++;
            }
        }
        result.add(element.toString());
        return result;
    }

}
