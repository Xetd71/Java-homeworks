package edu.hse.chuev.reactiveCalculator.operations;

/**
 * The list of binary operations
 */
public enum BinaryOperation implements BinaryMathOperation {
    ADDITION('+', (a, b) -> a + b),
    SUBTRACTION('-', (a, b) -> a - b),
    MULTIPLICATION('*', (a, b) -> a * b),
    DIVISION(':', (a, b) -> a / b);

    /**
     * symbol of the current binary operation
     */
    private final char _operationSymbol;
    /**
     * math operation of the current binary operation
     */
    private final BinaryMathOperation _mathOperation;

    /**
     * Initialize the fields of binary operation
     * @param operationSymbol   symbol of the binary operation
     * @param mathOperation     math operation of the binary operation
     */
    private BinaryOperation(char operationSymbol, BinaryMathOperation mathOperation) {
        _operationSymbol = operationSymbol;
        _mathOperation = mathOperation;
    }

    /**
     * Return current operation symbol
     * @return  char value which is the symbol of the operation
     */
    public char getOperationSymbol() {
        return _operationSymbol;
    }

    /**
     * Return binary math operation of the current operation
     * @return  object which implements BinaryMathOperation
     */
    public BinaryMathOperation getMathOperation(){
        return this._mathOperation;
    }

    /**
     * Calculates and returns the result of the current operation
     * @param a first parameter
     * @param b second parameter
     * @return  returns the result of the current operation
     */
    @Override
    public double calculate(double a, double b){
        return this._mathOperation.calculate(a, b);
    }
}