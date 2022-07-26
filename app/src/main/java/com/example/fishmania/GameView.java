package com.example.fishmania;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable{

    private Thread thread;
    private boolean isPlaying;
    private Background gameBackground;
    private int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private GameActivity activity;
    private PlayerFish playerFish;

    public GameView(GameActivity gameActivity, int screenX, int screenY){
        super(gameActivity);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1080f/screenX;
        screenRatioY = 1920f/screenY;
        this.activity = gameActivity;
        SharedPreferences gameOptionsSP = activity.getSharedPreferences("GameOptionsPrefs", Context.MODE_PRIVATE);
        String chosenBackground = gameOptionsSP.getString("background","");
        String chosenDifficulty = gameOptionsSP.getString("difficulty","");
        String chosenFishColor = gameOptionsSP.getString("fishColor","");

        playerFish = new PlayerFish(GameLevel.EASY, screenY, getResources());

        gameBackground = new Background(screenX, screenY, getResources(), chosenBackground);
        paint = new Paint();

    }

    @Override
    public void run() {
        while (isPlaying){
            update();
            draw();
            sleep();
        }
    }

    private void update (){
        if(playerFish.isGoingUp){
            playerFish.y -= 30 * screenRatioY;
        }
        else{
            playerFish.y += 30 * screenRatioY;
        }

        if(playerFish.y < 0){
            playerFish.y = 0;
        }

        if(playerFish.y > screenY - playerFish.height){
            playerFish.y = screenY - playerFish.height;
        }
    }

    private void draw (){
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(gameBackground.background, gameBackground.x, gameBackground.y, paint);

            canvas.drawBitmap(playerFish.myFish, playerFish.x, playerFish.y, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void sleep (){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume(){
        isPlaying = true;
        thread = new Thread( this);
        thread.start();
    }

    public void pause(){
        try{
            isPlaying = false;
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private GameLevel defineGameLevel(String gameLevelStr){
        if(gameLevelStr == "easy"){
            return GameLevel.EASY;
        }
        else if(gameLevelStr == "medium"){
            return GameLevel.MEDIUM;
        }
        else if(gameLevelStr == "hard"){
            return GameLevel.HARD;
        }
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX() < screenX/2){
                    playerFish.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                playerFish.isGoingUp = false;
                break;
        }
        return true;
    }
}
