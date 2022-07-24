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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private ImageView playerFish, firstOtherFish, secondOtherFish, thirdOtherFish, fourthOtherFish, fifthOtherFish;
    //TextView for Fish Values
    private TextView playerFishValue, firstOtherFishValue, secondOtherFishValue, thirdOtherFishValue, fourthOtherFishValue, fifthOtherFishValue;
    //Positions of Fish
    private float xPlayerFish, yPlayerFish;
    private float xFirstOtherFish, yFirstOtherFish;
    private float xSecondOtherFish, ySecondOtherFish;
    private float xThirdOtherFish, yThirdOtherFish;
    private float xFourthOtherFish, yFourthOtherFish;
    private float xFifthOtherFish, yFifthOtherFish;

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
        //Player Fish Movement by Touch
        playerFishValue = (TextView) findViewById(R.id.playerFishValue);
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //Get touch location on screen and set player fish image to that location
                xPlayerFish=motionEvent.getRawX() - playerFish.getWidth() / 2;
                yPlayerFish=motionEvent.getRawY()- playerFish.getHeight() / 2;

                if(motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    playerFish.setX(xPlayerFish);
                    playerFish.setY(yPlayerFish);
                    playerFishValue.setX(xPlayerFish + 450);
                    playerFishValue.setY(yPlayerFish + (playerFishValue.getHeight() - playerFishValue.getHeight() / 2));
                }
                return true;
            }
        });
        //ImageView of otherFish find
        firstOtherFish = (ImageView) findViewById(R.id.firstOtherFish);
        secondOtherFish = (ImageView) findViewById(R.id.secondOtherFish);
        thirdOtherFish = (ImageView) findViewById(R.id.thirdOtherFish);
        fourthOtherFish = (ImageView) findViewById(R.id.fourthOtherFish);
        fifthOtherFish = (ImageView) findViewById(R.id.fifthOtherFish);
        //TextView of otherFish values find
        firstOtherFishValue = (TextView) findViewById(R.id.firstOtherFishValue);
        secondOtherFishValue = (TextView) findViewById(R.id.secondOtherFishValue);
        thirdOtherFishValue = (TextView) findViewById(R.id.thirdOtherFishValue);
        fourthOtherFishValue = (TextView) findViewById(R.id.fourthOtherFishValue);
        fifthOtherFishValue = (TextView) findViewById(R.id.fifthOtherFishValue);

        //Locate images outside of screen first
        firstOtherFish.setX(-80.0f);
        firstOtherFish.setY(-80.0f);
        secondOtherFish.setX(-80.0f);
        secondOtherFish.setY(-80.0f);
        thirdOtherFish.setX(-80.0f);
        thirdOtherFish.setY(-80.0f);
        fourthOtherFish.setX(-80.0f);
        fourthOtherFish.setY(-80.0f);
        fifthOtherFish.setX(-80.0f);
        fifthOtherFish.setY(-80.0f);

        //Locate values outside of screen first
        firstOtherFishValue.setX(-80.0f);
        firstOtherFishValue.setY(-80.0f);
        secondOtherFishValue.setX(-80.0f);
        secondOtherFishValue.setY(-80.0f);
        thirdOtherFishValue.setX(-80.0f);
        thirdOtherFishValue.setY(-80.0f);
        fourthOtherFishValue.setX(-80.0f);
        fourthOtherFishValue.setY(-80.0f);
        fifthOtherFishValue.setX(-80.0f);
        fifthOtherFishValue.setY(-80.0f);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 6; i++) {
                            otherFishChangePosition(i);
                        }
                    }
                });
            }
        },0 ,10);
    }

    public void otherFishChangePosition(int otherFishNumber){
        //Right to Left
        switch (otherFishNumber){
            case 1:
                    xFirstOtherFish -= 9;
                    if (firstOtherFish.getX() + firstOtherFish.getWidth() < 0) {
                        xFirstOtherFish = width + 100.0f;
                        yFirstOtherFish = (float)Math.floor(Math.random() * (height - firstOtherFish.getHeight()));
                    }
                    firstOtherFish.setX(xFirstOtherFish);
                    firstOtherFish.setY(yFirstOtherFish);
                    firstOtherFishValue.setX(xFirstOtherFish);
                    firstOtherFishValue.setY(yFirstOtherFish);
                break;
            case 2:
                    xSecondOtherFish -= 5;
                    if (secondOtherFish.getX() + secondOtherFish.getWidth() < 0) {
                        xSecondOtherFish = width + 100.0f;
                        ySecondOtherFish = (float)Math.floor(Math.random() * (height - secondOtherFish.getHeight()));
                    }
                    secondOtherFish.setX(xSecondOtherFish);
                    secondOtherFish.setY(ySecondOtherFish);
                    secondOtherFishValue.setX(xSecondOtherFish);
                    secondOtherFishValue.setY(ySecondOtherFish);
                break;
            case 3:
                    xThirdOtherFish -= 6;
                    if (thirdOtherFish.getX() + thirdOtherFish.getWidth() < 0) {
                        xThirdOtherFish = width + 100.0f;
                        yThirdOtherFish = (float)Math.floor(Math.random() * (height - thirdOtherFish.getHeight()));
                    }
                    thirdOtherFish.setX(xThirdOtherFish);
                    thirdOtherFish.setY(yThirdOtherFish);
                    thirdOtherFishValue.setX(xThirdOtherFish);
                    thirdOtherFishValue.setY(yThirdOtherFish);
                break;
            case 4:
                    xFourthOtherFish -= 7;
                    if (fourthOtherFish.getX() + fourthOtherFish.getWidth() < 0) {
                        xFourthOtherFish = width + 100.0f;
                        yFourthOtherFish = (float)Math.floor(Math.random() * (height - fourthOtherFish.getHeight()));
                    }
                    fourthOtherFish.setX(xFourthOtherFish);
                    fourthOtherFish.setY(yFourthOtherFish);
                    fourthOtherFishValue.setX(xFourthOtherFish);
                    fourthOtherFishValue.setY(yFourthOtherFish);
                break;
            case 5:
                    xFifthOtherFish -= 8;
                    if (fifthOtherFish.getX() + fifthOtherFish.getWidth() < 0) {
                        xFifthOtherFish = width + 100.0f;
                        yFifthOtherFish = (float)Math.floor(Math.random() * (height - fifthOtherFish.getHeight()));
                    }
                    fifthOtherFish.setX(xFifthOtherFish);
                    fifthOtherFish.setY(yFifthOtherFish);
                    fifthOtherFishValue.setX(xFifthOtherFish);
                    fifthOtherFishValue.setY(yFifthOtherFish);
                break;
        }
    }

}