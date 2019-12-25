package com.example.whatsappagain2;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.whatsappagain2.Fragment_Chats.customAdapterForMainScene;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    PagerViewAdapter pagerViewAdapter;
    int count = 0;
    static ArrayList<String> names, contacts;


    int MY_CAMERA_REQUEST_CODE = 5;
    int MY_CONTACT_REQUEST_CODE = 10;
    int MY_CALL_REQUEST_CODE = 15;
    int MY_STORAGE_REQUEST_CODE = 20;
    /**
    * the below are the textViews and imageViews of the tabbed activity top.
    * */
    TextView chat, status, call;
    ImageView camera;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.usersetting, menu);

        MenuItem item = menu.findItem(R.id.Search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setQueryHint("Search...");
//        Below is for getting the moment when we click the icon.
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.include).setAlpha(0);
                viewPager.animate().translationYBy(-150).setDuration(1000).start();
            }
        });

//        Below is for getting the moment when we click the cross icon.
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                findViewById(R.id.include).setAlpha(1);
                viewPager.animate().translationYBy(150).setDuration(1000).start();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    Toast.makeText(MainActivity.this, "Here", Toast.LENGTH_SHORT).show();
                    customAdapterForMainScene.filter("");
                }else{
                    Toast.makeText(MainActivity.this, "Here1", Toast.LENGTH_SHORT).show();
                    newText = makeComfortableString(newText);
                    customAdapterForMainScene.filter(newText);
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.NewGroup:
                Toast.makeText(this, "New Group", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.NewBroadcast:
                Toast.makeText(this, "New BroadCast", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.WhatsAppWeb:
                Toast.makeText(this, "WhatsApp Web", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.StarredMessages:
                Toast.makeText(this, "Starred Messages", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;

//                Icons are below.
            case R.id.Search:
                Toast.makeText(this, "Here310", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(!getSupportActionBar().isShowing()) {
            getSupportActionBar().show();
            count = 1;
            return true;
        }
        if(count == 1) {
            count = 0;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 5:
                if (data != null) {
                    Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Nothing Captured", Toast.LENGTH_SHORT).show();
                }
                viewPager.setCurrentItem(1);
                chat.setTextSize(20);
                chat.setTextColor(Color.WHITE);

                status.setTextSize(15);
                status.setTextColor(Color.BLACK);

                call.setTextSize(15);
                call.setTextColor(Color.BLACK);
                break;
            case 10:
                getContactList();
                break;

        }
    }

    public boolean hasPermissions(Context context, String... permissions){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null){
            for(String permission : permissions){
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);
        camera = findViewById(R.id.CameraId);
        call = findViewById(R.id.CallsId);
        status = findViewById(R.id.StatusId);
        chat = findViewById(R.id.ChatsId);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.whatsappagain2", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("Wallpaper", R.drawable.whatsappbackgroundimage).apply();

        viewPager = findViewById(R.id.Container);
        pagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerViewAdapter);

        viewPager.setCurrentItem(1);
        chat.setTextSize(20);
        chat.setTextColor(Color.WHITE);

        status.setTextSize(15);
        status.setTextColor(Color.BLACK);

        call.setTextSize(15);
        call.setTextColor(Color.BLACK);

        names = new ArrayList<>();
        contacts = new ArrayList<>();


        int permission_All = 1;
        String[] allPermissions = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, android.Manifest.permission.CAMERA};
        if(!hasPermissions(this, allPermissions)){
            ActivityCompat.requestPermissions(this, allPermissions, permission_All);
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onTabChanged(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void onTabChanged(int position) {
        if(position == 0){

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(intent, 5);
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                }
                viewPager.setCurrentItem(1);
                chat.setTextSize(20);
                chat.setTextColor(Color.WHITE);

                status.setTextSize(15);
                status.setTextColor(Color.BLACK);

                call.setTextSize(15);
                call.setTextColor(Color.BLACK);
            }

            chat.setTextSize(15);
            chat.setTextColor(Color.BLACK);

            status.setTextSize(15);
            status.setTextColor(Color.BLACK);

            call.setTextSize(15);
            call.setTextColor(Color.BLACK);
        }
        if(position == 1){
            chat.setTextSize(20);
            chat.setTextColor(Color.WHITE);

            status.setTextSize(15);
            status.setTextColor(Color.BLACK);

            call.setTextSize(15);
            call.setTextColor(Color.BLACK);
        }
        if(position == 2){
            chat.setTextSize(15);
            chat.setTextColor(Color.BLACK);

            status.setTextSize(20);
            status.setTextColor(Color.WHITE);

            call.setTextSize(15);
            call.setTextColor(Color.BLACK);
        }
        if(position == 3){
            chat.setTextSize(15);
            chat.setTextColor(Color.BLACK);

            status.setTextSize(15);
            status.setTextColor(Color.BLACK);

            call.setTextSize(20);
            call.setTextColor(Color.WHITE);
        }
    }

    public void getContactList() {
        ContentResolver cr = getContentResolver();
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

    private String makeComfortableString(String newText) {
        char fLetter = Character.toUpperCase(newText.charAt(0));
        String remaining = newText.substring(1);
        return fLetter + remaining;
    }


}
