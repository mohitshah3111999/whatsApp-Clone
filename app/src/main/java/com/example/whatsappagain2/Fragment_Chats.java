package com.example.whatsappagain2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment_Chats extends Fragment {
    Context context;
    static ArrayList<ItemHolder> list;
    ArrayList<String> names, contacts;
    static CustomAdapterForMainScene customAdapterForMainScene;
    int MY_CONTACT_REQUEST_CODE = 10;

    Fragment_Chats(Context context){
        this.context = context;
    }


    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, null);
        ListView listView = view.findViewById(R.id.listview);
        list = new ArrayList<>();

        TextView textView =  view.findViewById(R.id.GeneralMessage);

        names = new ArrayList<>();
        contacts = new ArrayList<>();

        textView.setText("Please Reopen the app to sync contacts");

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, MY_CONTACT_REQUEST_CODE);
        }else{
            getContactList();
        }

        if(names.size() == 0 && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            textView.setText("You Have no contacts");
        }

        for(int i=0; i<names.size(); i++){
            list.add(new ItemHolder(R.drawable.manchurian, names.get(i), contacts.get(i)));
        }

        if(list.size() != 0){
            textView.setVisibility(View.INVISIBLE);
        }

        customAdapterForMainScene = new CustomAdapterForMainScene(context, list);
        listView.setAdapter(customAdapterForMainScene);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("ContactName", list.get(position).name);
                intent.putExtra("PhoneNo", list.get(position).phoneNo);
                intent.putExtra("ContactDp", list.get(position).personDp);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getContactList() {
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        names.add(name);
                        contacts.add(phoneNo);
                        if(names.size() >= 2){
                            if (names.get(names.size()-1).equals(names.get(names.size()-2))){
                                names.remove(names.size()-1);
                                contacts.remove(contacts.size()-1);
                            }
                        }
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }

}
