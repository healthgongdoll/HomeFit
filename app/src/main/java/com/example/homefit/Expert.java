package com.example.homefit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

public class Expert extends AppCompatActivity {
    private TextView timer;
    private TextView restTimer;
    private myTimer myTimer60;
    private newTimer rTimer;
    private myTimer myTimer30;

    LinearLayout mykonten, overbox;
    private TextView helpMessage;
    private ImageView workoutImage;
    private TextView mainmenu;
    private Animation fromsmall,togo;
    private Button check;
    private boolean isTime = false;
    private int i = 0;
    private ProgressBar progressBar;
    private int [] arrWorkOut = {R.drawable.pullup,R.drawable.situp, R.drawable.pushup,R.drawable.flank};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        progressBar = findViewById(R.id.mProgressbar);
        mykonten = findViewById(R.id.mykonten);
        overbox = findViewById(R.id.overbox);
        mainmenu = findViewById(R.id.txtGoMain);
        check = findViewById(R.id.btnIC);
        restTimer = findViewById(R.id.txtTimer30);
        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Expert.this, MainActivity.class);
                startActivity(intent);
            }
        });
        workoutImage =findViewById(R.id.pushupImg);
        // Gif
        Glide.with(this).load(R.mipmap.stretching).into(workoutImage);
        timer = findViewById(R.id.txtTimer);
        countTime30();


        fromsmall = AnimationUtils.loadAnimation(Expert.this,R.anim.fromsmall);
        togo = AnimationUtils.loadAnimation(Expert.this,R.anim.togo);
        mykonten.setAlpha(0);
        overbox.setAlpha(0);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mykonten.setAlpha(1);
                overbox.setAlpha(1);
                mykonten.startAnimation(fromsmall);

                //
                rTimer = new newTimer(30000,1000);
                rTimer.start();
                initProg();
                startTimerThread();

                //
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        overbox.startAnimation(togo);
                        mykonten.startAnimation(togo);

                        ViewCompat.animate(mykonten).setStartDelay(1000).alpha(0).start();
                        ViewCompat.animate(overbox).setStartDelay(1000).alpha(0).start();
                        rTimer.cancel();
                    }
                },30000);

                if(myTimer60 != null) {
                    countTIme60Finish();
                }
                countTIme30Finish();

                Glide.with(Expert.this).load(arrWorkOut[i]).into(workoutImage);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        countTime60();
                    }
                },30000);

                i++;

                if(i > arrWorkOut.length)
                {
                    goToMain();
                }

            }
        });


        // Timer



        // 설명서
        helpMessage = findViewById(R.id.txtQuestion);
        helpMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(Expert.this);
                ad.setIcon(R.mipmap.ic_launcher);
                ad.setTitle("운동에 관한 설명");
                ad.setMessage("푸쉬업을 할때는 정자세로 하세요");
                ad.setPositiveButton("이해했습니다", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });

    }
    class newTimer extends CountDownTimer {
        long mil = 0;
        long countdown = 0;
        public newTimer(long mil , long countDown)
        {
            super(mil,countDown);
            this.mil = mil;
            this.countdown = countDown;
        }
        @Override
        public void onTick(long millisUntilFinished) {
            restTimer.setText(millisUntilFinished/1000 + " 초");

        }
        @Override
        public void onFinish() {
            restTimer.setText("0 초");

        }
    }
    //
    private void countTime60()
    {
        myTimer60 = new myTimer(60000, 1000);
        myTimer60.start();
    }
    private void countTIme60Finish()
    {
        myTimer60.cancel();
    }
    private void countTIme30Finish()
    {
        myTimer30.cancel();
    }
    private void startToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    private void countTime30()
    {
        myTimer30 = new myTimer(30000,1000);
        myTimer30.start();
    }
    class myTimer extends CountDownTimer {
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

    private void startRestActivity()
    {
        Intent intent = new Intent(Expert.this,Rest.class);
        startActivity(intent);
    } private void goToMain()
    {
        Intent intent = new Intent(Expert.this,MainActivity.class);
        startActivity(intent);
    }
    private int countdown = 30000;
    public void initProg(){
        progressBar.setMax(countdown);
        progressBar.setProgress(countdown);
    }
    TimerTask timerTask = null;
    Timer ti = null;
    public void startTimerThread(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                decreaseBar();
            }
        };
        ti = new Timer();
        ti.schedule(timerTask,0,1000);
    }
    public void decreaseBar(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                countdown = progressBar.getProgress();
                if(countdown > 0)
                {
                    countdown = countdown-1;
                }else if(countdown == 0)
                {
                    ti.cancel();
                    Thread.interrupted();
                }
                progressBar.setProgress(countdown);
            }
        });
    }
}
