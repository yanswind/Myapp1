package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    private int score1=0;

    private static final String TAG = "ScoreActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //接收数据
        Intent intent = getIntent();
        String name2 = intent.getStringExtra("name1");
        int age2 = intent.getIntExtra("age1",11);
        float rate2 = intent.getFloatExtra("rate1",12.3f);

        Log.i(TAG,"onCreate:name2="+name2);
        Log.i(TAG,"onCreate:age2="+age2);
        Log.i(TAG,"onCreate:rate2="+rate2);
    }

    public void click(View btn){
        if(btn.getId()==R.id.btn3){
            updateScore(3);
        }else if(btn.getId()==R.id.btn2){
            updateScore(2);
        }else if(btn.getId()==R.id.btn1){
            updateScore(1);
        }
    }
    private void updateScore(int s){
        //更新计分
        TextView scorea = findViewById(R.id.score1);
        //int score_old = Integer.parseInt(scorea.getText().toString());
        //scorea.setText(String.valueOf(score_old+s));

        score1 += s;
        scorea.setText(String.valueOf(score1));
    }
    public void reset(View v){
        TextView scorea = findViewById(R.id.score1);
        score1 = 0;
        scorea.setText(String.valueOf(score1));
    }
}