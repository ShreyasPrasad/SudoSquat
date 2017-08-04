package com.shreyas.sudosquat;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class winscreen extends AppCompatActivity {
    TextView mistakesprompt, timeprompt;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winscreen);

        mistakesprompt=(TextView) findViewById(R.id.mistakesprompt);
        timeprompt=(TextView) findViewById(R.id.timeprompt);

        mistakesprompt.setText("Mistakes: "+game.mistakenum);
        timeprompt.setText("Time: "+game.time);

        back=(Button)findViewById(R.id.returnmenu);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (getApplicationContext(), menu.class);
                startActivity(intent);
            }
        });

        //setfonts
        Typeface customfont=Typeface.createFromAsset(getAssets(),"fonts/bebas.ttf");
        mistakesprompt.setTypeface(customfont);
        timeprompt.setTypeface(customfont);
        back.setTypeface(customfont);
    }
}
