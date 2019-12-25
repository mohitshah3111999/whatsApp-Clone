package com.example.whatsappagain2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class CustomAdapterForMainScene extends BaseAdapter {
    ArrayList<ItemHolder> list, modelList;
    Context context;
    ImageView contactDp;

    public CustomAdapterForMainScene(Context context, ArrayList<ItemHolder> list){
        this.context = context;
        this.list = list;
        modelList = new ArrayList<>();
        modelList.addAll(list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public int getItemViewType(final int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public long getItemId(final int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.mainscene, viewGroup, false);
        }
        contactDp = view.findViewById(R.id.ContactDp);
        TextView personName = view.findViewById(R.id.PersonName);
        TextView phoneNo = view.findViewById(R.id.contactNumber);
        personName.setText(list.get(i).name);
        phoneNo.setText(list.get(i).phoneNo);

        Resources res = view.getResources();
        Bitmap src = BitmapFactory.decodeResource(res, list.get(i).personDp);
        RoundedBitmapDrawable dr =
                RoundedBitmapDrawableFactory.create(res, src);
        dr.setCircular(true);
        contactDp.setImageDrawable(dr);
        contactDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("PositionIs", String.valueOf(i));
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(list.get(i).personDp);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(imageView);
                builder.setMessage(list.get(i).name);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    public void filter(String charText) {
        list.clear();
        if(charText.length() == 0){
            list.addAll(modelList);
        }else{
            for(ItemHolder item : modelList){
                if(item.name.contains(charText)){
                    list.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

}
