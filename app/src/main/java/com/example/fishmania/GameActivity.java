package com.example.fishmania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import java.util.Timer;

import java.time.LocalDateTime;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    int height, width;
    //Scoreboard records
    int numOfFishEaten, finalScore;
    LocalDateTime dateTime;
    //ImageViews
    private ImageView playerFish, firstOtherFish, secondOtherFish, thirdOtherFish, forthOtherFish;
    //Positions of Fish
    private float xPlayerFish, yPlayerFish;
    private float xFirstOtherFish, yFirstOtherFish;
    private float xSecondOtherFish, ySecondOtherFish;
    private float xThirdOtherFish, yThirdOtherFish;
    private float xForthOtherFish, yForthOtherFish;

    private Handler handler = new Handler();
    private Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        relativeLayout = (RelativeLayout) findViewById(R.id.game_activity_id);
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        width = size.x;
        height = size.y;

        //Remove top action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //Shared preferences of game options pull
        SharedPreferences gameOptionsSP = getApplicationContext().getSharedPreferences("GameOptionsPrefs", Context.MODE_PRIVATE);

        //Dynamic difficulty game from shared preferences
        String chosenDifficulty = gameOptionsSP.getString("difficulty","");

        //Dynamic background image from share preferences
        String chosenBackground = gameOptionsSP.getString("background","");
        if(chosenBackground=="ocean"){
            relativeLayout.setBackgroundResource(R.drawable.ocean_bg_option);
        }
        else{
            relativeLayout.setBackgroundResource(R.drawable.underwater_bg_option);
        }

        //Dynamic PlayerFish image from shared preferences
        String chosenFishColor = gameOptionsSP.getString("fishColor","");
        playerFish = (ImageView) findViewById(R.id.playerFish);
        if(chosenFishColor=="pink"){
            playerFish.setImageResource(R.drawable.pink_eating_fish);
        }
        else{
            playerFish.setImageResource(R.drawable.green_eating_fish);
        }
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //Get touch location on screen and set player fish image to that location
                xPlayerFish=motionEvent.getRawX() - playerFish.getWidth() / 2;
                yPlayerFish=motionEvent.getRawY()- playerFish.getHeight() / 2;

                if(motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    playerFish.setX(xPlayerFish);
                    playerFish.setY(yPlayerFish);
                }
                return true;
            }
        });

        firstOtherFish = (ImageView) findViewById(R.id.firstOtherFish);
        secondOtherFish = (ImageView) findViewById(R.id.secondOtherFish);
        thirdOtherFish = (ImageView) findViewById(R.id.thirdOtherFish);
        forthOtherFish = (ImageView) findViewById(R.id.forthOtherFish);

        //Move to Out of Screen
        firstOtherFish.setX(-80.0f);
        firstOtherFish.setY(-80.0f);
        secondOtherFish.setX(-80.0f);
        secondOtherFish.setY(-80.0f);
        thirdOtherFish.setX(-80.0f);
        thirdOtherFish.setY(-80.0f);
        forthOtherFish.setX(-80.0f);
        forthOtherFish.setY(-80.0f);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos(1);
                        changePos(2);
                        changePos(3);
                        changePos(4);
                    }
                });
            }
        },0 ,20);
    }

    public void changePos(int otherFishNumber){
        //Right to Left
        switch (otherFishNumber){
            case 1:
                xFirstOtherFish -= 10;
                if (firstOtherFish.getX() + firstOtherFish.getWidth() < 0) {
                    xFirstOtherFish = width + 100.0f;
                    yFirstOtherFish = (float)Math.floor(Math.random() * (height - firstOtherFish.getHeight()));
                }
                firstOtherFish.setX(xFirstOtherFish);
                firstOtherFish.setY(yFirstOtherFish);
            case 2:
                xSecondOtherFish -= 10;
                if (secondOtherFish.getX() + secondOtherFish.getWidth() < 0) {
                    xSecondOtherFish = width + 100.0f;
                    ySecondOtherFish = (float)Math.floor(Math.random() * (height - secondOtherFish.getHeight()));
                }
                secondOtherFish.setX(xSecondOtherFish);
                secondOtherFish.setY(ySecondOtherFish);
            case 3:
                xThirdOtherFish -= 10;
                if (thirdOtherFish.getX() + thirdOtherFish.getWidth() < 0) {
                    xThirdOtherFish = width + 100.0f;
                    yThirdOtherFish = (float)Math.floor(Math.random() * (height - thirdOtherFish.getHeight()));
                }
                thirdOtherFish.setX(xThirdOtherFish);
                thirdOtherFish.setY(yThirdOtherFish);
//            case 4:
//                xForthOtherFish -= 10;
//                if (forthOtherFish.getX() + forthOtherFish.getWidth() < 0) {
//                    xForthOtherFish = width + 100.0f;
//                    yForthOtherFish = (float)Math.floor(Math.random() * (height - forthOtherFish.getHeight()));
//                }
//                forthOtherFish.setX(xForthOtherFish);
//                forthOtherFish.setY(yForthOtherFish);
//            case 5:
//                xFifthOtherFish -= 10;
//                if (fifthOtherFish.getX() + fifthOtherFish.getWidth() < 0) {
//                    xFifthOtherFish = width + 100.0f;
//                    yFifthOtherFish = (float)Math.floor(Math.random() * (height - fifthOtherFish.getHeight()));
//                }
//                fifthOtherFish.setX(xFifthOtherFish);
//                fifthOtherFish.setY(yFifthOtherFish);
        }
    }

}