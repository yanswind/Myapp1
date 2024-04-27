package com.example.myapp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MyTask3 implements Runnable{
    private static final String TAG = "MyTask3";

    private Handler handler;
    public void  setHandler(Handler handler){
        this.handler = handler;
    }
    @Override
    public void run() {
        Log.i(TAG,"run:()......");

        //模拟延时
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }


        ArrayList<HashMap<String, String>> retlist = new ArrayList<HashMap<String, String>>();
        //获取网络数据
        try{
            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Elements tables = doc.getElementsByTag("table");
            Element table = tables.get(1);
            Elements trs = table.getElementsByTag("tr");
            trs.remove(0);
            for(Element tr:trs){
                Elements tds = tr.children();
                String name = tds.get(0).text();
                String rate = tds.get(5).text();
                Log.i(TAG,"run:"+name+"==>"+rate);
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("ItemTitle",name);
                map.put("ItemDetail",rate);
                retlist.add(map);
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        //发送消息Message,用于返回主线程
        Message msg = handler.obtainMessage(7,retlist);
        handler.sendMessage(msg);
    }
}
