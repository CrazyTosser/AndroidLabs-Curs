package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("LAB", "Activity created");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LAB", "Activity resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LAB", "Activity paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LAB", "Activity stoped");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LAB", "Activity started");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LAB", "Activity destroyed");
    }
}
