package com.shreyas.sudosquat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;


public class gamemenu extends AppCompatActivity {
    String userfirstname=menu.userfirstname, userid=menu.id;
    ImageView userinfobg, defaultimage, returnmenu;;
    TextView welcome, nametext, prompt1;
    ProfilePictureView profilePictureView;
    Button finalstart;
    ImageButton difficultyimg, multiplierimg;
    Animation zoomin, zoomin1;

    public static int difficulty=1, multiplier=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (userid!=null) {
            if (Build.MODEL.equals("Nexus 5"))
                setContentView(R.layout.activity_gamemenunexus5);
            else
            setContentView(R.layout.activity_gamemenu);
        }
        else {
            if (Build.MODEL.equals("Nexus 5"))
                setContentView(R.layout.activity_gamemenunologinnexus5);
            else
            setContentView(R.layout.activity_gamemenunologin);
        }
        userinfobg=(ImageView)findViewById(R.id.userinfobg);

        //init animations
        zoomin= AnimationUtils.loadAnimation(this, R.anim.zoomin);
        zoomin1=AnimationUtils.loadAnimation(this, R.anim.zoomin1);

        Typeface customfont=Typeface.createFromAsset(getAssets(),"fonts/bebas.ttf");
        welcome=(TextView)findViewById(R.id.welcome);
        welcome.setText("Welcome to Sudosquat");
        welcome.setTypeface(customfont);

        nametext=(TextView)findViewById(R.id.name);
        nametext.setTypeface(customfont);

        if (userid != null) {

            if (userfirstname.length()>6){
                nametext.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
            }
            nametext.setText(userfirstname+"!");
            profilePictureView=(ProfilePictureView)findViewById(R.id.profilePicture);
            profilePictureView.setProfileId(userid);
        }
        else{
            nametext.setText("Are you ready?");
            nametext.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
        }

        prompt1=(TextView)findViewById(R.id.difficultyprompt);
        prompt1.setTypeface(customfont);

        final Intent intent= new Intent(this, loading.class);
        finalstart=(Button)findViewById(R.id.finalstart);
        finalstart.setTypeface(customfont);
        finalstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        difficultyimg=(ImageButton)findViewById(R.id.imagedifficult);
        difficultyimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(difficultyimg.getTag()).equals("first")) {
                    initimgswitch1(1);
                    difficultyimg.setTag("second");
                }
                else if (String.valueOf(difficultyimg.getTag()).equals("second")) {
                    initimgswitch1(2);
                    difficultyimg.setTag("third");
                }
                else if (String.valueOf(difficultyimg.getTag()).equals("third")){
                    initimgswitch1(3);
                    difficultyimg.setTag("fourth");
                }
                else {
                    initimgswitch1(4);
                    difficultyimg.setTag("first");
                }
            }
        });
        multiplierimg=(ImageButton)findViewById(R.id.intensity);
        multiplierimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(multiplierimg.getTag()).equals("first")) {
                    initimgswitch2(1);
                    multiplierimg.setTag("second");
                }
                else if (String.valueOf(multiplierimg.getTag()).equals("second")) {
                    initimgswitch2(2);
                    multiplierimg.setTag("third");
                }
                else {
                    initimgswitch2(3);
                    multiplierimg.setTag("first");
                }

            }
        });

        returnmenu=(ImageView)findViewById(R.id.backbutton);
    }



    private void initimgswitch1(int image){
       switch (image){
           case 1:
               difficulty=2;
               difficultyimg.setImageResource(R.drawable.mediumbutton);
               break;
           case 2:
               difficulty=3;
               difficultyimg.setImageResource(R.drawable.hardbutton);
               break;
           case 3:
               difficulty=4;
               difficultyimg.setImageResource(R.drawable.diabbutton);
               break;
           case 4:
               difficulty=1;
               difficultyimg.setImageResource(R.drawable.easybutton);
               break;
        }
        difficultyimg.startAnimation(zoomin);
    }

    private void initimgswitch2(int image){
        switch (image){
            case 1:
                multiplier=2;
                multiplierimg.setImageResource(R.drawable.multiplierx2);
                break;
            case 2:
                multiplier=3;
                multiplierimg.setImageResource(R.drawable.multiplierx3);
                break;
            case 3:
                multiplier=1;
                multiplierimg.setImageResource(R.drawable.multiplierx1);
                break;
        }
        multiplierimg.startAnimation(zoomin1);
    }
        public void goback (View view){
            Intent intent=new Intent(getApplication(), menu.class);
            startActivity(intent);
        }
    @Override
    protected void onPause(){
        musichandler.player.pause();
        super.onPause();
    }

    @Override
    protected void onResume(){
        musichandler.player.start();
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        Log.d ("destroyed", "destroy");
        stopService(menu.musicplayer);
        super.onDestroy();
    }
    }


