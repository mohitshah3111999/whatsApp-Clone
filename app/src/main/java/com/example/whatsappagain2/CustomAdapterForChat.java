package com.example.whatsappagain2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

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
        int resourceLayout;
        String message = "";
        if(arrayList.get(position).contains("->")){
            resourceLayout = R.layout.left_chat;
            message = arrayList.get(position).substring(2);
        }else{
            resourceLayout = R.layout.right_chat;
            message = arrayList.get(position);
        }
        convertView = LayoutInflater.from(context).inflate(resourceLayout, parent, false);
        if(resourceLayout == R.layout.right_chat) {
            textView = convertView.findViewById(R.id.right_chat_textview);
        }else{
            textView = convertView.findViewById(R.id.left_chat_textview);
        }
        textView.setText(message);
        return convertView;
    }
}
