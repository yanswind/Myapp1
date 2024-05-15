package com.example.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MyRateTask implements Runnable{
    private static final String TAG = "MyRateTask";

    private Handler handler;
    public MyRateTask(Handler handler){
        this.handler = handler;
    }
    public void  setHandler(Handler handler){
        this.handler = handler;
    }
    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";

    @Override
    public void run() {
        Log.i(TAG,"run:()......");

        //模拟延时
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }


        ArrayList<RateItem> retlist = new ArrayList<>();
        //获取网络数据
        try{
            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Elements tables = doc.getElementsByTag("table");
            Element table = tables.get(1);
            Elements trs = table.getElementsByTag("tr");
            for(Element tr:trs){
                Elements tds = tr.children();
                String name = tds.get(0).text();
                String rate = tds.get(5).text();
                Log.i(TAG,"run:"+name+"==>"+rate);
                RateItem item = new RateItem(name,rate);
                retlist.add(item);
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

    /*
    @Override
    public void run() {
        Log.i("List","run...");
        List<String> retList = new ArrayList<String>();
        Message msg = handler.obtainMessage();
        String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        Log.i("run","curDateStr:" + curDateStr + " logDate:" + logDate);
        if(curDateStr.equals(logDate)){
            //如果相等，则不从网络中获取数据
            Log.i("run","日期相等，从数据库中获取数据");
            RateManager dbManager = new RateManager(RateListActivity4.this);
            for(RateItem rateItem : dbManager.listAll()){
                retList.add(rateItem.getCname() + "=>" + rateItem.getCval());
            }
        }else{
            Log.i("run","日期相等，从网络中获取在线数据");
            //获取网络数据
            ArrayList<RateItem> ratelist = new ArrayList<>();
            try {
                Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
                Elements tables = doc.getElementsByTag("table");
                Element table = tables.get(1);
                Elements trs = table.getElementsByTag("tr");
                for(Element tr:trs){
                    Elements tds = tr.children();
                    String name = tds.get(0).text();
                    String rate = tds.get(5).text();
                    Log.i(TAG,"run:"+name+"==>"+rate);
                    RateItem item = new RateItem(name,rate);
                    ratelist.add(item);
                }
                RateManager dbManager = new RateManager(RateListActivity4.this);
                dbManager.deleteAll();
                Log.i("db","删除所有记录");
                dbManager.addAll(ratelist);
                Log.i("db","添加新记录集");

            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            //更新记录日期
            SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(DATE_SP_KEY, curDateStr);
            edit.commit();
            Log.i("run","更新日期结束：" + curDateStr);
        }

        msg.obj = retList;
        msg.what = 7;
        handler.sendMessage(msg);
    }*/
}
