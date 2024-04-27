package com.example.myapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends ListActivity {

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        //数据
        String[] datas = {"one","two","three","android","java"};
        List<String> list1 = new ArrayList<String>();
        for(int i=1;i<30;i++){
            list1.add("Item" + i);
        }
        //构造适配器
        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list1);

        setListAdapter(adapter);*/

        /*handler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==7){
                    List<String> list2= (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this, android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };


        //启动线程
        MyTask task1 = new MyTask();
        task1.setHandler(handler);
        Thread thread = new Thread(task1);
        thread.start();*/


        handler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==9){
                    List<String> list3= (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this, android.R.layout.simple_list_item_1,list3);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

        //启动线程
        MyTask2 task2 = new MyTask2();
        task2.setHandler(handler);
        Thread t2 = new Thread(task2);
        t2.start();
    }
}