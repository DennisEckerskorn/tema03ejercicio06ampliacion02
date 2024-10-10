package com.denniseckerskorn.calculadora.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denniseckerskorn.calculadora.R;
import com.denniseckerskorn.calculadora.models.Calculator;
import com.denniseckerskorn.calculadora.models.Operator;
import com.denniseckerskorn.calculadora.models.State;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denniseckerskorn.calculadora.R;
import com.denniseckerskorn.calculadora.models.Calculator;
import com.denniseckerskorn.calculadora.models.Operator;
import com.denniseckerskorn.calculadora.models.State;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private Calculator calculator;

    private final int[] buttonIDs = {
            R.id.bCE, R.id.bNegativeSign, R.id.bPercent, R.id.bDivide,
            R.id.bSeven, R.id.bEigth, R.id.bNine, R.id.bMultiply,
            R.id.bFour, R.id.bFive, R.id.bSix, R.id.bMinus,
            R.id.bOne, R.id.bTwo, R.id.bThree, R.id.bPlus,
            R.id.bZero, R.id.bDot, R.id.bEquals
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = new Calculator();
        display = findViewById(R.id.display);

        for (int id : buttonIDs) {
            Button buttons = findViewById(id);
            buttons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button clickedButton = (Button) view;
                    String buttonText = clickedButton.getText().toString();
                    handleButtonClick(buttonText);

                }
            });
        }

    }

    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "CE":
                calculator.clearEntry();
                updateDisplay("0");
                break;
            case "±":
                calculator.changeSign();
                updateDisplay(calculator.getCurrentOperand());
                break;
            case "%":
                calculator.inputOperator(Operator.PERCENT);
                break;
            case "/":
                calculator.inputOperator(Operator.DIVIDE);
                break;
            case "*":
                calculator.inputOperator(Operator.MULTIPLY);
                break;
            case "-":
                calculator.inputOperator(Operator.SUBTRACT);
                break;
            case "+":
                calculator.inputOperator(Operator.ADD);
                break;
            case "=":
                if (calculator.getCurrentState() == State.OPERAND_SELECTED || calculator.getCurrentState() == State.RESULT_CALCULATED) {
                    double result = calculator.calculate();
                    updateDisplay(String.valueOf(result));
                }
                break;
            case ".":
                calculator.inputDot();
                updateDisplay(calculator.getCurrentOperand());
                break;
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                calculator.inputNumber(buttonText);
                updateDisplay(calculator.getCurrentOperand());
                break;
            default:
                break;
        }
    }

    private void updateDisplay(String text) {
        display.setText(text);
    }
}