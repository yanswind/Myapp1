package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity2 extends AppCompatActivity {

    private int score1;
    private int score2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score2);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("sc1",score1);
        outState.putInt("sc2",score2);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //取已保存的数据
        score1 = savedInstanceState.getInt("sc1",0);
        score2 = savedInstanceState.getInt("sc2",0);

        //显示在界面中
        updateScore1(0);
        updateScore2(0);

        super.onRestoreInstanceState(savedInstanceState);

    }

    public void click(View btn){
        if(btn.getId()==R.id.btn3_a){
            updateScore1(3);
        }else if(btn.getId()==R.id.btn2_a){
            updateScore1(2);
        }else if(btn.getId()==R.id.btn1_a){
            updateScore1(1);
        }

        if(btn.getId()==R.id.btn3_b){
            updateScore2(3);
        }else if(btn.getId()==R.id.btn2_b){
            updateScore2(2);
        }else if(btn.getId()==R.id.btn1_b){
            updateScore2(1);
        }

    }
    private void updateScore1(int s1){
        //更新计分1
        TextView scorea = findViewById(R.id.score1);

        score1 += s1;
        scorea.setText(String.valueOf(score1));
    }
    private void updateScore2(int s2){
        //更新计分2
        TextView scoreb = findViewById(R.id.score2);

        score2 += s2;
        scoreb.setText(String.valueOf(score2));
    }
    public void reset(View v){
        TextView scorea = findViewById(R.id.score1);
        TextView scoreb = findViewById(R.id.score2);
        score1 = 0;
        score2 = 0;
        scorea.setText(String.valueOf(score1));
        scoreb.setText(String.valueOf(score2));
    }

}