package com.shpp.p2p.cs.aogorodnik.assignment10;

import java.util.ArrayList;

public class Evaluator implements Constants {

    private final double answer;

    /**
     * Constructor. Takes an expression, evaluates it
     */
    public Evaluator(ArrayList<String> exp) {
        answer = evaluate(exp);
    }

    public double getAnswer() {
        return answer;
    }

    /**
     * Evaluator main function. Calculates the result of given expression,
     * represented with a string array
     */
    double evaluate(ArrayList<String> exp) {
        /* Return the answer if the expression consists of only one number */
        if (exp.size() == 1) return Double.parseDouble(exp.get(0));
        else {
            /* Output intermediate result */
            for (String el : exp)
                System.out.print(el + " ");
            System.out.print("\n");
        }

        if (exp.contains("^")) exp = calculateSubexp(exp, "^");
        else if (exp.contains("*")) exp = calculateSubexp(exp, "*");
        else if (exp.contains("/")) exp = calculateSubexp(exp, "/");
        else if (exp.contains("+")) exp = calculateSubexp(exp, "+");
        else if (exp.contains("-")) exp = calculateSubexp(exp, "-");

        /* Recursion until the expression is fully evaluated */
        return evaluate(exp);
    }

    /**
     * Does the op operation to the first subexpression of the expression
     */
    ArrayList<String> calculateSubexp(ArrayList<String> exp, String op) {
        int index = exp.indexOf(op);
        double num1 = Double.parseDouble(exp.get(index - 1));
        double num2 = Double.parseDouble(exp.get(index + 1));
        if (num2 == 0 && op.equals("/")) {
            System.out.println("Error: Division by 0 is not allowed");
            System.exit(EXIT_FAILURE);
        }

        double res = switch (op) {
            case "^" -> Math.pow(num1, num2);
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            default -> 0;
        };

        exp.set(index - 1, String.valueOf(res));
        exp.remove(index);
        exp.remove(index);
        return exp;
    }

}
