package edu.hse.chuev.reactiveCalculator;

import edu.hse.chuev.reactiveCalculator.operations.BinaryOperation;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ReactiveCalculatorTest {
    private ReactiveCalculator rc;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    @Before
    public void setUp() throws Exception {
        rc = new ReactiveCalculator();
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(null);
    }

    @Test
    public void setA_and_setB() throws Exception {
        rc.registerOperation(BinaryOperation.ADDITION);
        rc.setB(2);
        rc.setA(3);
        rc.setB(3);
        String[] output = out.toString().split("\r\n");
        assertEquals("a + b = 5.00", output[0]);
        assertEquals("a + b = 6.00", output[1]);
    }

    @Test
    public void getA_and_getB() throws Exception {
        rc.setA(3);
        assertEquals(3, rc.getA(), 1);

        rc.setB(2);
        assertEquals(2, rc.getA(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setVariable() throws Exception {
        rc.registerOperation(BinaryOperation.ADDITION);
        rc.setVariable('b', 2);
        rc.setVariable('a', 2);
        rc.setVariable('b', 3);
        String[] output = out.toString().split("\r\n");
        assertEquals("a + b = 4.00", output[0]);
        assertEquals("a + b = 5.00", output[1]);

        rc.setVariable('c', 3);
    }

    @Test(expected = NullPointerException.class)
    public void registerOperation_byBinaryOperation() throws Exception {
        rc.setA(2);
        rc.setB(3);
        rc.registerOperation(BinaryOperation.ADDITION);
        rc.registerOperation(BinaryOperation.SUBTRACTION);
        rc.registerOperation(BinaryOperation.MULTIPLICATION);
        rc.registerOperation(BinaryOperation.DIVISION);

        String[] output = out.toString().split("\r\n");
        assertEquals("a + b = 5.00", output[0]);
        assertEquals("a - b = -1.00", output[1]);
        assertEquals("a * b = 6.00", output[2]);
        assertEquals("a : b = 0.67", output[3]);

        rc.registerOperation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerOperation_bySymbol() throws Exception {
        rc.setA(2);
        rc.setB(3);
        rc.registerOperation('+');
        rc.registerOperation('-');
        rc.registerOperation('*');
        rc.registerOperation(':');

        String[] output = out.toString().split("\r\n");
        assertEquals("a + b = 5.00", output[0]);
        assertEquals("a - b = -1.00", output[1]);
        assertEquals("a * b = 6.00", output[2]);
        assertEquals("a : b = 0.67", output[3]);

        rc.registerOperation('/');
    }

    @Test
    public void getCurrentOperation() throws Exception {
        rc.registerOperation(BinaryOperation.ADDITION);
        assertEquals(BinaryOperation.ADDITION, rc.getCurrentOperation());
    }

}