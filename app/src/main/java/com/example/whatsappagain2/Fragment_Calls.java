package com.example.whatsappagain2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment_Calls extends Fragment {

    Context context;

    public Fragment_Calls(Context context) {
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calls, null);
        ListView listView = view.findViewById(R.id.CallsListView);
        ArrayList<ItemHolder> arrayList = new ArrayList<>();
//        arrayList.add(new ItemHolder(R.drawable.manchurian, "Mohit"));
//        arrayList.add(new ItemHolder(R.drawable.manchurian, "Mahesh"));
//        arrayList.add(new ItemHolder(R.drawable.manchurian, "Mohan"));

        CustomAdapterForCallActivity customAdapterForCallActivity = new CustomAdapterForCallActivity(context, arrayList);
        listView.setAdapter(customAdapterForCallActivity);
        return view;
    }
}
