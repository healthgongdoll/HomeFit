package com.example.homefit;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;

public class Intermediate extends AppCompatActivity {
    private TextView timer;
    private TextView restTimer;
    private myTimer myTimer60;
    private newTimer rTimer;
    private TextView messageTv;
    private myTimer myTimer30;
    private Dialog epicDialog;
    private TextView exerciseName;
    private ImageView closePopupPositiveImg;
    LinearLayout mykonten, overbox;
    private TextView helpMessage;
    private ImageView workoutImage;
    private TextView mainmenu;
    private Animation fromsmall,togo;
    private Button check,btnAccept;
    private boolean isTime = false;
    private int i = 0;
    private ProgressBar progressBar;
    private int [] arrWorkOut = {R.drawable.pullup,R.drawable.situp, R.drawable.pushup,R.drawable.plank,R.drawable.squat,R.drawable.chairdip};
    private String[] workoutName ={"Pull Up","Sit Up","Push Up","Flank","Squat","Chair Dip"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        progressBar = findViewById(R.id.mProgressbar);
        mykonten = findViewById(R.id.mykonten);
        overbox = findViewById(R.id.overbox);
        mainmenu = findViewById(R.id.txtGoMain);
        check = findViewById(R.id.btnIC);
        restTimer = findViewById(R.id.txtTimer30);
        exerciseName = findViewById(R.id.ename);
        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intermediate.this, MainActivity.class);
                startActivity(intent);
            }
        });
        workoutImage =findViewById(R.id.pushupImg);
        // Gif
        Glide.with(this).load(R.mipmap.stretching).into(workoutImage);
        timer = findViewById(R.id.txtTimer);
        countTime30();


        fromsmall = AnimationUtils.loadAnimation(Intermediate.this,R.anim.fromsmall);
        togo = AnimationUtils.loadAnimation(Intermediate.this,R.anim.togo);
        mykonten.setAlpha(0);
        overbox.setAlpha(0);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mykonten.setAlpha(1);
                overbox.setAlpha(1);
                mykonten.startAnimation(fromsmall);
                exerciseName.setText(workoutName[i]);
                //
                rTimer = new newTimer(30000,1000);
                rTimer.start();


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

                Glide.with(Intermediate.this).load(arrWorkOut[i]).into(workoutImage);




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
        epicDialog = new Dialog(this);

        helpMessage = findViewById(R.id.txtQuestion);
        helpMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowPositviePopup(i);
            }
        });
    }

    //

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
            int progress = (int)(millisUntilFinished/1000);
            progressBar.setProgress(progressBar.getMax()-progress);
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
        Intent intent = new Intent(Intermediate.this,Rest.class);
        startActivity(intent);
    } private void goToMain()
    {
        Intent intent = new Intent(Intermediate.this,MainActivity.class);
        startActivity(intent);
    }
    public void ShowPositviePopup(int i)
    {
        epicDialog.setContentView(R.layout.epic_popup_positive);
        closePopupPositiveImg = (ImageView)epicDialog.findViewById(R.id.closePopup);
        btnAccept = (Button)epicDialog.findViewById(R.id.btnAccept1);
        messageTv = (TextView)epicDialog.findViewById(R.id.messageTV);

        if(i == 0)
        {
            messageTv.setText("You Should Warm Up by doing dynamic stretches");
        }
        if(i == 1)
        {
            messageTv.setText("Grab the bar, Open your chest and look at the ceiling and PULL!!");
        }
        if( i == 2)
        {
            messageTv.setText("Your hands should be slightly wider than your shoulders. \n Lower your torso to the ground until your elbows reach a 90-degree angle \n Breathe out as you push");
        }
        if( i == 3)
        {
            messageTv.setText("Look down to avoid neck strain \n Pull your belly button up toward your spine \n knees straight \n squeeze abs and glutes");
        }
        if( i == 4)
        {
            messageTv.setText("Do not start with bent legs \n Do not curve back");
        }
        if( i == 5)
        {
            messageTv.setText("Lower your body using the elbows until your upper arm is less than 90 degree with the forearm");
        }

        closePopupPositiveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

}
