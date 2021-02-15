package com.example.homefit;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Rest extends AppCompatActivity {
    private TextView timer;
    private myTimer myTimer30;
    private ImageView restTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        startLoading();
        timer = findViewById(R.id.txtTimer);
        countTime30();
       // restTimer = findViewById(R.id.imgRestTimer);

        Glide.with(this).load(R.drawable.resttime).into(restTimer);

    }
    private void countTime30()
    {
        myTimer30 = new myTimer(30000,1000);
        myTimer30.start();
    }
    private void startLoading(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              finish();
            }
        },30000);
    }class myTimer extends CountDownTimer {
        long mil = 0;
        long countdown = 0;
        public myTimer(long mil , long countDown)
        {
            super(mil,countDown);
            this.mil = mil;
            this.countdown = countDown;
        }
        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText(millisUntilFinished/1000 + " 초");
        }


        @Override
        public void onFinish() {
            timer.setText("0 초");
        }
    }
}
