package edu.hse.chuev.reactiveCalculator.operations;

import org.junit.Test;
import static org.junit.Assert.*;

public class BinaryOperationTest {
    @Test
    public void getOperationSymbol() throws Exception {
        assertEquals('+', BinaryOperation.ADDITION.getOperationSymbol());
        assertEquals('-', BinaryOperation.SUBTRACTION.getOperationSymbol());
        assertEquals('*', BinaryOperation.MULTIPLICATION.getOperationSymbol());
        assertEquals(':', BinaryOperation.DIVISION.getOperationSymbol());
    }

    @Test
    public void getMathOperation_ADDITION() throws Exception {
        BinaryMathOperation operation = BinaryOperation.ADDITION.getMathOperation();

        assertEquals(3, operation.calculate(1,2), 1);
        assertEquals(3, operation.calculate(2,1), 1);
        assertEquals(8.65, operation.calculate(4.32,4.33), 0.01);
        assertEquals(-1.2, operation.calculate(-5.5,4.3), 0.1);
    }

    @Test
    public void getMathOperation_SUBTRACTION() throws Exception {
        BinaryMathOperation operation = BinaryOperation.SUBTRACTION.getMathOperation();

        assertEquals(-1, operation.calculate(1,2), 1);
        assertEquals(1, operation.calculate(2,1), 1);
        assertEquals(-0.01, operation.calculate(4.32,4.33), 0.01);
        assertEquals(-9.8, operation.calculate(-5.5,4.3), 0.1);
    }

    @Test
    public void getMathOperation_MULTIPLICATION() throws Exception {
        BinaryMathOperation operation = BinaryOperation.MULTIPLICATION.getMathOperation();

        assertEquals(2, operation.calculate(1,2), 1);
        assertEquals(2, operation.calculate(2,1), 1);
        assertEquals(18.7056, operation.calculate(4.32,4.33), 0.0001);
        assertEquals(-23.65, operation.calculate(-5.5,4.3), 0.01);
    }

    @Test
    public void getMathOperation_DIVISION() throws Exception {
        BinaryMathOperation operation = BinaryOperation.DIVISION.getMathOperation();

        assertEquals(0.5, operation.calculate(1,2), 0.1);
        assertEquals(2, operation.calculate(2,1), 1);
        assertEquals(0.9976, operation.calculate(4.32,4.33), 0.0001);
        assertEquals(-1.2791, operation.calculate(-5.5,4.3), 0.0001);
    }

    @Test
    public void calculate_ADDITION() throws Exception {
        BinaryOperation operation = BinaryOperation.ADDITION;

        assertEquals(3, operation.calculate(1,2), 1);
        assertEquals(3, operation.calculate(2,1), 1);
        assertEquals(8.65, operation.calculate(4.32,4.33), 0.01);
        assertEquals(-1.2, operation.calculate(-5.5,4.3), 0.1);
    }

    @Test
    public void calculate_SUBTRACTION() throws Exception {
        BinaryOperation operation = BinaryOperation.SUBTRACTION;

        assertEquals(-1, operation.calculate(1,2), 1);
        assertEquals(1, operation.calculate(2,1), 1);
        assertEquals(-0.01, operation.calculate(4.32,4.33), 0.01);
        assertEquals(-9.8, operation.calculate(-5.5,4.3), 0.1);
    }

    @Test
    public void calculate_MULTIPLICATION() throws Exception {
        BinaryOperation operation = BinaryOperation.MULTIPLICATION;

        assertEquals(2, operation.calculate(1,2), 1);
        assertEquals(2, operation.calculate(2,1), 1);
        assertEquals(18.7056, operation.calculate(4.32,4.33), 0.0001);
        assertEquals(-23.65, operation.calculate(-5.5,4.3), 0.01);
    }

    @Test
    public void calculate_DIVISION() throws Exception {
        BinaryOperation operation = BinaryOperation.DIVISION;

        assertEquals(0.5, operation.calculate(1,2), 0.1);
        assertEquals(2, operation.calculate(2,1), 1);
        assertEquals(0.9976, operation.calculate(4.32,4.33), 0.0001);
        assertEquals(-1.2791, operation.calculate(-5.5,4.3), 0.0001);
    }

}