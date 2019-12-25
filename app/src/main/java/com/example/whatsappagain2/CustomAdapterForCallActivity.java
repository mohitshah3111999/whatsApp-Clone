package com.example.whatsappagain2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import java.util.ArrayList;

public class CustomAdapterForCallActivity extends BaseAdapter {

    Context context;
    ArrayList<ItemHolder> list;

    CustomAdapterForCallActivity(Context context, ArrayList<ItemHolder> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
//      Here we have to do the work.
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.callslog, parent, false);
        ImageView dp = convertView.findViewById(R.id.UserDpCallLog);
        TextView textView = convertView.findViewById(R.id.UserNameCallLog);

        Resources resources = convertView.getResources();
        Resources res = resources;
        Bitmap src = BitmapFactory.decodeResource(res, list.get(position).personDp);
        RoundedBitmapDrawable dr =
                RoundedBitmapDrawableFactory.create(res, src);
        dr.setCircular(true);
        dp.setImageDrawable(dr);
        textView.setText(list.get(position).name);
        return convertView;
    }
}
