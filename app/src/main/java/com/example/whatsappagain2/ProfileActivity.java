package com.example.whatsappagain2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    String title, finalnum;
    int imageId;
    int MY_CALL_REQUEST_CODE = 15;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        imageId = intent.getIntExtra("imageId", 0);
        title = intent.getStringExtra("Name");
        finalnum = intent.getStringExtra("Number");

        getSupportActionBar().setTitle(title);

        ImageView imageView = findViewById(R.id.DPinProfile);
        imageView.setImageResource(imageId);

        Button button = findViewById(R.id.BlockContact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Really???", Toast.LENGTH_SHORT).show();
            }
        });

        Button button1 = findViewById(R.id.ReportSpam);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Spammer", Toast.LENGTH_SHORT).show();
            }
        });

        TextView textView = findViewById(R.id.PhoneNumberInProfile);
        textView.setText(finalnum);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, MY_CALL_REQUEST_CODE);
                    }
                }else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    if(finalnum.charAt(0) == '+') {
                        finalnum = finalnum.substring(3);
                    }
                    intent.setData(Uri.parse("tel:" + finalnum));
                    startActivity(intent);
                }
            }
        });
    }
}
