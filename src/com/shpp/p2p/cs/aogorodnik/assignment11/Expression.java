package com.shpp.p2p.cs.aogorodnik.assignment11;

/**
 * An object of this class represents single expression
 */
public class Expression implements Constants {

    /* Root node of abstract syntax tree */
    private final Node ast;

    /**
     * Constructor. Takes a string, checks if it's valid,
     * removes extra spaces. Parses the expression into AST
     */
    public Expression (String initExp) {
        /* Remove spaces */
        String strExp = initExp.replaceAll("\\s+","");

        /* Test validity */
        ExpValidator validator = new ExpValidator(strExp);
        if (!validator.isValid()) {
            System.out.println("Error: Something is wrong with exp syntax");
            System.exit(EXIT_FAILURE);
        }

        ast = parseExp(strExp);
    }

    public Node getExp() {
        return ast;
    }

    /**
     * Takes a string and parses it into AST, example:
     * "1+(1+3*sin(-x))^7"        * "1-2-3-(4-5)"
     *                 [+]        *              [-]
     *                /  \        *            /    \
     *              [^]  [1]      *         [-]      [-]
     *             /  \           *        /  \     /  \
     *           [+]  [7]         *     [-]  [3]  [4]  [5]
     *          /  \              *    /  \
     *        [*]  [1]            *  [1]  [2]
     *       /  \                 *
     *   [sin]  [3]               *
     *     |                      *
     *    [-x]                    *
     *                            *
     */
    private Node parseExp(String str) {
        Node current;

        /* Base cases */
        if (ExpValidator.isNumber(str)) {
            return new Node(str, nodeType.VAL);
        }
        else if (ExpValidator.isVariable(str)) {
            return new Node(str, nodeType.VAR);
        }

        /* Else, current node value is operation with lowest priority */
        int lp = indexOfLowestPriorityOp(str);
        if (lp == -1)
            return parseExp(str.substring(1, str.length() - 1));
        current = valueOfCurrentNode(str, lp);

        /* Divide string into 1-2 parts. Only one part, if function or negation */
        if (ExpValidator.isLetter( str.charAt(lp) )) {
            String substr = str.substring(str.indexOf('(') + 1, str.length() - 1);
            current.left = parseExp(substr);
        }
        else if (str.charAt(lp) == '-' && lp == 0) {
            String substr = str.substring(1);
            current.left = parseExp("0");
            current.right = parseExp(substr);
        }
        /* Two parts, if binary operator */
        else {
            String substr1 = str.substring(0, lp);
            String substr2 = str.substring(lp + 1);
            current.left = parseExp(substr1);
            current.right = parseExp(substr2);
        }

        return current;
    }

    /**
     * Defines the value of current node according to the name of operation/function
     * with a given index
     * @param str The expression
     * @param index Index of the operation/function
     * @return Creates node and returns it
     */
    private Node valueOfCurrentNode(String str, int index) {
        /* If lowest priority operation is a function */
        if (ExpValidator.isLetter(str.charAt(index))) {
            StringBuilder funName = new StringBuilder();
            for (int i = index; str.charAt(i) != '('; i++)
                funName.append(str.charAt(i));
            return new Node(funName.toString(), nodeType.FUN);
        }
        /* Otherwise, it's an operation */
        return new Node(String.valueOf(str.charAt(index)), nodeType.OP);
    }

    /**
     * Given an expression, finds the index of the operation
     * with the lowest priority, which sits on 1st level of parenthesis nesting.
     * Returns -1 if the first level doesn't have any operations (e.g. "(((1+1)))")
     * Example:
     * in: "1-2-3", out: 3
     */
    private int indexOfLowestPriorityOp(String exp) {
        /* Is set to the number of level which iterative index points at */
        int level = 1;
        int result = -1;
        String l1s = extractSymbolsFromLevel1(exp);

        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '(') level++;
            if (exp.charAt(i) == ')') level--;
            if (level != 1) continue;

            /* We're on level 1 now */
            if (exp.charAt(i) == '+')
                result = i;

            /* Special case for testing negation */
            if (exp.charAt(i) == '-' && (i == 0 || !ExpValidator.isOperator(exp.charAt(i - 1))))
                result = i;

            /* Test if lowest priority op is *,/ */
            if (!l1s.contains("+") && !l1s.contains("-") &&
                    (exp.charAt(i) == '*' || exp.charAt(i) == '/'))
                result = i;

            /* Test if lowest priority op is ^ */
            if (!l1s.contains("+") && !l1s.contains("-") && !l1s.contains("*") && !l1s.contains("/") && exp.charAt(i) == '^')
                result = i;

            /* Test if lowest priority op is a function */
            if (!l1s.contains("+") && !l1s.contains("-") && !l1s.contains("*") && !l1s.contains("/") && !l1s.contains("^")
                && ExpValidator.isLetter(exp.charAt(i)) && ExpValidator.isLetter(exp.charAt(i + 1))) {
                result = i;
                /* There can be only one function, so break immediately. Always returns 0 */
                break;
            }
        }
        return result;
    }

    /**
     * Given an expression, extracts all symbols from level 1.
     * Warning: doesn't include negation symbols! (eg. in: "2+-8", out: "2+8")
     * Example:
     * in: "(2+2)+x+(-1)", out: "+x+"
     */
    private String extractSymbolsFromLevel1(String exp) {
        /* Is set to the number of level which iterative index points at */
        int level = 1;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '(') level++;
            if (exp.charAt(i) == ')') level--;
            if (level != 1) continue;
            /* Test the negation case */
            if (i == 0 || !ExpValidator.isOperator(exp.charAt(i - 1)))
                result.append(exp.charAt(i));
        }
        return result.toString();
    }

}