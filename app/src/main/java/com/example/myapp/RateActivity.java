package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RateActivity extends AppCompatActivity {

    float dollarRate = 12.34f;
    float euroRate = 10.56f;
    float wonRate = 0.234f;
    private static final String TAG = "RateActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
    }

    public void rateClick(View btn){
        //判断用户是否输入数据
        EditText rmbInput = findViewById(R.id.rmb);
        String rmb = rmbInput.getText().toString();
        if(rmb.length()==0){
            //提示消息
            Toast.makeText(this,"请输入正确数据", Toast.LENGTH_LONG).show();
        }else {
            float input = Float.parseFloat(rmb);
            float rmbResult = 0f;
            //判断用户按钮
            if (btn.getId() == R.id.btn_dollar) {
                rmbResult = input * dollarRate;
            } else if (btn.getId() == R.id.btn_euro) {
                rmbResult = input * euroRate;
            } else if (btn.getId() == R.id.btn_won) {
                rmbResult = input * wonRate;
            }
            TextView result = findViewById(R.id.result);
            result.setText(String.valueOf(rmbResult));
        }

        //open Activity
        //Intent intent = new Intent(this, ScoreActivity.class);
        //startActivity(intent);
    }

    public void open(View v){
        //打开计分页面，并传入参数
        Intent intent = new Intent(this,ScoreActivity.class);
        intent.putExtra("name1","Tomcat");
        intent.putExtra("age1",21);
        intent.putExtra("rate1",dollarRate);

        Log.i(TAG,"open:name1=Tomcat");
        Log.i(TAG,"open:age1=21");
        Log.i(TAG,"open:rate1="+dollarRate);
        startActivity(intent);
    }

}