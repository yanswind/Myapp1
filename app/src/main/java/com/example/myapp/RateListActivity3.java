package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateListActivity3 extends ListActivity {

    private static final String TAG = "RateListActivity3";
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /*ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0;i< 30;i++){
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate:"+ i); //标题文字
            map.put("ItemDetail", "detail" + i); //详情描述
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItems, // listItems 数据源
                R.layout.list_item, // ListItem 的XML 布局实现
                new String[] { "ItemTitle", "ItemDetail"},
        new int[] {R.id.itemTitle, R.id.itemDetail });

        setListAdapter(listItemAdapter);*/


        handler = new Handler(Looper.myLooper()){
            public void handleMessage(Message msg){
                if(msg.what==7){
                    Log.i(TAG,"handlerMessage:what="+msg.what);
                    ArrayList<HashMap<String,String>> listItems = (ArrayList<HashMap<String, String>>) msg.obj;

                    /*SimpleAdapter listItemAdapter = new SimpleAdapter(RateListActivity3.this, listItems, // listItems 数据源
                            R.layout.list_item, // ListItem 的XML 布局实现
                            new String[] { "ItemTitle", "ItemDetail"},
                            new int[] {R.id.itemTitle, R.id.itemDetail });
                    setListAdapter(listItemAdapter);*/

                    MyAdapter myAdapter = new MyAdapter(RateListActivity3.this, R.layout.list_item,listItems);
                    setListAdapter(myAdapter);
                }
                super.handleMessage(msg);
            }
        };

        //启动线程，获取网络数据
        MyTask3 mytask = new MyTask3();
        mytask.setHandler(handler);
        Thread t3 = new Thread(mytask);
        t3.start();
    }

    public void onItemClick(AdapterView<?> adapterView,View view,int i,long l){
        //点击行后的时间处理
        HashMap<String,String> map = (HashMap<String, String>) getListView().getItemAtPosition(i);
        String title = map.get("ItemTitle");
        String rate = map.get("ItemDetail");
        Log.i(TAG,"onItemClick: name="+title);
        Log.i(TAG,"onItemClick: rate="+rate);
    }

}