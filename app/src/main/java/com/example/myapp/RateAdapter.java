package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RateAdapter extends ArrayAdapter {
    private static final String TAG = "RateAdapter";
    //private int resource = 0;

    public RateAdapter(Context context, ArrayList<RateItem> list) {
        super(context, 0, list);
    }

    /*public RateAdapter(Context context, int resource, ArrayList<RateItem> list) {
        super(context, resource, list);
        this.resource = resource;
    }*/

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        RateItem item = (RateItem) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail);

        title.setText(item.getCname());
        detail.setText("汇率="+item.getCval());

        return itemView;
    }
}






