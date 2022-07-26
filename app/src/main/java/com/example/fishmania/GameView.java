package com.example.fishmania;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable{

    private Thread thread;
    private boolean isPlaying;
    private Background gameBackground;
    private int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private Paint paint;

    public GameView(Context context, int screenX, int screenY, String chosenBackground, String fishColor, String level){
        super(context);
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

    }

    private void draw (){
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(gameBackground.background, gameBackground.x, gameBackground.y, paint);
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
}
