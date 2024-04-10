package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ConfigActivity extends AppCompatActivity {

    private static final String TAG = "ConfigActivity";
    private EditText dollarEdit,euroEdit,wonEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        //接受传入的数据
        Intent intent = getIntent();
        float dollar2 = intent.getFloatExtra("dollar_key",1.0f);
        float euro2 = intent.getFloatExtra("euro_key",1.0f);
        float won2 = intent.getFloatExtra("won_key",1.0f);

        Log.i(TAG,"onCreate:get dollar2="+dollar2);
        Log.i(TAG,"onCreate:get euro2="+euro2);
        Log.i(TAG,"onCreate:get won2="+won2);

        //显示在控件中
        dollarEdit = findViewById(R.id.dollar_edit);
        euroEdit = findViewById(R.id.euro_edit);
        wonEdit = findViewById(R.id.won_edit);

        dollarEdit.setText(String.valueOf(dollar2));
        euroEdit.setText(String.valueOf(euro2));
        wonEdit.setText(String.valueOf(won2));
    }

    public void save(View btn){
        //重新获取用户输入后的数据
        float new_dollar = Float.parseFloat(dollarEdit.getText().toString());
        float new_euro = Float.parseFloat(euroEdit.getText().toString());
        float new_won = Float.parseFloat(wonEdit.getText().toString());

        Log.i(TAG,"save:new_dollar=" + new_dollar);
        Log.i(TAG,"save:new_euro=" + new_euro);
        Log.i(TAG,"save:new_won=" + new_won);

        //返回数据到调用页面
        Intent retIntent = getIntent();
        Bundle bdl = new Bundle();
        bdl.putFloat("dollar_key2",new_dollar);
        bdl.putFloat("euro_key2",new_euro);
        bdl.putFloat("won_key2",new_won);
        retIntent.putExtras(bdl);
        setResult(6,retIntent);

        finish();
    }

}