package com.shpp.p2p.cs.aogorodnik.assignment11;

/**
 * This is the main class of the evaluator project.
 * It is responsible for initialising components
 * and uniting the consecutive steps.
 *
 * The program is an expression evaluator, which supports
 * +,-,*,/,^ operations, negative numbers and numbers with floating point,
 * parentheses (), the following functions: sin, cos, tan, atan, log10, log2, sqrt
 * and single-letter variables
 */
public class Assignment11Part1 implements Constants {

    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Error: You should provide an expression to evaluate");
            System.exit(EXIT_FAILURE);
        }
        try {
            Expression exp = new Expression(args[0]);
            Evaluator evaluator = new Evaluator(exp.getExp(), args);
            System.out.println("Answer: " + evaluator.getAnswer());
        } catch (Exception e) {
            System.out.println("Error: unexpected error occurred");
            System.exit(EXIT_FAILURE);
        }
    }

}