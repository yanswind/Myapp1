package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RateListActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list2);

        //数据
        String[] datas = {"one","two","three","android","java"};
        List<String> list1 = new ArrayList<String>();
        for(int i=1;i<30;i++){
            list1.add("Item" + i);
        }
        //构造适配器
        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list1);


        //获取控件
        ListView mylistv = findViewById(R.id.mylist);
        mylistv.setAdapter(adapter);
    }
}