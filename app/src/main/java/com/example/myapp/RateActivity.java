package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RateActivity extends AppCompatActivity implements Runnable{

    float dollarRate = 12.34f;
    float euroRate = 10.56f;
    float wonRate = 0.234f;
    private static final String TAG = "RateActivity";
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        //读取保存的汇率数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollarRate = sharedPreferences.getFloat("dollar_rate", 10.11f);
        euroRate = sharedPreferences.getFloat("euro_rate", 10.22f);
        wonRate = sharedPreferences.getFloat("won_rate", 22.33f);

        Log.i(TAG, "onCreate: load from SharePreferences");

        //启动线程
        Thread t = new Thread(this);
        t.start();//this.run()

        handler = new Handler(Looper.myLooper()){
            public void handlerMessage(Message msg){
                if(msg.what==8){
                    //拆分消息
                    String str = (String) msg.obj;
                    Toast.makeText(RateActivity.this, "Msg="+str, Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
    }

    public void rateClick(View btn) {
        //判断用户是否输入数据
        EditText rmbInput = findViewById(R.id.rmb);
        String rmb = rmbInput.getText().toString();
        if (rmb.length() == 0) {
            //提示消息
            Toast.makeText(this, "请输入正确数据", Toast.LENGTH_LONG).show();
        } else {
            float input = Float.parseFloat(rmb);
            float rmbResult = 0f;
            //判断用户按钮
            if (btn.getId() == R.id.btn_dollar) {
                rmbResult = input * dollarRate;
            } else if (btn.getId() == R.id.btn_euro) {
                rmbResult = input * euroRate;
            } else if (btn.getId() == R.id.btn_won) {
                rmbResult = input * wonRate;
            }
            TextView result = findViewById(R.id.result);
            result.setText(String.valueOf(rmbResult));
        }

        //open Activity
        //Intent intent = new Intent(this, ScoreActivity.class);
        //startActivity(intent);
    }

    public void open(View v) {
        //打开另个页面，并传入参数
        Intent intent = new Intent(this, ConfigActivity.class);

        /*
        intent.putExtra("name1","Tomcat");
        intent.putExtra("age1",21);
        intent.putExtra("rate1",dollarRate);

        Log.i(TAG,"open:name1=Tomcat");
        Log.i(TAG,"open:age1=21");
        Log.i(TAG,"open:rate1="+dollarRate);
        startActivity(intent);*/

        //三个汇率
        intent.putExtra("dollar_key", dollarRate);
        intent.putExtra("euro_key", euroRate);
        intent.putExtra("won_key", wonRate);

        Log.i(TAG, "open:dollarRate=" + dollarRate);
        Log.i(TAG, "open:euroRate=" + euroRate);
        Log.i(TAG, "open:wonRate=" + wonRate);

        startActivityForResult(intent, 3);
    }

    //处理返回的数据
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        if (requestcode == 3 && resultcode == 6) {
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("dollar_key2");
            euroRate = bundle.getFloat("euro_key2");
            wonRate = bundle.getFloat("won_key2");

            Log.i(TAG, "onActivityResult: dollarRate=" + dollarRate);
            Log.i(TAG, "onActivityResult: euroRate=" + euroRate);
            Log.i(TAG, "onActivityResult: wonRate=" + wonRate);
        }
        super.onActivityResult(requestcode, resultcode, data);

        //修改保存的数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("dollar_rate",dollarRate);
        editor.putFloat("euro_rate",euroRate);
        editor.putFloat("won_rate",wonRate);
        editor.apply();

        Log.i(TAG, "onActivityResult: save to SharePreferences");
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.rate,menu);
        return  true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.menu_setting){
            //添加对应功能
        }
        return  super.onOptionsItemSelected(item);
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
            for(org.jsoup.nodes.Element tr:trs){
                Elements tds = tr.children();
                String name = tds.get(0).text();
                String rate = tds.get(5).text();
                Log.i(TAG,"run:"+name+"==>"+rate);

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
        Message msg = handler.obtainMessage();
        msg.what = 8;//标记
        msg.obj = "return msg";//返回对象
        handler.sendMessage(msg);
    }

    /*
    private String inputStream2String(InputStream inputStream) throws IOException{
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }*/
}