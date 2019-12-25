package com.example.whatsappagain2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Scroller;
import android.widget.TextView;


import java.util.ArrayList;

import static com.example.whatsappagain2.ChatActivity.currentColor;

public class CustomAdapterForChat extends BaseAdapter {
    ArrayList<String> arrayList;
    Context context;
    TextView textView;

    public CustomAdapterForChat(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "ResourceAsColor"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.simple_list_item_1, parent, false);
        }
        textView = convertView.findViewById(R.id.chatTextView);
        textView.setTextColor(currentColor);
        textView.setText(arrayList.get(position));
        return convertView;
    }
}
