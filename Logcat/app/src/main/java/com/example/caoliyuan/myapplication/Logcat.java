package com.example.caoliyuan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Logcat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("1","verbose");
        Log.d("2","debug");
        Log.i("1","info");
        Log.w("2","warning");
        Log.e("1","error");
        setContentView(R.layout.activity_element);
    }
}
