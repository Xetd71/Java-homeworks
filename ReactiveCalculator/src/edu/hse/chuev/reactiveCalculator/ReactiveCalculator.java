package edu.hse.chuev.reactiveCalculator;

import edu.hse.chuev.reactiveCalculator.operations.BinaryOperation;

/**
 * class that implements the work of reactive calculator
 */
public class ReactiveCalculator {
    /**
     * first variable
     */
    private Double _a;
    /**
     * second variable
     */
    private Double _b;
    /**
     * operation
     */
    private BinaryOperation _currentOperation;

    /**
     * creates instance and initialize all fields by null
     */
    public ReactiveCalculator(){
        _a = null;
        _b = null;
        _currentOperation = null;
    }

    /**
     * Set the first variable '_a' and trying calculate the result
     * of the expression with _currentOperation
     * @param a first variable new value
     */
    public void setA(double a){
        _a = a;
        valueChanged();
    }

    /**
     * Return value of first variable '_a'
     * @return value of variable a
     */
    public double getA(){
        return _a;
    }

    /**
     * Set the second variable '_b' and trying calculate the result
     * of the expression with _currentOperation
     * @param b second variable new value
     */
    public void setB(double b){
        _b = b;
        valueChanged();
    }

    /**
     * Return value of second variable '_b'
     * @return value of variable b
     */
    public double getB(){
        return _b;
    }

    /**
     * Set the variable by given value and trying calculate the result
     * of the expression with _currentOperation
     * @param variableName  variable name, can be only 'a' or 'b'
     * @param value         new value for variable
     * @throws IllegalArgumentException throw exception if variable with name: variableName not exist
     */
    public void setVariable(char variableName, double value) throws IllegalArgumentException {
        switch(variableName){
            case 'a':
                setA(value);
                break;
            case 'b':
                setB(value);
                break;
            default:
                throw new IllegalArgumentException("The existing variables only: \'a\' or \'b\'");
        }
    }

    /**
     * Register new operation
     * @param operation new operation
     * @throws NullPointerException throw exception if new operation is null
     */
    public  void registerOperation(BinaryOperation operation)throws NullPointerException {
        if(operation == null)
            throw new NullPointerException("Operation can not be null");

        _currentOperation = operation;
        valueChanged();
    }

    /**
     * Register new operation
     * @param symbol operation symbol
     * @throws IllegalArgumentException throw exception if operation with current symbol not exist
     */
    public  void registerOperation(char symbol) throws IllegalArgumentException {
        switch(symbol){
            case '+':
                _currentOperation = BinaryOperation.ADDITION;
                break;
            case '-':
                _currentOperation = BinaryOperation.SUBTRACTION;
                break;
            case '*':
                _currentOperation = BinaryOperation.MULTIPLICATION;
                break;
            case ':':
                _currentOperation = BinaryOperation.DIVISION;
                break;
            default:
                throw new IllegalArgumentException(String.format("Can't recognise operation with symbol: \'%c\'", symbol));
        }
        valueChanged();
    }

    /**
     * Return current operation
     * @return current operation
     */
    public BinaryOperation getCurrentOperation(){
        return _currentOperation;
    }

    /**
     * trying execute operation and print result in console
     */
    private void valueChanged(){
        if(_a != null && _b != null && _currentOperation != null)
            System.out.printf("a %c b = %.2f%n", _currentOperation.getOperationSymbol(), _currentOperation.calculate(_a, _b));
    }
}
