package com.example.whatsappagain2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ChatActivity extends AppCompatActivity {
    ImageView stt;
    EditText textView;
    String title, finalnum, num;
    int imageId;
    int MY_CALL_REQUEST_CODE = 15;
    static int currentColor = R.color.Initial_Color;
//    counter = 0 => mic mode
//    counter = 1 => send mode
    int counter = 0, ncount=0;
    static ConstraintLayout constraintLayout;
    static SQLiteDatabase sqLiteDatabase;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.chatactivitysettings, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return super.onOptionsItemSelected(item);
        }

        switch (item.getItemId()){
            case R.id.ViewContact:
                Intent contactIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                contactIntent.putExtra("Name", title);
                contactIntent.putExtra("imageId", imageId);
                String sendingnum = getIntent().getStringExtra("PhoneNo");
                contactIntent.putExtra("Number", sendingnum);
                startActivity(contactIntent);
                return true;
            case R.id.Media:
                Toast.makeText(this, "Media", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.SearchInChat:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.MuteNotification:
                Toast.makeText(this, "Mute Notification", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Wallpaper:
                Intent intent1 = new Intent(getApplicationContext(), WallpaperActivity.class);
                startActivity(intent1);
                return true;
            case R.id.More:
                return true;
            case R.id.Report:
                Toast.makeText(this, "Report", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Block:
                Toast.makeText(this, "Block", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ClearChat:
                Toast.makeText(this, "Clear Chat", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ExportChat:
                Toast.makeText(this, "Export Chat", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.AddShortCut:
                Toast.makeText(this, "Add ShortCut", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ChangeTextColor:
                changeTextColor();
                return true;


//                Icons are below.
            case R.id.VideoCamera:
                Toast.makeText(this, "Video Call Pressed", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Call:
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, MY_CALL_REQUEST_CODE);
                    }
                }else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    num = getIntent().getStringExtra("PhoneNo");
                    finalnum = num.substring(3);
                    intent.setData(Uri.parse("tel:" + finalnum));
                    startActivity(intent);
                }
                return true;

        }
        return false;
    }

    private void changeTextColor() {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, currentColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
                sqLiteDatabase.execSQL("update textColor set color = " + currentColor);
            }
        });
        ambilWarnaDialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        title = intent.getStringExtra("ContactName");
        imageId = intent.getIntExtra("ContactDp", 0);
        setTitleanddp(title, imageId);
        stt = findViewById(R.id.STT);
        textView = findViewById(R.id.editText);

        sqLiteDatabase = this.openOrCreateDatabase("BackGroundChooser", MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("create table if not exists backGround(wall int)");
        sqLiteDatabase.execSQL("create table if not exists textColor(color int)");

        int background = getBackGround(sqLiteDatabase);
        getTextColor(sqLiteDatabase);

        constraintLayout = findViewById(R.id.cLayout);
        constraintLayout.setBackgroundResource(background);


        final ListView listView = findViewById(R.id.chatListView);
        final ArrayList<String> messageHistory = new ArrayList<>();
        final CustomAdapterForChat arrayAdapter = new CustomAdapterForChat(this, messageHistory);
        listView.setAdapter(arrayAdapter);
        listView.setDivider(null);

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Drawable drawable = getDrawable(R.drawable.ic_mic_white_24dp);
                        stt.setForeground(drawable);
                        counter = 0;
                    }
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Drawable drawable = getDrawable(R.drawable.ic_send_white_24dp);
                        stt.setForeground(drawable);
                        counter = 1;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        stt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(counter == 0) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, 100);
                    } else {
                        Toast.makeText(ChatActivity.this, "Not Supported", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

        stt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if(counter == 0) {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bloops);
                    mediaPlayer.start();
                    Toast.makeText(ChatActivity.this, "Hold button to Speak", Toast.LENGTH_SHORT).show();
                }else if(counter == 1){
                    if(ncount == 0){
                        ncount = 1;
                        messageHistory.add(textView.getText().toString());
                    }else{
                        messageHistory.add("-> " + textView.getText().toString());
                        ncount = 0;
                    }
                    textView.setText(null);
                    listView.smoothScrollToPosition(messageHistory.size());
                    /**
                     * we can use firebase to store the data on cloud.Here we are not going to store any kind of data.
                     */
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getTextColor(SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.rawQuery("select * from textColor", null);
        int colorindex = cursor.getColumnIndex("color");
        cursor.moveToFirst();
        ArrayList<Integer> arrayList = new ArrayList<>();
        try {
            while (true) {
                arrayList.add(cursor.getInt(colorindex));
                cursor.moveToNext();
            }
        }catch (Exception e){
            Log.i("ErrorIs", e.toString());
        }
        if(arrayList.size() == 0){
            sqLiteDatabase.execSQL("insert into textColor values(" + currentColor + ")");
        }else{
            currentColor = arrayList.get(0);
        }
    }

    private int getBackGround(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from backGround", null);
        int wallindex = cursor.getColumnIndex("wall");
        cursor.moveToFirst();
        ArrayList<Integer> arrayList = new ArrayList<>();
        try {
            while (true) {
                arrayList.add(cursor.getInt(wallindex));
                cursor.moveToNext();
            }
        }catch (Exception e){
            Log.i("ErrorIs", e.toString());
        }
        if(arrayList.size() == 0){
            sqLiteDatabase.execSQL("insert into backGround values(" + R.drawable.whatsappbackgroundimage + ")");
            return R.drawable.whatsappbackgroundimage;
        }else{
            return arrayList.get(0);
        }
    }

    private void setTitleanddp(String title, int imageId) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imageView = new ImageView(this);
        Resources res = getResources();
        Bitmap src = BitmapFactory.decodeResource(res, imageId);
        RoundedBitmapDrawable dr =
                RoundedBitmapDrawableFactory.create(res, src);
        dr.setCircular(true);
        imageView.setImageDrawable(dr);
        getSupportActionBar().setIcon(imageView.getDrawable());
        setTitle(title);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if(resultCode == RESULT_OK && data != null){
                ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Drawable drawable;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getDrawable(R.drawable.ic_send_white_24dp);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        stt.setForeground(drawable);
                    }
                }
                textView.setText(arrayList.get(0));
            }
        }
    }

}
