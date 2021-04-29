package com.shpp.p2p.cs.aogorodnik.assignment11;

import java.util.HashMap;

public interface Constants {

    /* Exit failure */
    int EXIT_FAILURE = 1;


    /* Node type */
    enum nodeType {
        OP, /* Binary operator */
        FUN, /* Function, unary operator */
        VAL, /* Value of operand */
        VAR /* Variable */
    }

    /* Operations map */
    HashMap<String, IActionOp> opmap = new HashMap<>() {
        {
            put("+", Double::sum);
            put("-", (double x, double y) -> x - y);
            put("*", (double x, double y) -> x * y);
            put("/", (double x, double y) -> x / y);
            put("^", Math::pow);
        }};

    /* Functions map */
    HashMap<String, IActionFun> funmap = new HashMap<>() {
        {
            put("sin", Math::sin);
            put("cos", Math::cos);
            put("tan", Math::tan);
            put("atan", Math::atan);
            put("log10", Math::log10);
            put("log2", (double x) -> Math.log(x) / Math.log(2));
            put("sqrt", Math::sqrt);
        }};

}

interface IActionOp {
    double calculate(double x, double y);
}

interface IActionFun {
    double calculate(double x);
}
