package com.shreyas.sudosquat;
//all imports
import android.content.Context;
import android.content.Intent;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;

import static com.shreyas.sudosquat.R.layout.popup;

public class menu extends AppCompatActivity {

    ImageView logomenu, startwhite, ruleswhite;
    ImageButton start, rules;
    TextView loginprompt, chooseoption;
    FloatingActionButton loginclick;
    FloatingActionButton musiccontrol;
    Button initlogin, continuewithout;
    private PopupWindow checksignIn;
    RelativeLayout screen;
        public int counter=0;
    boolean signedIn, switchedActivity, usedPrompt=false;
    long current1, duration1, current2, duration2;
    Animation fadein, fadebutton, fadebutton1, fadeout, fadeout1, slideleft, slideright,promptfadein, slideright1;
    CallbackManager callbackManager;
    static Intent musicplayer;
    ViewGroup container;
    //fb
    FirebaseDatabase database;
    AccessToken accessToken;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static String userfirstname, userlastname, username, id;
    public static boolean accessed, loadGame;
    public static String savedGameUserAnswers, savedGameCompletedState,date, time, mistakes, loadmultiplier;
    boolean messageEnabled=true;
    boolean in=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("model: ", android.os.Build.MODEL);
        if (android.os.Build.MODEL.equals("Nexus 5"))
            setContentView(R.layout.activity_menunexus5);
        else
             setContentView(R.layout.activity_menu);
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //signed in
                } else {
                    //signed out
                }
            }
        };
        //initialize typeface
        Typeface customfont=Typeface.createFromAsset(getAssets(),"fonts/bebas.ttf");
        chooseoption=(TextView)findViewById(R.id.option);
        chooseoption.setTypeface(customfont);
        startwhite = (ImageView) findViewById(R.id.startbg);
        ruleswhite = (ImageView) findViewById(R.id.rulesbg);
        startwhite.setVisibility(View.INVISIBLE);
        ruleswhite.setVisibility(View.INVISIBLE);
        //initialize animations
        fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadebutton = AnimationUtils.loadAnimation(this, R.anim.fadebuttons1);
        fadebutton1=AnimationUtils.loadAnimation(this, R.anim.fadebuttons2);
        fadeout=AnimationUtils.loadAnimation(this, R.anim.fadebuttonout1);
        fadeout1=AnimationUtils.loadAnimation(this, R.anim.fadebuttonout2);
        slideleft=AnimationUtils.loadAnimation(this,R.anim.slideleft);
        promptfadein=AnimationUtils.loadAnimation(this, R.anim.fadeprompt);
        slideright=AnimationUtils.loadAnimation(this, R.anim.slideright);
        slideright1=AnimationUtils.loadAnimation(this,R.anim.slideright1);
        //initialize components and start logo animation
        logomenu = (ImageView) findViewById(R.id.logo);
        logomenu.startAnimation(fadein);
        //initialize floatingactionbar
        musiccontrol=(FloatingActionButton)findViewById(R.id.music);
        //two buttons
        start = (ImageButton) findViewById(R.id.startbutton);
     start.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
             if (event.getAction()==MotionEvent.ACTION_DOWN){
                 startfade1();
                 current1=System.currentTimeMillis();
             }
             if (event.getAction()==MotionEvent.ACTION_UP){
                 duration1=System.currentTimeMillis()-current1;
                 decideswitch(1);

             }
             return false;
         }
     });
        rules = (ImageButton) findViewById(R.id.rulesbutton);
        rules.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    startfade2();
                    current2=System.currentTimeMillis();
                }
                if (event.getAction()==MotionEvent.ACTION_UP){
                    duration2=System.currentTimeMillis()-current2;
                    decideswitch(2);
                }
                return false;
            }
        });
        musicplayer = new Intent (this, musichandler.class);
        startService(musicplayer);

        //initialize facebooksdk
        AppEventsLogger.activateApp(getApplication());
        callbackManager= CallbackManager.Factory.create();

        loginclick=(FloatingActionButton)findViewById(R.id.facebook_login);
        loginclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(menu.this, Arrays.asList("public_profile", "user_friends", "email"));
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
    @Override
    public void onSuccess(LoginResult loginResult) {

        signedIn=true;
        Toast notify=Toast.makeText(getApplicationContext(),"Login Authentication Successful",Toast.LENGTH_SHORT);
        accessToken=loginResult.getAccessToken();
        handleFacebookAccessToken(accessToken);

        Profile profile=Profile.getCurrentProfile();
        if (profile!=null){
            userfirstname=profile.getFirstName();userlastname=profile.getLastName();username=profile.getName();accessed=true;id=profile.getId();
            notify.show();
        }
        else
             authfailed();
        if (usedPrompt && profile!=null){
            container.startAnimation(slideright);
            slideright.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    checksignIn.dismiss();
                    pastGameExists();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
        //createfirebaseuserentry

        DatabaseReference checkExist=FirebaseDatabase.getInstance().getReference("USERDATA/"+id);
        checkExist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (messageEnabled) {
                        Toast notify2 = Toast.makeText(getApplicationContext(), "Welcome back " + userfirstname + "!", Toast.LENGTH_SHORT);
                        notify2.show();
                        messageEnabled=false;
                    }
                }
                else {
                    if (id!=null) {
                        DatabaseReference loguser = FirebaseDatabase.getInstance().getReference();
                        loguser.child("USERDATA").child(id).child("Games Played").setValue(0);
                        loguser.child("USERDATA").child(id).child("Player Name: ").setValue(username);
                        messageEnabled = false;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

        if (error instanceof FacebookAuthorizationException) {
            if (AccessToken.getCurrentAccessToken() != null) {
                LoginManager.getInstance().logOut();
                LoginManager.getInstance().logInWithReadPermissions(menu.this, Arrays.asList("public_profile", "user_friends", "email"));
            }
        }
        else
        {
           authfailed();
        }
    }

});

        LayoutInflater layoutInflater;
        screen=(RelativeLayout)findViewById(R.id.activity_menu);
        layoutInflater=(LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        container=(ViewGroup)layoutInflater.inflate(popup,null);
        container.setFocusable(false);
        checksignIn=new PopupWindow(container, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        checksignIn.setOutsideTouchable(true);

        initlogin  =(Button)container.findViewById(R.id.login);
        initlogin.setTypeface(customfont);
        continuewithout=(Button)container.findViewById(R.id.cancel);
        continuewithout.setTypeface(customfont);
        initlogin.setVisibility(View.INVISIBLE);
        continuewithout.setVisibility(View.INVISIBLE);

        loginprompt=(TextView)container.findViewById(R.id.prompt);
        loginprompt.setTypeface(customfont);
        loginprompt.setVisibility(View.INVISIBLE);

        in=true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void startfade1() {
            startwhite.startAnimation(fadebutton);
            fadebutton.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    startwhite.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    startwhite.startAnimation(fadeout);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

    }
    private void startfade2() {

                ruleswhite.startAnimation(fadebutton1);
                fadebutton1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        ruleswhite.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ruleswhite.startAnimation(fadeout1);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
    }

    private void decideswitch(final int num) {
        Intent intent;
        final Intent switchscreen= new Intent(this, gamemenu.class);
        AlphaAnimation reverse;
        if (num==1 && duration1>=10){
            if (signedIn) {
                intent = new Intent(this, gamemenu.class);
                startActivity(intent);
                switchedActivity=true;
            }
            else{
                checksignIn.showAtLocation(screen, Gravity.NO_GRAVITY, 0,0);
                container.startAnimation(slideleft);
                slideleft.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        continuewithout.startAnimation(promptfadein);
                        initlogin.startAnimation(promptfadein);
                        loginprompt.startAnimation(promptfadein);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                initlogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginManager.getInstance().logInWithReadPermissions(menu.this, Arrays.asList("public_profile", "user_friends", "email"));
                        usedPrompt=true;
                    }
                });

                continuewithout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        container.startAnimation(slideright1);
                        slideright1.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                LoginManager.getInstance().logOut();userfirstname=null;username=null;userlastname=null;id=null;
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                checksignIn.dismiss();
                                switchedActivity=true;
                                startActivity(switchscreen);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                    }
                });
            }
        }
        else if (num==2 && duration2>=10) {
            switchedActivity=true;
            intent = new Intent(this, Rules.class);
            startActivity(intent);
        }
        else if (num==2 || num==1){
            reverse= new AlphaAnimation(ruleswhite.getAlpha(),0.0f);
            reverse.setDuration(500);
            reverse.start();
        }
            duration1=0; current1=0; current2=0; duration2=0;

    }

    public void musicadjust(View v){
        Context context=getApplicationContext();
        CharSequence muted="Music muted", unmuted="Music Unmuted";
        int duration= Toast.LENGTH_SHORT;
        Toast off=Toast.makeText(context,muted,duration);
        Toast on=Toast.makeText(context,unmuted,duration);
        if (musichandler.player.isPlaying()) {
            musiccontrol.setImageResource(R.drawable.musiciconstop);
            musichandler.player.pause();
            off.show();
        }
        else {
            musiccontrol.setImageResource(R.drawable.musicicon);
            musichandler.player.start();
            on.show();
        }
    }
    public void pastGameExists(){
        DatabaseReference checkGame=database.getReference("USERDATA/"+id+"/CurrentSavedGame/UserAnswers");
        checkGame.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    savedGameUserAnswers = dataSnapshot.getValue(String.class);
                    Log.d("key1: ", savedGameUserAnswers);

                }
                counter++;
                if (counter==6) ifDone();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference getComp=database.getReference("USERDATA/"+id+"/CurrentSavedGame/CompletedState");
        getComp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    savedGameCompletedState = dataSnapshot.getValue(String.class);
                    Log.d("key2: ", savedGameCompletedState);

                }
                counter++;
                if (counter==6) ifDone();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference getDate=database.getReference("USERDATA/"+id+"/CurrentSavedGame/Date");
        getDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    date = dataSnapshot.getValue(String.class);
                    Log.d("date ", date);

                }
                counter++;
                if (counter==6) ifDone();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference getTime=database.getReference("USERDATA/"+id+"/CurrentSavedGame/Time");
        getTime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    time= dataSnapshot.getValue(Long.class).toString();
                    Log.d("time: ", time);

                }
                counter++;
                if (counter==6) ifDone();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference getMistakes=database.getReference("USERDATA/"+id+"/CurrentSavedGame/MistakesNum");
        getMistakes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mistakes= dataSnapshot.getValue(Long.class).toString();
                    Log.d("time: ", mistakes);

                }
                counter++;
                if (counter==6) ifDone();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference getMultiplier=database.getReference("USERDATA/"+id+"/CurrentSavedGame/Multiplier");
        getMultiplier.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    loadmultiplier= dataSnapshot.getValue(Long.class).toString();
                    Log.d("Multiplier: ", loadmultiplier);

                }
                counter++;
                if (counter==6) ifDone();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void ifDone(){
        if (savedGameUserAnswers!=null){
            loadGame=true;
            Intent intent=new Intent (getApplicationContext(), game.class);
            startActivity(intent);
        }
        else{
            Intent intent=new Intent (getApplicationContext(), gamemenu.class);
            loadGame=false;
            startActivity(intent);
        }
    }

    private void authfailed(){
    Toast toast=Toast.makeText(getApplicationContext(), "Authentication Failed. Try Again.", Toast.LENGTH_SHORT);
    toast.show();
    }

    @Override
    public void onBackPressed(){
        //donothing
    }

    @Override
    protected void onPause(){
        if (musichandler.player!=null)
        musichandler.player.pause();
        super.onPause();
    }
    @Override
    protected void onResume(){
        if (musichandler.player!=null) {
            if (!musichandler.player.isPlaying())
                musichandler.player.start();
        }
        super.onResume();
    }
    @Override
    protected void onDestroy(){
        Log.d ("destroyed", "destroy");
        stopService(musicplayer);
        super.onDestroy();
    }

}

