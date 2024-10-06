package com.denniseckerskorn.calculadora.models;

public class Calculator {
    private State currentState;
    private StringBuilder firstOperand;
    private StringBuilder secondOperand;
    private Operator operator;
    private boolean firstDotUsed;
    private boolean secondDotUsed;

    public Calculator() {
        this.currentState = State.INITIAL;
        this.firstOperand = new StringBuilder();
        this.secondOperand = new StringBuilder();
        this.firstDotUsed = false;
        this.secondDotUsed = false;
    }

    public void inputNumber(String value) {
        if (!value.matches("\\d")) {
            throw new IllegalArgumentException("Invalid input. Only digits are allowed");
        }
        switch (currentState) {
            case INITIAL:
                firstOperand.append(value);
                currentState = State.OPERAND_ENTERED;
                break;
            case OPERAND_ENTERED:
                secondOperand.append(value);
                currentState = State.OPERAND_SELECTED; //Prepare for operator selection
                break;
            case OPERAND_SELECTED:
                secondOperand.append(value); // Update second operand
                currentState = State.RESULT_CALCULATED; //Prepare for calculation
                break;
            case RESULT_CALCULATED: //Reset for new calculation
                firstOperand = new StringBuilder(value);
                secondOperand.setLength(0);
                operator = null;
                currentState = State.OPERAND_ENTERED;
                break;
        }
    }

    public void inputDot() {
        switch (currentState) {
            case INITIAL:
                break;
            case OPERAND_ENTERED:
                if (!firstDotUsed) {
                    firstOperand.append(".");
                    firstDotUsed = true;
                }
                break;
            case OPERAND_SELECTED:
                if (!secondDotUsed) {
                    secondOperand.append(".");
                    secondDotUsed = true;
                }
                break;
            case RESULT_CALCULATED:
                break;
        }
    }

    public void inputOperator(Operator operator) {
        if (operator == null) {
            throw new IllegalArgumentException("Operator cannot be null");
        }
        switch (currentState) {
            case OPERAND_ENTERED:
                this.operator = operator;
                currentState = State.OPERAND_SELECTED; //Wait for second operand
                break;
            case OPERAND_SELECTED:
                //Invalid operation, should calculate first
                break;
            case RESULT_CALCULATED:
                this.operator = operator;
                currentState = State.OPERAND_ENTERED; //Start new calculation
                break;
            case INITIAL:
                //Cannot input operator in initial state
                break;
        }
    }

    public double calculate() {
        if (currentState != State.OPERAND_SELECTED) {
            throw new IllegalStateException("Invalid state for calculation");
        }
        double firstNumber = Double.parseDouble(firstOperand.toString());
        double secondNumber = Double.parseDouble(secondOperand.toString());
        double result;

        switch (operator) {
            case ADD:
                result = firstNumber + secondNumber;
                break;
            case SUBTRACT:
                result = firstNumber - secondNumber;
                break;
            case MULTIPLY:
                result = firstNumber * secondNumber;
                break;
            case DIVIDE:
                if (secondNumber == 0) {
                    throw new ArithmeticException("Cannot divide by Zero");
                }
                result = firstNumber / secondNumber;
                break;
            case PERCENT:
                result = firstNumber * (secondNumber / 100);
                break;
            case SIGN_CHANGE:
                result = -firstNumber;
                break;
            default:
                throw new UnsupportedOperationException("Invalid Operator");
        }
        currentState = State.RESULT_CALCULATED;
        return result;
    }

    public void clearEntry() {
        switch (currentState) {
            case OPERAND_ENTERED:
                firstOperand.setLength(0);
                firstDotUsed = false;
                break;
            case OPERAND_SELECTED:
                secondOperand.setLength(0);
                secondDotUsed = false;
                break;
            case RESULT_CALCULATED:
                clearAll();
                break;
            case INITIAL:
                break;
        }
    }

    public void changeSign() {
        switch (currentState) {
            case OPERAND_ENTERED:
                firstOperand.insert(0, "-");
                break;
            case OPERAND_SELECTED:
                secondOperand.insert(0, "-");
                break;
            case RESULT_CALCULATED:
                break;
            case INITIAL:
                break;
        }
    }

    private void clearAll() {
        firstOperand.setLength(0);
        secondOperand.setLength(0);
        operator = null;
        currentState = State.INITIAL;
        firstDotUsed = false;
        secondDotUsed = false;
    }

    public State getCurrentState() {
        return currentState;
    }

    public StringBuilder getFirstOperand() {
        return firstOperand;
    }

    public StringBuilder getSecondOperand() {
        return secondOperand;
    }

    public Operator getOperator() {
        return operator;
    }

    public boolean isFirstDotUsed() {
        return firstDotUsed;
    }

    public boolean isSecondDotUsed() {
        return secondDotUsed;
    }
}
