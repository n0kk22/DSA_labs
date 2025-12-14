package dataStructures;

import java.util.Scanner;
import java.util.Stack;

public class Expression extends LinkedBinaryTree {

    public Expression() {
        super();
    }

    public void printInfix() {
        if (this.root() == null) return;
        printInfixHelper(root);
        System.out.println();
    }

    private void printInfixHelper(BinaryTreeNode t) {
        if (t != null) {
            if (t.getLeftChild() != null || t.getRightChild() != null) {
                System.out.print("(");
            }

            printInfixHelper(t.getLeftChild());
            System.out.print(t.getElement() + " ");
            printInfixHelper(t.getRightChild());

            if (t.getLeftChild() != null || t.getRightChild() != null) {
                System.out.print(")");
            }
        }
    }

    public void printPrefix() {
        System.out.println("Prefix form:");
        this.preOrderOutput();
        System.out.println();
    }

    public void printPostfix() {
        System.out.println("Postfix form:");
        this.postOrderOutput();
        System.out.println();
    }

    public void buildPostfix(String expression) {
        Stack<LinkedBinaryTree> stack = new Stack<>();
        String[] tokens = expression.split("\\s+");

        for (String token : tokens) {
            if (isOperator(token)) {
                LinkedBinaryTree rightTree = stack.pop();
                LinkedBinaryTree leftTree = stack.pop();
                LinkedBinaryTree newTree = new LinkedBinaryTree();
                newTree.makeTree(token.charAt(0), leftTree, rightTree);
                stack.push(newTree);
            } else {
                LinkedBinaryTree operandTree = new LinkedBinaryTree();
                operandTree.makeTree(tryParse(token), new LinkedBinaryTree(), new LinkedBinaryTree());
                stack.push(operandTree);
            }
        }

        if (!stack.isEmpty()) {
            LinkedBinaryTree result = stack.pop();
            this.root = result.root;
        }
    }

    public void buildPrefix(String expression) {
        Stack<LinkedBinaryTree> stack = new Stack<>();
        String[] tokens = expression.split("\\s+");

        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if (isOperator(token)) {
                LinkedBinaryTree leftTree = stack.pop();
                LinkedBinaryTree rightTree = stack.pop();
                LinkedBinaryTree newTree = new LinkedBinaryTree();
                newTree.makeTree(token.charAt(0), leftTree, rightTree);
                stack.push(newTree);
            } else {
                LinkedBinaryTree operandTree = new LinkedBinaryTree();
                operandTree.makeTree(tryParse(token), new LinkedBinaryTree(), new LinkedBinaryTree());
                stack.push(operandTree);
            }
        }
        if (!stack.isEmpty()) {
            this.root = stack.pop().root;
        }
    }

    public void buildInfix(String expression) {
        String postfix = infixToPostfix(expression);
        buildPostfix(postfix);
    }

    public double evaluate() {
        return evaluateNode(root);
    }

    private double evaluateNode(BinaryTreeNode node) {
        if (node == null) return 0;

        if (node.getLeftChild() == null && node.getRightChild() == null) {
            return getValue(node.getElement());
        }

        double leftVal = evaluateNode(node.getLeftChild());
        double rightVal = evaluateNode(node.getRightChild());
        char operator = node.getElement().toString().charAt(0);

        return calculate(leftVal, rightVal, operator);
    }

    private double calculate(double a, double b, char op) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            case '^' -> Math.pow(a, b);
            default -> 0;
        };
    }

    private double getValue(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        String str = obj.toString();
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter value for variable '" + str + "': ");
            return sc.nextDouble();
        }
    }

    private boolean isOperator(String token) {
        return "+-*/^".contains(token);
    }

    private Object tryParse(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            return token;
        }
    }

    private String infixToPostfix(String infix) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        infix = infix.replaceAll("\\s+", "");

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                result.append(c).append(" ");
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(" ");
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && priority(c) <= priority(stack.peek())) {
                    result.append(stack.pop()).append(" ");
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(" ");
        }
        return result.toString().trim();
    }
    private int priority(char operator) {
        switch (operator) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^': return 3;
        }
        return -1;
    }
}