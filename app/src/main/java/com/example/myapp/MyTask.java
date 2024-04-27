package com.example.myapp;

import android.os.Bundle;
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


public class MyTask implements Runnable{
    private static final String TAG = "MyTask";

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

        Bundle bundle = new Bundle();
        List<String> retlist = new ArrayList<String>();
        //获取网络数据
        try{
            /*
            URL url = null;
            url = new URL("https://www.boc.cn/sourcedb/whpj/");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();

            String html = inputStream2String(in);
            Log.i(TAG,"run:html="+html);*/

            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Elements tables = doc.getElementsByTag("table");
            Element table = tables.get(1);
            Elements trs = table.getElementsByTag("tr");
            for(Element tr:trs){
                Elements tds = tr.children();
                String name = tds.get(0).text();
                String rate = tds.get(5).text();
                Log.i(TAG,"run:"+name+"==>"+rate);
                retlist.add(name+"==>"+rate);

                if("美元".equals(name)){
                    bundle.putFloat("web_dollar",100f/Float.parseFloat(rate));
                }else if("欧元".equals(name)){
                    bundle.putFloat("web_euro",100f/Float.parseFloat(rate));
                }else if("韩国元".equals(name)) {
                    bundle.putFloat("web_won",100f/Float.parseFloat(rate));
                }

            }
            Element dollar = doc.select("body > div > div.BOC_main > div.publish > div:nth-child(3) > table > tbody > tr:nth-child(27) > td:nth-child(6)").first();
            Log.i(TAG,"run:直接获取美元汇率数据："+dollar);
            Element euro = doc.select("body > div > div.BOC_main > div.publish > div:nth-child(3) > table > tbody > tr:nth-child(8) > td:nth-child(6)").first();
            Log.i(TAG,"run:直接获取欧元汇率数据："+euro);
            Element won = doc.select("body > div > div.BOC_main > div.publish > div:nth-child(3) > table > tbody > tr:nth-child(14) > td:nth-child(6)").first();
            Log.i(TAG,"run:直接获取韩元汇率数据："+won);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        //发送消息Message,用于返回主线程
        Message msg = handler.obtainMessage(8,bundle);
        //msg.what = 8;//标记
        //msg.obj = "return msg";//返回对象
        handler.sendMessage(msg);

        Message msg2 = handler.obtainMessage(7,retlist);
        handler.sendMessage(msg2);
    }
}
