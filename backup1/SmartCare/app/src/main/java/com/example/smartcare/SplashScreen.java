package com.example.smartcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //timer of splash screen bago mag redirect sa login page
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                    startActivity(new Intent(getApplicationContext(), Login.class));
            }
        }, 5000);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}