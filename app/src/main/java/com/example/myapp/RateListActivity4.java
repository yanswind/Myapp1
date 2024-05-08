package com.example.myapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RateListActivity4 extends AppCompatActivity implements AdapterView.OnItemLongClickListener{
    private static final String TAG = "RateListActivity4";
    private Handler handler;
    ListView mylist4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list4);
        ListView mylist4 = findViewById(R.id.mylist4);
        ProgressBar bar = findViewById(R.id.progressBar);
        TextView nodata = findViewById(R.id.nodata);
        ArrayList<HashMap<String,String>> listItems = new ArrayList<>();
        for(int i=0;i<30;i++){
            HashMap<String,String> map = new HashMap<>();
            map.put("ItemTitle","Rate:"+i);//标题文字
            map.put("ItemDetail","detail"+i);//详情描述
            listItems.add(map);
        }

        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item,listItems);
        mylist4.setAdapter(myAdapter);
        mylist4.setEmptyView(nodata);

        handler = new Handler(Looper.myLooper()){
            public void handleMessage(Message msg){
                if(msg.what==7){
                    ArrayList<RateItem> list4 = (ArrayList<RateItem>) msg.obj;
                    RateAdapter adapter = new RateAdapter(RateListActivity4.this, list4);
                    mylist4.setAdapter(adapter);

                    //hide bar
                    bar.setVisibility(View.GONE);
                    nodata.setText("No Data");
                }
                super.handleMessage(msg);
            }
        };
/*
        //启动子线程获取数据
        MyRateTask mytask = new MyRateTask(handler);
        //mytask.setHandler(handler);
        Thread t3 = new Thread(mytask);
        t3.start();
*/
        mylist4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG,"onitemClick:单击"+i);
                AlertDialog.Builder builder = new AlertDialog.Builder(RateListActivity4.this);
                builder.setTitle("提示")
                        .setMessage("请确认是否剥除当前数据")
                                .setPositiveButton("是",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i(TAG, "onClick：对话框事件处理");
                                        myAdapter.remove(mylist4.getItemAtPosition(i));
                                    }
                                }).setNegativeButton("否",null);
                builder.create().show();
            }
        });
        mylist4.setOnItemLongClickListener(this);
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l){
        HashMap<String,String> rowdata = (HashMap<String, String>) mylist4.getItemAtPosition(i);
        Log.i(TAG,"onItemLongClick:"+rowdata.get("ItemTitle"));
        return false;
    }
}