package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RateManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public RateManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public void add(RateItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curname", item.getCname());
        values.put("currate", item.getCval());
        db.insert(TBNAME, null, values);
        //db.close();
    }

    public void addAll(List<RateItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (RateItem item : list) {
            ContentValues values = new ContentValues();
            values.put("curname", item.getCname());
            values.put("currate", item.getCval());
            db.insert(TBNAME, null, values);
        }
        //db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        //db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        //db.close();
    }

    public void update(RateItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curname", item.getCname());
        values.put("currate", item.getCval());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        //db.close();
    }

    public List<RateItem> listAll(){
        List<RateItem> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<>();
            while(cursor.moveToNext()){
                RateItem item = new RateItem();
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                item.setCname(cursor.getString(cursor.getColumnIndexOrThrow("CURNAME")));
                item.setCval(cursor.getString(cursor.getColumnIndexOrThrow("CURRATE")));

                rateList.add(item);
            }
            cursor.close();
        }
        //db.close();
        return rateList;

    }

    public RateItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        RateItem rateItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            rateItem = new RateItem();
            rateItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
            rateItem.setCname(cursor.getString(cursor.getColumnIndexOrThrow("CURNAME")));
            rateItem.setCval(cursor.getString(cursor.getColumnIndexOrThrow("CURRATE")));
            cursor.close();
        }
        //db.close();
        return rateItem;
    }
}

