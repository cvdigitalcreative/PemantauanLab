package com.rosalina.pemantauanlab;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rosalina.pemantauanlab.Fragment.LoginForm;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    private Timer timer;
    private ProgressBar progressBar;
    private int i=0;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        final TextView textView = findViewById(R.id.splashtext);
        textView.setText("");
//
//        new Thread(new Runnable() {
//            public void run() {
//                doWork(progressBar);
//                startApp();
//                finish();
//            }
//        }).start();
//    }
//
//    private void doWork(ProgressBar progressBar) {
//        for (int progress = 0; progress < 100; progress += 10) {
//            try {
//                Thread.sleep(1000);
//                progressBar.setProgress(progress);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void startApp() {
//        intent = new Intent(Splash.this, LoginActivity.class);
//        startActivity(intent);
//    }

        final long period = 100;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //this repeats every 100 ms
                if (i<100){
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(i)+"%");
                        }
                    });
                    progressBar.setProgress(i);
                    i++;
                }else{
                    //closing the timer
                    timer.cancel();
                    Intent intent =new Intent(Splash.this,LoginActivity.class);
                    startActivity(intent);
                    // close this activity
                    finish();
                }
            }
        }, 0, period);
    }
}
