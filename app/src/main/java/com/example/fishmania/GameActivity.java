package com.example.fishmania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.Timer;

import java.time.LocalDateTime;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {

    //private Fish playerFishInstance = new PlayerFish();

    private RelativeLayout relativeLayout;
    private int height, width;
    //Scoreboard records
    private int numOfFishEaten, finalScore;
    private LocalDateTime dateTime;
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
    private SharedPreferences gameOptionsSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        relativeLayout = (RelativeLayout) findViewById(R.id.game_activity_id);
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        //Remove top action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //Shared preferences of game options pull
        gameOptionsSP = getApplicationContext().getSharedPreferences("GameOptionsPrefs", Context.MODE_PRIVATE);

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
        playerFishValue = (TextView) findViewById(R.id.playerFishValue);
        if(chosenFishColor=="pink"){
            playerFish.setImageResource(R.drawable.pink_eating_fish);
        }
        else{
            playerFish.setImageResource(R.drawable.green_eating_fish);
        }

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

        playerFish.setId((Integer) 0);
        firstOtherFish.setId((Integer) 1);
        secondOtherFish.setId((Integer) 2);
        thirdOtherFish.setId((Integer) 3);
        fourthOtherFish.setId((Integer) 4);
        fifthOtherFish.setId((Integer) 5);

        playerFish.setOnTouchListener(this);
        firstOtherFish.setOnTouchListener(this);
        secondOtherFish.setOnTouchListener(this);
        thirdOtherFish.setOnTouchListener(this);
        fourthOtherFish.setOnTouchListener(this);
        fifthOtherFish.setOnTouchListener(this);


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
                handler.post(() -> {
                    for (int i = 0; i < 6; i++) {
                        otherFishChangePosition(i);

                    }
                });
            }
        },0 ,10);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean canEatFish;
        switch(v.getId()){
            case 0:  //Relative Layout
                //Player Fish Movement by Touch
                xPlayerFish=event.getRawX() - playerFish.getWidth() / 2;
                yPlayerFish=event.getRawY()- playerFish.getHeight() / 2;
                if(event.getAction() == MotionEvent.ACTION_MOVE) {
                    playerFish.setX(xPlayerFish);
                    playerFish.setY(yPlayerFish);
                    playerFishValue.setX(xPlayerFish + 450);
                    playerFishValue.setY(yPlayerFish + (playerFishValue.getHeight() - playerFishValue.getHeight() / 2));
                }
                break;
            case 1: //First Other Fish
                canEatFish= checkIfEatableFish(firstOtherFishValue.getText().toString());
                //canEatFish=false;
                if(canEatFish){
                    numOfFishEaten++;
                    firstOtherFish.setX(-80.0f);
                    firstOtherFish.setY(-80.0f);
                    firstOtherFishValue.setX(-80.0f);
                    firstOtherFishValue.setY(-80.0f);
                    otherFishChangePosition(1);
                    if(numOfFishEaten%4==0){
                        finalScore++;
                    }
                }
                else{
                    CharSequence text = new StringBuffer("Game Finished with Score: "+finalScore);
                    Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GameActivity.this,ScoreboardActivity.class);
                    startActivity(intent);
                }
                break;
            case 2:  //Second Other Fish
                canEatFish= checkIfEatableFish(secondOtherFishValue.getText().toString());
                if(canEatFish==true){
                    numOfFishEaten++;
                    secondOtherFish.setX(-80.0f);
                    secondOtherFish.setY(-80.0f);
                    secondOtherFishValue.setX(-80.0f);
                    secondOtherFishValue.setY(-80.0f);
                    otherFishChangePosition(2);
                    if(numOfFishEaten%4==0){
                        finalScore++;
                    }
                }
                else {
                    CharSequence text = new StringBuffer("Game Finished with Score: "+finalScore);
                    Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GameActivity.this,ScoreboardActivity.class);
                    startActivity(intent);
                }
                break;
            case 3:
                canEatFish= checkIfEatableFish(thirdOtherFishValue.getText().toString());
                if(canEatFish==true){
                    numOfFishEaten++;
                    thirdOtherFish.setX(-80.0f);
                    thirdOtherFish.setY(-80.0f);
                    thirdOtherFishValue.setX(-80.0f);
                    thirdOtherFishValue.setY(-80.0f);
                    otherFishChangePosition(3);
                    if(numOfFishEaten%4==0){
                        finalScore++;
                    }
                }
                else {
                    CharSequence text = new StringBuffer("Game Finished with Score: "+finalScore);
                    Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GameActivity.this,ScoreboardActivity.class);
                    startActivity(intent);
                }
                break;
            case 4:
                canEatFish= checkIfEatableFish(fourthOtherFishValue.getText().toString());
                if(canEatFish==true){
                    numOfFishEaten++;
                    fourthOtherFish.setX(-80.0f);
                    fourthOtherFish.setY(-80.0f);
                    fourthOtherFishValue.setX(-80.0f);
                    fourthOtherFishValue.setY(-80.0f);
                    otherFishChangePosition(4);
                    if(numOfFishEaten%4==0){
                        finalScore++;
                    }
                }
                else {
                    CharSequence text = new StringBuffer("Game Finished with Score: "+finalScore);
                    Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GameActivity.this,ScoreboardActivity.class);
                    startActivity(intent);
                }
                break;
            case 5:
                canEatFish= false;
                //canEatFish= checkIfEatableFish(fifthOtherFishValue.getText().toString());
                if(canEatFish==true){
                    numOfFishEaten++;
                    fifthOtherFish.setX(-80.0f);
                    fifthOtherFish.setY(-80.0f);
                    fifthOtherFishValue.setX(-80.0f);
                    fifthOtherFishValue.setY(-80.0f);
                    otherFishChangePosition(5);
                    if(numOfFishEaten%4==0){
                        finalScore++;
                    }
                }
                else {
                    CharSequence text = new StringBuffer("Game Finished with Score: "+finalScore);
                    Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GameActivity.this,ScoreboardActivity.class);
                    startActivity(intent);
                }
                break;
        }
        SharedPreferences.Editor gameScoreEditor = gameOptionsSP.edit();
        gameScoreEditor.putInt("numberOfFish",numOfFishEaten);
        gameScoreEditor.putInt("finalScore",finalScore);
        gameScoreEditor.commit();
        return true;
    }
    public boolean checkIfEatableFish(String value) {
    return true;
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