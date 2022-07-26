package com.example.fishmania;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private Background gameBackground;
    private int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private GameActivity activity;
    private PlayerFish playerFish;
    private OtherFish[] otherFishArray;
    private Random random;
    private Bitmap bubble;
    private int finalScore, numOfFishEaten;
    SharedPreferences gameOptionsSP;

    public GameView(GameActivity gameActivity, int screenX, int screenY) {
        super(gameActivity);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1080f / screenX;
        screenRatioY = 1920f / screenY;
        this.activity = gameActivity;
        gameOptionsSP = activity.getSharedPreferences("GameOptionsPrefs", Context.MODE_PRIVATE);
        String chosenBackground = gameOptionsSP.getString("background", "");
        String chosenDifficulty = gameOptionsSP.getString("difficulty", "");
        String chosenFishColor = gameOptionsSP.getString("fishColor", "");
        finalScore = gameOptionsSP.getInt("finalScore", 0);
        numOfFishEaten = gameOptionsSP.getInt("numberOfFish", 0);

        playerFish = new PlayerFish(GameLevel.EASY, screenY, getResources(), chosenFishColor);
        otherFishArray = new OtherFish[5];
        bubble = BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.value_bubble);
        int bubbleWidth = bubble.getWidth();
        int bubbleHeight = bubble.getHeight();
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

        gameBackground = new Background(screenX, screenY, getResources(), chosenBackground);
        paint = new Paint();
        random = new Random();

    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        if (playerFish.isGoingUp) {
            playerFish.y -= 30 * screenRatioY;
        } else {
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
                int bound = (int) (30 * screenRatioX);
                otherSingleFish.speed = random.nextInt(bound);

                if (otherSingleFish.speed < 10 * screenRatioX) {
                    otherSingleFish.speed = (int) (10 * screenRatioX);
                }
                otherSingleFish.x = screenX;
                otherSingleFish.y = random.nextInt(screenY - otherSingleFish.fishHeight);

            }

            if (Rect.intersects(otherSingleFish.getCollisionShape(), playerFish.getCollisionShape())) {
                if (playerFish.checkIfEatableFish(otherSingleFish.getOtherFishValueList())) {
                    numOfFishEaten++;
                    otherSingleFish.x=screenX;
                    otherSingleFish.updateValue(playerFish.getPlayerFishValue());
                    if (numOfFishEaten % 3 == 0) {
                        playerFish.setPlayerFishValue();
                        for (OtherFish changeValueOtherFish : otherFishArray) {
                            changeValueOtherFish.updateValue(playerFish.getPlayerFishValue());
                        }
                        finalScore++;
                    }

                } else {
                    isGameOver = true;
                }
            }
        }
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(gameBackground.background, gameBackground.x, gameBackground.y, paint);

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
            canvas.drawBitmap(playerFish.myFish, playerFish.x, playerFish.y, paint);
            canvas.drawBitmap(bubble, playerFish.x-20, playerFish.y-30, paint);
            paint.setTextSize(50);
            paint.setTextScaleX(1.f);
            paint.setAlpha(0);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            canvas.drawText(playerFish.getPlayerFishValue().toString(),playerFish.x+5,playerFish.y+10,paint);

            if (isGameOver) {
                isPlaying = false;
                getHolder().unlockCanvasAndPost(canvas);
                SharedPreferences.Editor editor = gameOptionsSP.edit();
                editor.putInt("numberOfFish", numOfFishEaten);
                editor.putInt("finalScore", finalScore);
                editor.commit();
                activity.startActivity(new Intent(activity, ScoreboardActivity.class));
                activity.finish();
                return;
            }

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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