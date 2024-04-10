package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

        //读取保存的汇率数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollarRate = sharedPreferences.getFloat("dollar_rate", 10.11f);
        euroRate = sharedPreferences.getFloat("euro_rate", 10.22f);
        wonRate = sharedPreferences.getFloat("won_rate", 22.33f);

        Log.i(TAG, "onCreate: load from SharePreferences");
    }

    public void rateClick(View btn) {
        //判断用户是否输入数据
        EditText rmbInput = findViewById(R.id.rmb);
        String rmb = rmbInput.getText().toString();
        if (rmb.length() == 0) {
            //提示消息
            Toast.makeText(this, "请输入正确数据", Toast.LENGTH_LONG).show();
        } else {
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

    public void open(View v) {
        //打开另个页面，并传入参数
        Intent intent = new Intent(this, ConfigActivity.class);

        /*
        intent.putExtra("name1","Tomcat");
        intent.putExtra("age1",21);
        intent.putExtra("rate1",dollarRate);

        Log.i(TAG,"open:name1=Tomcat");
        Log.i(TAG,"open:age1=21");
        Log.i(TAG,"open:rate1="+dollarRate);
        startActivity(intent);*/

        //三个汇率
        intent.putExtra("dollar_key", dollarRate);
        intent.putExtra("euro_key", euroRate);
        intent.putExtra("won_key", wonRate);

        Log.i(TAG, "open:dollarRate=" + dollarRate);
        Log.i(TAG, "open:euroRate=" + euroRate);
        Log.i(TAG, "open:wonRate=" + wonRate);

        startActivityForResult(intent, 3);
    }

    //处理返回的数据
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        if (requestcode == 3 && resultcode == 6) {
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("dollar_key2");
            euroRate = bundle.getFloat("euro_key2");
            wonRate = bundle.getFloat("won_key2");

            Log.i(TAG, "onActivityResult: dollarRate=" + dollarRate);
            Log.i(TAG, "onActivityResult: euroRate=" + euroRate);
            Log.i(TAG, "onActivityResult: wonRate=" + wonRate);
        }
        super.onActivityResult(requestcode, resultcode, data);

        //修改保存的数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("dollar_rate",dollarRate);
        editor.putFloat("euro_rate",euroRate);
        editor.putFloat("won_rate",wonRate);
        editor.apply();

        Log.i(TAG, "onActivityResult: save to SharePreferences");
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.rate,menu);
        return  true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.menu_setting){
            //添加对应功能
        }
        return  super.onOptionsItemSelected(item);
    }

}