package com.shpp.p2p.cs.aogorodnik.assignment10;

/**
 * This is the main class of the evaluator project.
 * It is responsible for initialising components
 * and uniting the consecutive steps
 */
public class Assignment10Part1 implements Constants {

    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Error: You should provide an expression to evaluate");
            System.exit(EXIT_FAILURE);
        }
        Expression exp = new Expression(args);
        Evaluator evaluator = new Evaluator(exp.getExp());
        System.out.println(evaluator.getAnswer());
    }

}