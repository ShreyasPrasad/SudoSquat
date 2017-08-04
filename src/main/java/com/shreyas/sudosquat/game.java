package com.shreyas.sudosquat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static com.shreyas.sudosquat.R.layout.popuppause;

public class game extends AppCompatActivity {
//load game keys
    public static String initialstate, completedstate, dateload;
    public static char [] answers= new char[81];
    public int position, attempts=0;
    public static int currentpos;
    public static boolean screenswitched=false;
    public boolean fill, allowCheck=false;
    public static int time, mistakenum;
    public String id= menu.id;
    DatabaseReference save;
    //imageview resource ids
    int [] ids= {R.id.s1, R.id.s2, R.id.s3, R.id.s4, R.id.s5, R.id.s6, R.id.s7,
        R.id.s8, R.id.s9, R.id.s10, R.id.s11, R.id.s12, R.id.s13, R.id.s14, R.id.s15,
        R.id.s16, R.id.s17, R.id.s18, R.id.s19, R.id.s20, R.id.s21, R.id.s22, R.id.s23,
        R.id.s24, R.id.s25, R.id.s26, R.id.s27, R.id.s28, R.id.s29, R.id.s30, R.id.s31,
        R.id.s32, R.id.s33, R.id.s34, R.id.s35, R.id.s36, R.id.s37, R.id.s38, R.id.s39,
        R.id.s40, R.id.s41, R.id.s42, R.id.s43, R.id.s44, R.id.s45, R.id.s46, R.id.s47,
        R.id.s48, R.id.s49, R.id.s50, R.id.s51, R.id.s52, R.id.s53, R.id.s54, R.id.s55,
        R.id.s56, R.id.s57, R.id.s58, R.id.s59, R.id.s60, R.id.s61, R.id.s62, R.id.s63,
        R.id.s64, R.id.s65, R.id.s66, R.id.s67, R.id.s68, R.id.s69, R.id.s70, R.id.s71,
        R.id.s72, R.id.s73, R.id.s74, R.id.s75, R.id.s76, R.id.s77, R.id.s78, R.id.s79, R.id.s80, R.id.s81};

    //normal (correct) imageids
    int [] nimageids={R.drawable.e0, R.drawable.t1, R.drawable.t2, R.drawable.t3, R.drawable.t4,  R.drawable.t5, R.drawable.t6, R.drawable.t7, R.drawable.t8,R.drawable.t9};
    int [] uimageids={R.drawable.e0u, R.drawable.t1u, R.drawable.t2u, R.drawable.t3u, R.drawable.t4u, R.drawable.t5u, R.drawable.t6u, R.drawable.t7u, R.drawable.t8u, R.drawable.t9u};
    int [] dimageids={R.drawable.e0d,R.drawable.t1d, R.drawable.t2d, R.drawable.t3d, R.drawable.t4d, R.drawable.t5d, R.drawable.t6d, R.drawable.t7d, R.drawable.t8d, R.drawable.t9d};
    int [] limageids={R.drawable.e0l, R.drawable.t1l, R.drawable.t2l, R.drawable.t3l, R.drawable.t4l, R.drawable.t5l, R.drawable.t6l, R.drawable.t7l, R.drawable.t8l, R.drawable.t9l};
    int [] rimageids={R.drawable.e0r, R.drawable.t1r, R.drawable.t2r, R.drawable.t3r, R.drawable.t4r, R.drawable.t5r, R.drawable.t6r, R.drawable.t7r, R.drawable.t8r, R.drawable.t9r};
    int [] urimageids={R.drawable.e0ur, R.drawable.t1ur, R.drawable.t2ur, R.drawable.t3ur, R.drawable.t4ur, R.drawable.t5ur, R.drawable.t6ur, R.drawable.t7ur, R.drawable.t8ur, R.drawable.t9ur};
    int [] drimageids={ R.drawable.e0dr, R.drawable.t1dr, R.drawable.t2dr, R.drawable.t3dr, R.drawable.t4dr, R.drawable.t5dr, R.drawable.t6dr, R.drawable.t7dr, R.drawable.t8dr, R.drawable.t9dr};
    int [] ulimageids={R.drawable.e0ul, R.drawable.t1ul, R.drawable.t2ul, R.drawable.t3ul, R.drawable.t4ul, R.drawable.t5ul, R.drawable.t6ul, R.drawable.t7ul, R.drawable.t8ul, R.drawable.t9ul};
    int [] dlimageids={R.drawable.e0dl, R.drawable.t1dl, R.drawable.t2dl, R.drawable.t3dl, R.drawable.t4dl, R.drawable.t5dl, R.drawable.t6dl, R.drawable.t7dl, R.drawable.t8dl, R.drawable.t9dl};
    //wrong answer imageids
    int [] nwrongimageids={R.drawable.w1, R.drawable.w2, R.drawable.w3, R.drawable.w4, R.drawable.w5,  R.drawable.w6, R.drawable.w7, R.drawable.w8, R.drawable.w9};
    int [] wuimageids={R.drawable.w1u, R.drawable.w2u, R.drawable.w3u, R.drawable.w4u, R.drawable.w5u, R.drawable.w6u, R.drawable.w7u, R.drawable.w8u, R.drawable.w9u};
    int [] wdimageids={R.drawable.w1d, R.drawable.w2d, R.drawable.w3d, R.drawable.w4d, R.drawable.w5d, R.drawable.w6d, R.drawable.w7d, R.drawable.w8d, R.drawable.w9d};
    int [] wlimageids={R.drawable.w1l, R.drawable.w2l, R.drawable.w3l, R.drawable.w4l, R.drawable.w5l, R.drawable.w6l, R.drawable.w7l, R.drawable.w8l, R.drawable.w9l};
    int [] wrimageids={R.drawable.w1r, R.drawable.w2r, R.drawable.w3r, R.drawable.w4r, R.drawable.w5r, R.drawable.w6r, R.drawable.w7r, R.drawable.w8r, R.drawable.w9r};
    int [] wurimageids={R.drawable.w1ur, R.drawable.w2ur, R.drawable.w3ur, R.drawable.w4ur, R.drawable.w5ur, R.drawable.w6ur, R.drawable.w7ur, R.drawable.w8ur, R.drawable.w9ur};
    int [] wdrimageids={R.drawable.w1dr, R.drawable.w2dr, R.drawable.w3dr, R.drawable.w4dr, R.drawable.w5dr, R.drawable.w6dr, R.drawable.w7dr, R.drawable.w8dr, R.drawable.w9dr};
    int [] wulimageids={R.drawable.w1ul, R.drawable.w2ul, R.drawable.w3ul, R.drawable.w4ul, R.drawable.w5ul, R.drawable.w6ul, R.drawable.w7ul, R.drawable.w8ul, R.drawable.w9ul};
    int [] wdlimageids={R.drawable.w1dl, R.drawable.w2dl, R.drawable.w3dl, R.drawable.w4dl, R.drawable.w5dl, R.drawable.w6dl, R.drawable.w7dl, R.drawable.w8dl, R.drawable.w9dl};

    //replacement imadeids
    int[] replaceimageids={R.drawable.s1, R.drawable.s2, R.drawable.s3, R.drawable.s4, R.drawable.s5,  R.drawable.s6, R.drawable.s7, R.drawable.s8, R.drawable.s9};

    //hoverwrongids
    int [] hoverwrongids={R.drawable.hw1,R.drawable.hw2,R.drawable.hw3,R.drawable.hw4,R.drawable.hw5,R.drawable.hw6,R.drawable.hw7,R.drawable.hw8,R.drawable.hw9};
    //setup
    String [] high={"ul", "u", "ur", "ul", "u", "ur", "ul", "u", "ur"};
    String [] med={"l", "n", "r", "l", "n", "r", "l", "n", "r"};
    String [] low={"dl", "d", "dr", "dl", "d", "dr", "dl", "d", "dr"};

    //initialize button array
    ImageView [] allButtons= new ImageView[81];

    TextView mistakes, timer, pausetext;
    RelativeLayout screen;
    ViewGroup container;
    Button end, resume,saveimage1, newgame;
    Animation slideup, promptfadein, slidedown;
    private PopupWindow popuppausewindow;
    public static boolean continuetimer=true;
    Thread timerhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.MODEL.equals("Nexus 5"))
            setContentView(R.layout.activity_gamenexus5);
        else
            setContentView(R.layout.activity_game);

        mistakes=(TextView)findViewById(R.id.mistakes);
        timer=(TextView)findViewById(R.id.time);
        save = FirebaseDatabase.getInstance().getReference();//initdatabase
        for (int i=0; i<81;i++){
            allButtons[i]=(ImageView)findViewById(ids[i]);
         }
        if (screenswitched){
           mistakes.setText("Mistakes: "+mistakenum);
        }
        else {
            time=0; mistakenum=0;
            if (menu.loadGame && id!=null)
            loadSavedGame();
            else {
                initialstate = loading.initial;
                completedstate = loading.completed;
            }

            setAnswers(); //default settings
        }
        setBoard();

        //init secondary textfields

        //fontinit
        Typeface customfont=Typeface.createFromAsset(getAssets(),"fonts/bebas.ttf");
        mistakes.setTypeface(customfont);
        timer.setTypeface(customfont);

        timerhandler = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            updateTextView();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        timerhandler.start();

        LayoutInflater layoutInflater;
        screen=(RelativeLayout)findViewById(R.id.activity_game);
        layoutInflater=(LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        container=(ViewGroup)layoutInflater.inflate(popuppause,null);
        container.setFocusable(false);
        popuppausewindow=new PopupWindow(container, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        popuppausewindow.setOutsideTouchable(true);

        resume =(Button)container.findViewById(R.id.resume);
        resume.setTypeface(customfont);

        end=(Button)container.findViewById(R.id.end);
        end.setTypeface(customfont);

        pausetext=(TextView)container.findViewById(R.id.prompttext);
        pausetext.setTypeface(customfont);

        saveimage1=(Button)container.findViewById(R.id.saveimage);
        saveimage1.setTypeface(customfont);

        newgame =(Button)container.findViewById(R.id.newgame);
        newgame.setTypeface(customfont);

        resume.setVisibility(View.INVISIBLE);
        end.setVisibility(View.INVISIBLE);
        pausetext.setVisibility(View.INVISIBLE);
        saveimage1.setVisibility(View.INVISIBLE);
        newgame.setVisibility (View.INVISIBLE);

        slideup= AnimationUtils.loadAnimation(this, R.anim.slideup);
        promptfadein=AnimationUtils.loadAnimation(this, R.anim.fadeprompt);
        slidedown=AnimationUtils.loadAnimation(this, R.anim.slidedown);
    }

    public void onGRIDClick(View view){
        char typeofpiece=view.getTag().toString().charAt(0);
        Log.d("length", ""+view.getTag().toString().length()+"");
         if(attempts>1) {
            if(answers[position]=='C')
                setPiece(0, position, true);
            else if (allButtons[position].getTag().toString().length()==3)
                setPiece(Character.getNumericValue(answers[position]),position,fill);
            else{
                if (answers[position]==completedstate.charAt(position))
                    setPiece(Character.getNumericValue(answers[position]),position,true);
                else
                    setPiece(Character.getNumericValue(answers[position]),position,false);
            }
        }

        position=Integer.parseInt(view.getTag().toString().substring(1,3));
        //highlight upon click
        if ((view.getTag().toString().length()!=3)){
            allButtons[position].setImageResource(hoverwrongids[Character.getNumericValue(answers[position])-1]);
        }
        else if (typeofpiece=='C') {
            allButtons[position].setImageResource(R.drawable.e1);
        }
    }

    private void setBoard(){
        for (int i=0; i<81;i++){
            char currentChar= answers[i];

            if (currentChar=='C')
                setPiece(0, i, true);
            else {
                if (currentChar==completedstate.charAt(i))
                     setPiece(Character.getNumericValue(currentChar), i, true);
                else
                    setPiece(Character.getNumericValue(currentChar), i, false);
            }
            if (i>9) {
                if (currentChar==completedstate.charAt(i)|| currentChar=='C')
                allButtons[i].setTag(currentChar + "" + i);
                else
                    allButtons[i].setTag(currentChar + "" + i+"w");
            }
            else {
                if (currentChar==completedstate.charAt(i)|| currentChar=='C')
                allButtons[i].setTag(currentChar + "0" + i);
                     else
                allButtons[i].setTag(currentChar + "" + i + "w");
            }
        }
        allowCheck=true;
    }

    public void enterValue(View view){
        char numpiece=view.getTag().toString().charAt(0);
        answers[position]=numpiece;
        currentpos=Character.getNumericValue(numpiece);
        changePiece(Character.getNumericValue(numpiece), position);
        //reset board values and check if winning condition satisfied
        checkWin();
        if (checkWin()){
            timerhandler.interrupt();
            Intent intent = new Intent (game.this, winscreen.class);
            startActivity(intent);
        }
    }

    private void setAnswers(){
            for (int i = 0; i < initialstate.length(); i++) {
                answers[i] = initialstate.charAt(i);
            }
    }
    private boolean checkWin(){
        for (int i=0; i<initialstate.length();i++){
            if (completedstate.charAt(i)!=answers[i])
                return false;
        }
        return true;//return true if winning condition satisfied
    }

    private void changePiece(int num, int position) {
        if (answers[position] == completedstate.charAt(position)){//ifcorrect
            allButtons[position].setImageResource(replaceimageids[num - 1]);
         }
        else{//elseifincorrect
            setPiece(num, position, false);
            mistakenum++;
            musichandler.player.pause();
            Toast toast=Toast.makeText(getApplicationContext(), num+" is Wrong!", Toast.LENGTH_SHORT);
            toast.show();
            Squat();
          }
    }

    private void setPiece(int piecevalue, int index, boolean fillCorrect){
        String current;

            if (index / 9 == 0 || index / 9 == 3 || index / 9 == 6)
                current = high[index % 9];
            else if (index / 9 == 1 || index / 9 == 4 || index / 9 == 7)
                current = med[index % 9];
            else
                current = low[index % 9];

            if (fillCorrect) {
                fill=true;
                if (current.equals("u"))
                    allButtons[index].setImageResource(uimageids[piecevalue]);
                else if (current.equals("d"))
                    allButtons[index].setImageResource(dimageids[piecevalue]);
                else if (current.equals("l"))
                    allButtons[index].setImageResource(limageids[piecevalue]);
                else if (current.equals("r"))
                    allButtons[index].setImageResource(rimageids[piecevalue]);
                else if (current.equals("ul"))
                    allButtons[index].setImageResource(ulimageids[piecevalue]);
                else if (current.equals("ur"))
                    allButtons[index].setImageResource(urimageids[piecevalue]);
                else if (current.equals("dl"))
                    allButtons[index].setImageResource(dlimageids[piecevalue]);
                else if (current.equals("dr"))
                    allButtons[index].setImageResource(drimageids[piecevalue]);
                else
                    allButtons[index].setImageResource(nimageids[piecevalue]);

                if (allowCheck && piecevalue!=0){
                    String currenttag = allButtons[index].getTag().toString();
                    char [] chararray= new char[3];
                    for (int i = 0; i < 3; i++) {
                        if (i == 0)
                            chararray[0] = Integer.toString(piecevalue).charAt(0);
                        else
                            chararray[i] = currenttag.charAt(i);
                    }
                    currenttag=new String(chararray);
                    allButtons[index].setTag(currenttag);
                }

            } else {fill=false;
                if (current.equals("u"))
                    allButtons[index].setImageResource(wuimageids[piecevalue - 1]);
                else if (current.equals("d"))
                    allButtons[index].setImageResource(wdimageids[piecevalue - 1]);
                else if (current.equals("l"))
                    allButtons[index].setImageResource(wlimageids[piecevalue - 1]);
                else if (current.equals("r"))
                    allButtons[index].setImageResource(wrimageids[piecevalue - 1]);
                else if (current.equals("ul"))
                    allButtons[index].setImageResource(wulimageids[piecevalue - 1]);
                else if (current.equals("ur"))
                    allButtons[index].setImageResource(wurimageids[piecevalue - 1]);
                else if (current.equals("dl"))
                    allButtons[index].setImageResource(wdlimageids[piecevalue - 1]);
                else if (current.equals("dr"))
                    allButtons[index].setImageResource(wdrimageids[piecevalue - 1]);
                else
                    allButtons[index].setImageResource(nwrongimageids[piecevalue - 1]);
                allButtons[index].setTag(allButtons[index].getTag()+"w");
            }
            attempts++;
    }
    private void updateTextView(){
        int min=time/60;
        int sec=time%60;
        if (sec<10)
             timer.setText("Time: "+min+":0"+sec);
        else
             timer.setText("Time: "+min+":"+sec);
        if (continuetimer)
        time++;
    }

    public void openPause(View v){
        continuetimer=false;
        popuppausewindow.showAtLocation(screen, Gravity.NO_GRAVITY, 0,0);
        container.startAnimation(slideup);
        slideup.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                end.startAnimation(promptfadein);
                resume.startAnimation(promptfadein);
                pausetext.startAnimation(promptfadein);
                saveimage1.startAnimation(promptfadein);
                newgame.startAnimation(promptfadein);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.startAnimation(slidedown);
                LoginManager.getInstance().logOut();
                saveCurrentGame(true);
                Intent intent = new Intent(game.this, menu.class);
                startActivity(intent);
            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.startAnimation(slidedown);
                slidedown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        popuppausewindow.dismiss();
                        continuetimer=true;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        saveimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentGame(false);
            }
        });

        newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerhandler.interrupt();
                if (menu.id!=null)
                clearGame();
                Intent intent=new Intent (getApplicationContext(), gamemenu.class);
                startActivity(intent);
            }
        });
    }
    private void Squat(){
        Intent intent=new Intent(getApplication(), squat.class);
        screenswitched=true;
        //save current configuration
        timerhandler.interrupt();
        startActivity(intent);
    }

    public void saveCurrentGame (boolean clickedEnd){
        Toast toast;
        if (id!=null) {
            //savegamecredentials

            save.child("USERDATA").child(id).child("CurrentSavedGame").child("UserAnswers").setValue(String.valueOf(answers));
            save.child("USERDATA").child(id).child("CurrentSavedGame").child("CompletedState").setValue(String.valueOf(completedstate));
            toast=Toast.makeText(getApplicationContext(), "Game Saved Successfully. We hope you enjoyed!", Toast.LENGTH_LONG);
             //save date
            Calendar calendar=Calendar.getInstance();
            String Date = calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH)
                    + "-" + calendar.get(Calendar.DAY_OF_MONTH)
                    + " at " + calendar.get(Calendar.HOUR_OF_DAY)
                    + ":" + calendar.get(Calendar.MINUTE);
            save.child("USERDATA").child(id).child("CurrentSavedGame").child("Date").setValue(Date);
            save.child("USERDATA").child(id).child("CurrentSavedGame").child("Time").setValue(time);
            save.child("USERDATA").child(id).child("CurrentSavedGame").child("MistakesNum").setValue(mistakenum);
            save.child("USERDATA").child(id).child("CurrentSavedGame").child("Multiplier").setValue(gamemenu.multiplier);
        }
        else{
            if (!clickedEnd)
             toast=Toast.makeText(getApplicationContext(), "Facebook Login Required to Save Game.", Toast.LENGTH_LONG);
            else
             toast=Toast.makeText(getApplicationContext(), "We hope you enjoyed!", Toast.LENGTH_LONG);
        }
        toast.show();
    }

    private void loadSavedGame(){
        initialstate=menu.savedGameUserAnswers;
        completedstate=menu.savedGameCompletedState;

        dateload=menu.date;
        time=Integer.parseInt(menu.time);
        updateTextView();

        mistakenum=Integer.parseInt(menu.mistakes);
        mistakes.setText("Mistakes: "+mistakenum);

        gamemenu.multiplier=Integer.parseInt(menu.loadmultiplier);
        Toast toast=Toast.makeText(getApplicationContext(), "Loaded Game from "+dateload, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void clearGame (){//clearsavedgameregistry
        save.child("USERDATA").child(id).child("CurrentSavedGame").removeValue();
        menu.date=null; menu.savedGameCompletedState=null; menu.savedGameUserAnswers=null; menu.mistakes=null; menu.time=null; menu.loadGame=false;
    }

    @Override
    public void onBackPressed(){
        //donothing
    }

    @Override
    protected void onResume(){
        musichandler.player.start();
        super.onResume();
    }
    @Override
    protected void onDestroy(){
        Log.d ("destroyed", "destroy");
        saveCurrentGame(true);
        stopService(menu.musicplayer);
        super.onDestroy();
    }
}
