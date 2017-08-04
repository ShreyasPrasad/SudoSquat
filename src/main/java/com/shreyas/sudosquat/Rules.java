package com.shreyas.sudosquat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Rules extends AppCompatActivity {
    ImageView rulestext;
    Animation fadeout, fadein;
    ImageButton returntostart;
    public boolean returned=false;
    Intent intent, music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

       //initialize rulestext imageview

         rulestext=(ImageView)findViewById(R.id.rulestext);
         fadeout= AnimationUtils.loadAnimation(this, R.anim.fadebuttonout1);
         fadein=AnimationUtils.loadAnimation(this, R.anim.fadebuttons1);
        //initialize button and return intent
         intent= new Intent(this, menu.class);
         returntostart=(ImageButton)findViewById(R.id.returntostart);
         returntostart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                returned=true;
            }
           });
         implementimageswap();

    }
    private void implementimageswap(){
        CountDownTimer timer1;
        timer1=new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                rulestext.startAnimation(fadeout);
                fadeout.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rulestext.setImageResource(R.drawable.rulesclaim);
                        rulestext.startAnimation(fadein);
                        returntostart.setVisibility(View.VISIBLE);
                        returntostart.setEnabled(true);
                        returntostart.startAnimation(fadein);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        };
        timer1.start();
    }

    @Override
    protected void onDestroy(){
        Log.d ("destroyed", "destroy");
        stopService(menu.musicplayer);
        super.onDestroy();
    }

    @Override
    protected void onResume(){
        musichandler.player.start();
        super.onResume();
    }

    @Override
    protected void onPause(){
       if (!returned)
        musichandler.player.pause();
        super.onPause();
    }
}
