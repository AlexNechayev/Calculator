package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private double firstNumber = 0;
    private double secondNumber = 0;
    private double result = 0;

    private boolean isFirstNumberEntered = false;
    private boolean isSecondNumberEntered = false;
    private boolean isArithmeticSignEntered = false;
    private boolean isEqualSignEntered = false;
    private boolean isFloatingPointEntered = false;
    private boolean isFloatingPointLastCharacter = false;

    private String numberBlock = "";
    private String equationBlock = "";
    private String arithmeticSign = "";

    private TextView screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.resultScreen);

        Button btn0 = findViewById(R.id.btn_0);
        Button btn1 = findViewById(R.id.btn_1);
        Button btn2 = findViewById(R.id.btn_2);
        Button btn3 = findViewById(R.id.btn_3);
        Button btn4 = findViewById(R.id.btn_4);
        Button btn5 = findViewById(R.id.btn_5);
        Button btn6 = findViewById(R.id.btn_6);
        Button btn7 = findViewById(R.id.btn_7);
        Button btn8 = findViewById(R.id.btn_8);
        Button btn9 = findViewById(R.id.btn_9);
        Button btnPoint = findViewById(R.id.btn_point);

        Button btnAdd = findViewById(R.id.btn_add);
        Button btnSub = findViewById(R.id.btn_sub);
        Button btnMul = findViewById(R.id.btn_mul);
        Button btnDiv = findViewById(R.id.btn_div);

        Button btnChangeSign = findViewById(R.id.btn_changeSign);

        Button btnEql = findViewById(R.id.btn_equal);
        Button btnReset = findViewById(R.id.btn_clear);

        btn0.setOnClickListener(new NumberButtonListener());
        btn1.setOnClickListener(new NumberButtonListener());
        btn2.setOnClickListener(new NumberButtonListener());
        btn3.setOnClickListener(new NumberButtonListener());
        btn4.setOnClickListener(new NumberButtonListener());
        btn5.setOnClickListener(new NumberButtonListener());
        btn6.setOnClickListener(new NumberButtonListener());
        btn7.setOnClickListener(new NumberButtonListener());
        btn8.setOnClickListener(new NumberButtonListener());
        btn9.setOnClickListener(new NumberButtonListener());

        btnPoint.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                numberBlock += view.getTag().toString();
                equationBlock += view.getTag().toString();
                screen.setText(equationBlock);
                isFloatingPointEntered = true;
                isFloatingPointLastCharacter = true;
            }
        });

        btnAdd.setOnClickListener(new ArithmeticButtonListener());
        btnSub.setOnClickListener(new ArithmeticButtonListener());
        btnMul.setOnClickListener(new ArithmeticButtonListener());
        btnDiv.setOnClickListener(new ArithmeticButtonListener());

        btnChangeSign.setOnClickListener(new ArithmeticButtonListener());


        btnEql.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!isFloatingPointLastCharacter && isSecondNumberEntered){

                    secondNumber = isFloatingPointEntered ? Double.parseDouble(numberBlock):Integer.parseInt(numberBlock);

                    boolean needFloatingPoint = isFloatingPointEntered;

                    if(arithmeticSign.equals("add")){
                        result = firstNumber + secondNumber;
                    }else if(arithmeticSign.equals("sub")){
                        result = firstNumber - secondNumber;
                    }else if(arithmeticSign.equals("mul")){
                        result = firstNumber * secondNumber;
                    }else if(arithmeticSign.equals("div")){
                        if((firstNumber / secondNumber)%secondNumber != 0) needFloatingPoint = true;
                        result = firstNumber / secondNumber;
                    }

                    isEqualSignEntered = true;
                    firstNumber = result;
                    equationBlock = needFloatingPoint?String.valueOf(result):String.valueOf((int)result);
                    screen.setText(equationBlock);
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                screen.setText(R.string.initCalculator);
                firstNumber = 0;
                secondNumber = 0;
                isFirstNumberEntered = false;
                isSecondNumberEntered = false;
                isArithmeticSignEntered = false;
                isEqualSignEntered = false;
                isFloatingPointEntered = false;
                isFloatingPointLastCharacter = false;
                numberBlock = "";
                equationBlock = "";
                arithmeticSign = "";
                result = 0;
            }
        });



    }


    class ArithmeticButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {

            if (isFirstNumberEntered && (!isArithmeticSignEntered || isEqualSignEntered)) {
                if (!isFloatingPointLastCharacter) {

                    if(isEqualSignEntered){
                        numberBlock = equationBlock;
                    }

                    firstNumber = isFloatingPointEntered ? Double.parseDouble(numberBlock):Integer.parseInt(numberBlock);
                    numberBlock = "";

                    int arithmeticID = view.getId();

                    if (arithmeticID == R.id.btn_add) {
                        arithmeticSign = "add";
                        equationBlock += " + ";
                    } else if (arithmeticID == R.id.btn_sub) {
                        arithmeticSign = "sub";
                        equationBlock += " - ";
                    } else if (arithmeticID == R.id.btn_mul) {
                        arithmeticSign = "mul";
                        equationBlock += " x ";
                    } else if (arithmeticID == R.id.btn_div) {
                        arithmeticSign = "div";
                        equationBlock += " / ";
                    }
                    screen.setText(equationBlock);
                    isArithmeticSignEntered = true;
                }
            }

        }
    }

    class NumberButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            numberBlock += view.getTag().toString();
            equationBlock += view.getTag().toString();
            screen.setText(equationBlock);
            isFirstNumberEntered = true;
            isSecondNumberEntered = isArithmeticSignEntered;
            isFloatingPointLastCharacter = false;
        }
    }
}