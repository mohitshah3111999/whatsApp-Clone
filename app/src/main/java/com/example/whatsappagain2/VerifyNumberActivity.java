package com.example.whatsappagain2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.whatsappagain2.WelcomeActivity.sharedPreferences;

public class VerifyNumberActivity extends AppCompatActivity {

    String verificationCode;
    FirebaseAuth firebaseAuth;
    EditText otpReceiver;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_number);

        number = getIntent().getStringExtra("Number");
        firebaseAuth = FirebaseAuth.getInstance();

        otpReceiver = findViewById(R.id.OTPReceiver);

        Log.i("numberis", number);
        sendVerificationCode(number);

        Button verify = findViewById(R.id.VerificationButton);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otpReceiver.getText().toString().trim();
                if(code.isEmpty() || code.length() < 6){
                    otpReceiver.setError("Enter Valid Code");
//                    TODO at the end, remove below 2 lines.
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("Decider", 1);
                    editor.apply();
                    startActivity(intent);
                    finish();
                    return;
                }
                verifyCode(code);
            }
        });

    }

    private void verifyCode(String code){
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationCode, code);
        signInWithCredential(phoneAuthCredential);
    }

    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("Decider", 1);
                    Log.i("EditorValue", String.valueOf(sharedPreferences.getInt("Decider", 5)));
                    editor.apply();
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(VerifyNumberActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCode = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyNumberActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
