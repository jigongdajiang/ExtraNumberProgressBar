package com.daimajia.numberprogressbar.example;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.daimajia.numberprogressbar.NumberProgressBar;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NumberProgressBar numberProgressBar1 = (NumberProgressBar) findViewById(R.id.numberbar1);
        numberProgressBar1.setProgress(2);
        NumberProgressBar numberProgressBar2 = (NumberProgressBar) findViewById(R.id.numberbar2);
        numberProgressBar2.setProgress(90);
        NumberProgressBar numberProgressBar3 = (NumberProgressBar) findViewById(R.id.numberbar3);
        numberProgressBar3.setProgress(50);
        NumberProgressBar numberProgressBar4 = (NumberProgressBar) findViewById(R.id.numberbar4);
        numberProgressBar4.setProgress(30);
        NumberProgressBar numberProgressBar5 = (NumberProgressBar) findViewById(R.id.numberbar5);
        numberProgressBar5.setProgress(300);
    }
}
