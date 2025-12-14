package dataStructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ExpressionTest {

    @Test
    public void testBuildPostfixAndEvaluate() {
        System.out.println("--- Test Postfix Build & Eval ---");
        Expression expr = new Expression();
        expr.buildPostfix("3 4 +");

        double result = expr.evaluate();
        assertEquals(7.0, result, 0.001);

        System.out.println("Calculated: " + result);
    }

    @Test
    public void testBuildPrefixAndEvaluate() {
        System.out.println("--- Test Prefix Build & Eval ---");
        Expression expr = new Expression();
        expr.buildPrefix("* + 2 3 4");

        double result = expr.evaluate();
        assertEquals(20.0, result, 0.001);
        System.out.println("Calculated: " + result);
    }

    @Test
    public void testBuildInfixAndEvaluate() {
        System.out.println("--- Test Infix Build & Eval ---");
        Expression expr = new Expression();
        expr.buildInfix("2 + 3 * 5");

        double result = expr.evaluate();
        assertEquals(17.0, result, 0.001);
        System.out.println("Calculated: " + result);
    }

    @Test
    public void testInfixPrintFormat() {
        System.out.println("--- Test Infix Printing ---");
        Expression expr = new Expression();
        expr.buildPostfix("a b +");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            expr.printInfix();
        } finally {
            System.setOut(originalOut);
        }

        String output = outContent.toString().trim();
        System.out.println("Captured Output: " + output);

        assertTrue(output.contains("(") && output.contains(")"));
    }

    @Test
    public void testPrefixPrintFormat() {
        System.out.println("--- Test Prefix Printing ---");
        Expression expr = new Expression();
        expr.buildInfix("2 + 3");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            expr.printPrefix();
        } finally {
            System.setOut(originalOut);
        }

        String output = outContent.toString().trim();
        System.out.println("Captured Output: " + output);

        assertTrue(output.contains("Prefix form:"));
    }

    @Test
    public void testPostfixPrintFormat() {
        System.out.println("--- Test Postfix Printing ---");
        Expression expr = new Expression();
        expr.buildInfix("2 + 3");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            expr.printPostfix();
        } finally {
            System.setOut(originalOut);
        }

        String output = outContent.toString().trim();
        System.out.println("Captured Output: " + output);

        assertTrue(output.contains("Postfix form:"));
    }
}