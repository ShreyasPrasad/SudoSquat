package com.shreyas.sudosquat;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class squat extends AppCompatActivity {

    TextView title, prompt, num;
    MediaPlayer playertemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squat);

        playertemp = MediaPlayer.create(this, R.raw.sandstorm);
        playertemp.setLooping(true);
        playertemp.setVolume(90,90);
        playertemp.start();

        title=(TextView)findViewById(R.id.title);
        prompt=(TextView)findViewById(R.id.prompt);
        num=(TextView)findViewById(R.id.numsquat);

        Typeface customfont=Typeface.createFromAsset(getAssets(),"fonts/bebas.ttf");
        title.setTypeface(customfont);
        prompt.setTypeface(customfont);
        num.setTypeface(customfont);

        int numint=gamemenu.multiplier*game.currentpos;
        prompt.setText("Do "+numint+" squats!");

        num.setText(numint+"");

    }

    public void returnscreen(View v){
        //release sandstorm
        playertemp.stop();
        playertemp.release();
       //resume normal music
        if (!musichandler.player.isPlaying())
        musichandler.player.start();
        //return to last activity
        Intent intent=new Intent (getApplication(), game.class);
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        playertemp.start();
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        Log.d ("destroyed", "destroy");
        stopService(menu.musicplayer);
        super.onDestroy();
    }
}
