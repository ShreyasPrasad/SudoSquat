package com.shreyas.sudosquat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class loading extends AppCompatActivity {
    TextView onlytext, onlytext1;
    int difficulty=gamemenu.difficulty, index;
    String userid=menu.id;
    Long gamesplayed;
    Toast processfailed;
    public boolean sweeponce=false;
    FirebaseDatabase database;
    public static String initial, completed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Typeface customfont=Typeface.createFromAsset(getAssets(),"fonts/bebas.ttf");

        onlytext=(TextView)findViewById(R.id.loadingtext);
        onlytext.setTypeface(customfont);

        onlytext1=(TextView)findViewById(R.id.loadingtext1);
        onlytext1.setTypeface(customfont);
        onlytext1.setVisibility(View.INVISIBLE);

        //init toast
        processfailed=Toast.makeText(getApplicationContext(), "Networking Process failed. Please restart.", Toast.LENGTH_SHORT);
        database=FirebaseDatabase.getInstance();
        loadBoard();
        begintransition();

    }
    private void begintransition(){
        final Intent intent =new Intent(this, game.class);
        CountDownTimer timer1= new CountDownTimer(3200,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                onlytext.setVisibility(View.INVISIBLE);
                onlytext1.setVisibility(View.VISIBLE);
                    CountDownTimer timer2= new CountDownTimer(2800,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            if (initial!=null) {
                                menu.loadGame=false;
                                menu.date=null; menu.savedGameCompletedState=null; menu.savedGameUserAnswers=null; menu.mistakes=null; menu.time=null; menu.loadGame=false;
                                game.continuetimer=true;
                                startActivity(intent);
                            }
                            else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Check your internet connection and try again.", Toast.LENGTH_LONG);
                                toast.show();
                                Intent intent1=new Intent(getApplicationContext(), gamemenu.class);
                                startActivity(intent1);
                            }
                        }
                    };
                timer2.start();
            }
        };
        timer1.start();
    }

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }

    private void loadBoard()
    {
         String rootelement="easy";
        if (difficulty==2)
            rootelement="medium";
        else if (difficulty==3)
            rootelement="hard";
        else if (difficulty==4)
            rootelement="diabolical";

             index=(int)(Math.random()*15)+1;

        DatabaseReference ref1=database.getReference(rootelement+"/"+index);
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                initial=dataSnapshot.getValue(String.class);
                Log.d ("key1: " ,initial);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                processfailed.show();
            }
        });

        ref1=database.getReference(rootelement+"/"+index+"ANS");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                completed=dataSnapshot.getValue(String.class);
                Log.d ("key2: " ,completed);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                processfailed.show();
            }
        });

        if (userid!=null) {
           DatabaseReference ref2=database.getReference("USERDATA"+"/"+userid+"/"+"Games Played");
            ref2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                     gamesplayed=dataSnapshot.getValue(Long.class);
                     Log.d("Games Played",gamesplayed+"");

                 if (!sweeponce){
                     DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
                     sweeponce=true;
                     ref2.child("USERDATA").child(userid).child("Games Played").setValue(gamesplayed+1);
                 }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    processfailed.show();
                }

            });
            DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference();
            ref3.child("USERDATA").child(userid).child("Game Entries").child(rootelement+": "+index).setValue("Complete");
        }

    }
}
