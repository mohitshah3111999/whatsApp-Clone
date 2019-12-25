package com.example.whatsappagain2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterForCountryCode extends BaseAdapter {

    ArrayList<CountryHolder> countries;
    Context context;

    CustomAdapterForCountryCode(Context context, ArrayList<CountryHolder> countries){
        this.countries = countries;
        this.context = context;
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.dropdown_mainscene, parent, false);
        TextView textView = convertView.findViewById(R.id.MainScenenOfDropDown);
        textView.setText("+" + countries.get(position).countryCode);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.country_codes, parent, false);
        ImageView imageView = convertView.findViewById(R.id.CountryFlag);
        TextView textView1 = convertView.findViewById(R.id.CountryName);
        imageView.setImageResource(countries.get(position).picId);
        textView1.setText(countries.get(position).countryName);
        return convertView;
    }
}
