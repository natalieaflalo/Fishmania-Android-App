package com.example.fishmania;

import static com.example.fishmania.ScoreboardActivity.scoreList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;
import androidx.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private GameActivity activity;
    //Background and Sound
    private Background gameBackground;
    private Paint paint;
    private boolean isPlaying, isGameOver = false;
    private SoundPool soundPool;
    private int eatSound;
    //Instances of Animated Fish and Bubble
    private PlayerFish playerFish;
    private OtherFish[] otherFishArray;
    private Bitmap bubble;
    //Screen and Bubble Sizes
    private int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    int bubbleWidth, bubbleHeight;
    //Saved Information
    private int finalScore, numOfFishEaten;
    private SharedPreferences gameOptionsSP;
    //Random Animation Factor
    private Random random;


    public GameView(GameActivity gameActivity, int screenX, int screenY) {
        super(gameActivity);
        //Initiate Screen
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1080f / screenX;
        screenRatioY = 1920f / screenY;
        this.activity = gameActivity;
        //Pull User Preferences
        gameOptionsSP = activity.getSharedPreferences("GameOptionsPrefs", Context.MODE_PRIVATE);
        String chosenBackground = gameOptionsSP.getString("background", "");
        String chosenDifficulty = gameOptionsSP.getString("difficulty", "");
        String chosenFishColor = gameOptionsSP.getString("fishColor", "");
        finalScore = gameOptionsSP.getInt("finalScore", 0);
        numOfFishEaten = gameOptionsSP.getInt("numberOfFish", 0);
        //Initiate Animated Fish and Bubble
        playerFish = new PlayerFish(GameLevel.EASY, screenY, getResources(), chosenFishColor);
        otherFishArray = new OtherFish[5];
        bubble = BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.value_bubble);
        bubbleWidth = bubble.getWidth();
        bubbleHeight = bubble.getHeight();
        bubbleWidth = bubbleWidth/8;
        bubbleHeight = bubbleHeight/8;
        bubbleWidth = (int) (bubbleWidth*screenRatioX);
        bubbleHeight = (int) (bubbleHeight*screenRatioY);
        bubble= Bitmap.createScaledBitmap(bubble, bubbleWidth, bubbleHeight, false);
        for (int i = 0; i < 5; i++) {
            OtherFish otherSingleFish;
            if (i % 2 == 0) {
                otherSingleFish = new OtherFish(FishGroup.LOW, defineGameLevel(chosenDifficulty), getResources());
            } else {
                otherSingleFish = new OtherFish(FishGroup.HIGH, defineGameLevel(chosenDifficulty), getResources());
            }
            otherFishArray[i] = otherSingleFish;
        }
        //Create Background
        gameBackground = new Background(screenX, screenY, getResources(), chosenBackground);
        paint = new Paint();
        //Initiate Random Factor for Animation
        random = new Random();
        //Eating Sound Initiate
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        }
        else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
        eatSound = soundPool.load(activity, R.raw.fish_eating_sound, 1);
    }

    @Override
    public void run() {
        //On Going Actions While Game is Playing
        while (isPlaying) {
            update(); //Change Fish Location and Speed, Check for Collision
            draw(); //Create All Elements on Screen, Check if Game Over
            sleep();
        }
    }

    private void update() {
        if (playerFish.isGoingUp) {
            playerFish.y -= 30 * screenRatioY;
        }
        else {
            playerFish.y += 30 * screenRatioY;
        }
        if (playerFish.y < 0) {
            playerFish.y = 0;
        }
        if (playerFish.y > screenY - playerFish.height) {
            playerFish.y = screenY - playerFish.height;
        }
        for (OtherFish otherSingleFish : otherFishArray) {
            otherSingleFish.x -= otherSingleFish.speed;
            if (otherSingleFish.x + otherSingleFish.fishWidth < 0) {
                int bound = (int) (25 * screenRatioX);
                otherSingleFish.speed = random.nextInt(bound);
                if (otherSingleFish.speed < 10 * screenRatioX) {
                    otherSingleFish.speed = (int) (10 * screenRatioX);
                }
                otherSingleFish.x = screenX;
                otherSingleFish.y = random.nextInt(screenY - otherSingleFish.fishHeight);
            }
            //Check for Collision between User Fish and Other Fish
            if (Rect.intersects(otherSingleFish.getCollisionShape(), playerFish.getCollisionShape())) {
                if (otherSingleFish.fishGroup==FishGroup.LOW) {
                    soundPool.play(eatSound, 1, 1, 0, 0, 1);
                    numOfFishEaten++;
                    otherSingleFish.x=screenX;
                    otherSingleFish.y = random.nextInt(screenY - otherSingleFish.fishHeight);
                    otherSingleFish.updateValue(playerFish.getPlayerFishValue());
                    //Raise Score and User Fish Value after 3 Eating Instances
                    if (numOfFishEaten % 3 == 0) {
                        playerFish.setPlayerFishValue();
                        for (OtherFish changeValueOtherFish : otherFishArray) {
                            changeValueOtherFish.updateValue(playerFish.getPlayerFishValue());
                        }
                        finalScore++;
                    }
                }
                else {
                    isGameOver = true;
                }
            }
        }
    }

    private void draw() {
        //Create Background
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(gameBackground.background, gameBackground.x, gameBackground.y, paint);
            //Create Other Fish with Their Values
            for (OtherFish otherSingeFish : otherFishArray) {
                canvas.drawBitmap(otherSingeFish.getFish(), otherSingeFish.x, otherSingeFish.y, paint);
                canvas.drawBitmap(bubble, otherSingeFish.x-20, otherSingeFish.y-30, paint);
                paint.setTextSize(50);
                paint.setTextScaleX(1.f);
                paint.setAlpha(0);
                paint.setAntiAlias(true);
                paint.setColor(Color.BLACK);
                canvas.drawText(otherSingeFish.setTextView(),otherSingeFish.x+5,otherSingeFish.y+10,paint);
            }
            //Create User's Fish with it's Value
            canvas.drawBitmap(playerFish.myFish, playerFish.x, playerFish.y, paint);
            canvas.drawBitmap(bubble, playerFish.x-20, playerFish.y-30, paint);
            paint.setTextSize(50);
            paint.setTextScaleX(1.f);
            paint.setAlpha(0);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            canvas.drawText(playerFish.getPlayerFishValue().toString(),playerFish.x+5,playerFish.y+10,paint);
            //Finish Game if Bad Collision was Detected in Update Method
            if (isGameOver) {
                isPlaying = false;
                getHolder().unlockCanvasAndPost(canvas);
                //Save Game Score Record, Send User to Scoreboard Activity
                SharedPreferences.Editor editor = gameOptionsSP.edit();
                editor.putInt("numberOfFish", numOfFishEaten);
                editor.putInt("finalScore", finalScore);
                editor.commit();
                createNewScoreRecord();
                activity.startActivity(new Intent(activity, ScoreboardActivity.class));
                activity.finish();
                return;
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void createNewScoreRecord(){
        //Pull Finished Game Information
        SharedPreferences finalRecordSP = activity.getSharedPreferences("GameOptionsPrefs", Context.MODE_PRIVATE);
        String difficultyRecord = finalRecordSP.getString("difficulty","");
        int numOfFishRecord = finalRecordSP.getInt("numberOfFish", 0);
        int finalScoreRecord = finalRecordSP.getInt("finalScore", 0);
        //Add New Score Record to Record List
        ScoreRecord newRecord = new ScoreRecord(LocalDateTime.now(),numOfFishRecord,finalScoreRecord,difficultyRecord);
        scoreList.add(newRecord);
        //Initiate Score Values for New Game
        SharedPreferences.Editor editor = gameOptionsSP.edit();
        editor.putInt("numberOfFish", 0);
        editor.putInt("finalScore", 0);
        editor.commit();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Animate User's Fish
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                playerFish.isGoingUp = true;
                break;
            case MotionEvent.ACTION_UP:
                playerFish.isGoingUp = false;
                break;
        }
        return true;
    }


    @Nullable
    private GameLevel defineGameLevel(String gameLevelStr) {
        //Convert String to GameLevel Variable
        if (gameLevelStr.matches("easy")) {
            return GameLevel.EASY;
        } else if (gameLevelStr.matches("medium")) {
            return GameLevel.MEDIUM;
        } else if (gameLevelStr.matches("hard")) {
            return GameLevel.HARD;
        }
        return null;
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}