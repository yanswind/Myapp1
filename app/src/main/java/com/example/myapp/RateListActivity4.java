package com.example.myapp;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class RateListActivity4 extends AppCompatActivity implements AdapterView.OnItemLongClickListener{
    private static final String TAG = "RateListActivity4";
    private Handler handler;
    ListView mylist4;
    RateAdapter adapter;
    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";
    private ResourceBundle sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list4);

        //SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
        //logDate = sp.getString(DATE_SP_KEY, "");
        //Log.i("List","lastRateDateStr=" + logDate);

        ListView mylist4 = findViewById(R.id.mylist4);
        ProgressBar bar = findViewById(R.id.progressBar);
        TextView nodata = findViewById(R.id.nodata);
        mylist4.setEmptyView(nodata);

        //每天更新一次汇率
        /*String updateDate = sharedPreferences.getString("update_date");

        //获取当前系统时间
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr = sdf.format(today);

        Log.i(TAG, "onCreate: sp updateDate=" + updateDate);
        Log.i(TAG, "onCreate: todayStr=" + todayStr);

        //判断时间
        if(!todayStr.equals(updateDate)){
            Log.i(TAG, "onCreate: 需要更新");
            //开启子线程
            Thread t = new Thread(String.valueOf(this));
            t.start();
        }else{
            Log.i(TAG, "onCreate: 不需要更新");
        }*/

        handler = new Handler(Looper.myLooper()){
            public void handleMessage(Message msg){
                if(msg.what==7){
                    ArrayList<RateItem> list4 = (ArrayList<RateItem>) msg.obj;
                    RateAdapter adapter = new RateAdapter(RateListActivity4.this, list4);
                    mylist4.setAdapter(adapter);

                    //hide bar
                    bar.setVisibility(View.GONE);
                    nodata.setText("No Data");

                    //向数据库中写入数据
                    RateManager manager = new RateManager(RateListActivity4.this);
                    manager.addAll(list4);
                    //RateItem item = new RateItem("测试币种",333f);
                    //manager.add(item);

                    Toast.makeText(RateListActivity4.this,"数据写入完毕",Toast.LENGTH_SHORT).show();
                    Log.i(TAG,"HandleMessage:数据写入完毕");
                }
                super.handleMessage(msg);

                //保存更新的日期
                //SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                //SharedPreferences.Editor editor = sp.edit();
                //editor.putString("update_date",todayStr);
                //editor.apply();
            }
        };

        //启动子线程获取数据
        MyRateTask mytask = new MyRateTask(handler);
        //mytask.setHandler(handler);
        Thread t3 = new Thread(mytask);
        t3.start();

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
                                        adapter.remove(mylist4.getItemAtPosition(i));
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