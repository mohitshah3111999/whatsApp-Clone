package com.example.whatsappagain2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    int code;
    static SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sharedPreferences = this.getSharedPreferences("com.example.whatsappagain2", Context.MODE_PRIVATE);

//        if successfully verified, make it 1, else 0;
        verifyUser();

        final Spinner spinner = findViewById(R.id.NumberStarting);
        final ArrayList<CountryHolder> countries = new ArrayList<>();

        initializer(countries);

        CustomAdapterForCountryCode customAdapterForCountryCode = new CustomAdapterForCountryCode(this, countries);
        spinner.setAdapter(customAdapterForCountryCode);

        final EditText number = findViewById(R.id.PhoneNumber);
        Button send = findViewById(R.id.OTPSender);

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 10){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number.getText().toString().length() == 0){
                    number.setError("Please Enter The Phone Number");
                    return;
                }else if(number.getText().toString().length() < 10 || number.getText().toString().length() < 10){
                    number.setError("Please Enter 10 digit Number");
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), VerifyNumberActivity.class);
                String numberInString = number.getText().toString();
                String phoneNumber = "+" + code + numberInString;
                intent.putExtra("Number", phoneNumber);
                startActivity(intent);
                finish();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                code = countries.get(spinner.getSelectedItemPosition()).countryCode;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void verifyUser() {
        int decider = sharedPreferences.getInt("Decider", 2);
        if(decider == 1){
            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initializer(ArrayList<CountryHolder> countries) {
        /**
        *Below method is used for hard coding countries code, their flag and their names.
        **/

        countries.add(new CountryHolder(R.drawable.afg, "Afghanistan", 93));
        countries.add(new CountryHolder(R.drawable.angola, "Angola", 244));
        countries.add(new CountryHolder(R.drawable.argentina, "Argentina", 54));
        countries.add(new CountryHolder(R.drawable.india, "India", 91));
        countries.add(new CountryHolder(R.drawable.nepal, "Nepal", 977));
        countries.add(new CountryHolder(R.drawable.pakistan, "Pakistan", 92));
        countries.add(new CountryHolder(R.drawable.sri_lnka, "Sri Lanka", 94));
        countries.add(new CountryHolder(R.drawable.us, "United States", 1));
    }

    public void refresher(View view){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (NullPointerException e){
            Log.i("ErrorIs", e.getMessage());
        }

    }
}
