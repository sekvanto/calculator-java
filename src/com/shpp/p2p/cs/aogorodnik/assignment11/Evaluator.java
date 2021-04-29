package com.shpp.p2p.cs.aogorodnik.assignment11;

import java.util.HashMap;

public class Evaluator implements Constants {

    private final double answer;

    /**
     * Constructor. Takes an expression and variables, binds variables and evaluates exp
     */
    public Evaluator(Node ast, String[] args) {
        HashMap<Character, Double> variables = createVariablesMap(args);
        answer = evaluate(ast, variables);
    }

    public double getAnswer() {
        return answer;
    }

    /**
     * Analyzes the args[] array, returns null if no variables
     * are defined; returns a HashMap with parsed variables otherwise
     */
    private HashMap<Character, Double> createVariablesMap(String[] args) {
        HashMap<Character, Double> result = new HashMap<>();

        if (args.length == 1)
            return null;
        for (int i = 1; i < args.length; i++) {
            /* Remove spaces */
            String var = args[i].replaceAll("\\s+","");

            /* Test arg validity */
            ExpValidator.testArgumentValidity(var);

            /* Parse */
            Character varName = var.split("=")[0].charAt(0);
            Double varNum = Double.parseDouble(var.split("=")[1]);
            result.put(varName, varNum);
        }
        return result;
    }

    /**
     * Evaluator main function. Calculates the result of given expression,
     * represented with an abstract syntax tree. First, optionally bind variables,
     * then provides calculations
     */
    private double evaluate(Node current, HashMap<Character, Double> variables) {
        /* Current node is a number? Return value */
        if (current.type == nodeType.VAL) return Double.parseDouble(current.value);

        /* Variable? Bind and return value */
        if (current.type == nodeType.VAR) {
            if (variables == null || !variables.containsKey(current.value.charAt(0))) {
                System.out.println("Error: expression contains undefined variables");
                System.exit(EXIT_FAILURE);
            }
            return variables.get(current.value.charAt(0));
        }

        /* Function? Return calculated result of evaluation of left node */
        if (current.type == nodeType.FUN) {
            return funmap.get(current.value).calculate(evaluate(current.left, variables));
        }

        /* Otherwise, current node is a binary operation */
        return opmap.get(current.value).calculate(evaluate(current.left, variables), evaluate(current.right, variables));
    }

}