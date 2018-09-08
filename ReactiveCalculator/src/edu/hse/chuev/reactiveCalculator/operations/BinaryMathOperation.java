package edu.hse.chuev.reactiveCalculator.operations;

/**
 * An interface which describe a binary math operation behavior
 */
public interface BinaryMathOperation {
    /**
     * Calculates and returns the result of operation
     * @param a first parameter
     * @param b second parameter
     * @return  the result of operation
     */
    double calculate(double a, double b);
}
