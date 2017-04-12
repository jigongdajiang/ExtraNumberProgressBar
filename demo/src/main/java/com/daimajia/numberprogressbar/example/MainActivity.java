package com.daimajia.numberprogressbar.example;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.daimajia.numberprogressbar.NumberProgressBar;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NumberProgressBar numberProgressBar = (NumberProgressBar) findViewById(R.id.numberbar1);
        numberProgressBar.setProgress(2);
    }
}
