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
import java.util.List;

public class MyTask2 implements Runnable{
    private static final String TAG = "MyTask2";

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

        List<String> retlist = new ArrayList<String>();
        //获取网络数据
        try{
            Document doc = Jsoup.connect("https://www.huilvzaixian.com/").get();
            Elements divs = doc.getElementsByTag("div");
            Element div = divs.get(18);
            Elements uls = div.getElementsByTag("ul");
            Element ul = uls.get(0);
            Elements lis = ul.getElementsByTag("li");
            Element li = lis.get(0);
            Elements as = li.getElementsByTag("a");
            for(Element a:as) {
                String text = a.text();
                Log.i(TAG, "run:" + text);
                retlist.add(text);
            }
            /*Document doc = Jsoup.connect("https://www.huilvzaixian.com/").get();
            Element table = doc.select("table").first();
            //Element table = tables.get(0);
            Elements trs = table.select("tr");
            for(int i = 115; i < 238; i++){
                Element tr = trs.get(i);
                String text = tr.select("td").get(1).text();
                Log.i(TAG,"run:"+text);
                retlist.add(text);

            }*/

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        //发送消息Message,用于返回主线程
        Message msg = handler.obtainMessage(9,retlist);
        handler.sendMessage(msg);
    }
}

