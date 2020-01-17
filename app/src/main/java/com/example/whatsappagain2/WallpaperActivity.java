package com.example.whatsappagain2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

import static com.example.whatsappagain2.ChatActivity.constraintLayout;
import static com.example.whatsappagain2.ChatActivity.sqLiteDatabase;

public class WallpaperActivity extends AppCompatActivity {

    public void changeBackGround(View view){
        ImageView imageView = (ImageView) view;
        int tag = Integer.parseInt(imageView.getTag().toString());
        Drawable drawable;

        switch (tag){
            case 1:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getDrawable(R.drawable.bg1);
                    constraintLayout.setBackground(drawable);
                    sqLiteDatabase.execSQL("update backGround set wall = " + R.drawable.bg1);
                }
                finish();
                break;
            case 2:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getDrawable(R.drawable.bg2);
                    constraintLayout.setBackground(drawable);
                    sqLiteDatabase.execSQL("update backGround set wall = " + R.drawable.bg2);
                }
                finish();
                break;
            case 3:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getDrawable(R.drawable.bg3);
                    constraintLayout.setBackground(drawable);
                    sqLiteDatabase.execSQL("update backGround set wall = " + R.drawable.bg3);
                }
                finish();
                break;
            case 4:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getDrawable(R.drawable.bg4);
                    constraintLayout.setBackground(drawable);
                    sqLiteDatabase.execSQL("update backGround set wall = " + R.drawable.bg4);
                }
                finish();
                break;
            case 5:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getDrawable(R.drawable.bg5);
                    constraintLayout.setBackground(drawable);
                    sqLiteDatabase.execSQL("update backGround set wall = " + R.drawable.bg5);
                }
                finish();
                break;
            case 6:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getDrawable(R.drawable.whatsappbackgroundimage);
                    constraintLayout.setBackground(drawable);
                    sqLiteDatabase.execSQL("update backGround set wall = " + R.drawable.whatsappbackgroundimage);
                }
                finish();
                break;
            case 7:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getDrawable(R.drawable.bg7);
                    constraintLayout.setBackground(drawable);
                    sqLiteDatabase.execSQL("update backGround set wall = " + R.drawable.bg7);
                }
                finish();
                break;
            case 8:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getDrawable(R.drawable.bg8);
                    constraintLayout.setBackground(drawable);
                    sqLiteDatabase.execSQL("update backGround set wall = " + R.drawable.bg8);
                }
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        final int MY_STORAGE_REQUEST_CODE = 20;
        findViewById(R.id.ChooseFromGallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_STORAGE_REQUEST_CODE);
                    }
                }else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, MY_STORAGE_REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 20){
            if(data != null){
                if(resultCode == RESULT_OK){
                    Uri uri =data.getData();
                    try {
                        Bitmap bitmap =MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            constraintLayout.setBackground(drawable);
                        }
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
