package com.example.whatsappagain2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    @SuppressLint("ObjectAnimatorBinding")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

        ProgressBar progreso;
        ObjectAnimator progressAnimator;
        progreso = findViewById(R.id.progressBar);
        progressAnimator = ObjectAnimator.ofFloat(progreso, "progress", 0.0f,1.0f);
        progressAnimator.setDuration(3000);
        progressAnimator.start();
    }
}
