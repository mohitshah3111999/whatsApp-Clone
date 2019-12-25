package com.example.whatsappagain2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment_Status extends Fragment {

    Context context;
    ImageView dp;

    public Fragment_Status(Context context) {
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, null);
        ListView listView = view.findViewById(R.id.StatusListView);

        dp = view.findViewById(R.id.UserDp);
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.manchurian);
        RoundedBitmapDrawable dr =
                RoundedBitmapDrawableFactory.create(getResources(), src);
        dr.setCircular(true);
        dp.setImageDrawable(dr);

        LinearLayout linearLayout = view.findViewById(R.id.InfoAboutStatus);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<ItemHolder> arrayList = new ArrayList<>();

        CustomAdapterForMainScene customAdapterForMainScene = new CustomAdapterForMainScene(context, arrayList);
        listView.setAdapter(customAdapterForMainScene);

        return view;
    }
}
