package com.example.myapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView out;
    EditText height;
    EditText weight;
    TextView result;
    String Tag = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_layout);

        out = findViewById(R.id.out);
        height = findViewById(R.id.input1);
        weight = findViewById(R.id.input2);
        Button btn = findViewById(R.id.button);

        out.setText("BMI计算器");
        System.out.println("this is out");

        Log.i(TAG,"onCreate");
    }

    public void click(View v) {
        Log.i(TAG, "click:......");
        //获得用户输入
        result = findViewById(R.id.result);
        height = findViewById(R.id.input1);
        weight = findViewById(R.id.input2);
        String inputStr1 = height.getText().toString();
        String inputStr2 = weight.getText().toString();

        try {
            float h = Float.parseFloat(inputStr1);
            float w = Float.parseFloat(inputStr2);
            double b = w/(h*h);
            String bmi = String.format("%.2f",b);
            if(b<18.5){
                result.setText("BMI值为"+bmi+",体重过低");
            }
            else if(b<24){
                result.setText("BMI值为"+bmi+",正常");
            }
            else if(b<28){
                result.setText("BMI值为"+bmi+",超重");
            }
            else{
                result.setText("BMI值为"+bmi+",肥胖");
            }
        }catch (NumberFormatException e){
            result.setText("请输入正常数据再计算");
        }


        Log.i(TAG, "click: 用户输入身高为："+inputStr1+"，体重为"+inputStr2);
    }
}
