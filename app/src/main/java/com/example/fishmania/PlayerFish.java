package com.example.fishmania;

import static com.example.fishmania.GameView.screenRatioX;
import static com.example.fishmania.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.util.List;

public class PlayerFish {
    public boolean isGoingUp = false;
    public int x, y;
    public Bitmap myFish;
    public int width, height;
    public GameLevel difficulty;
    private Integer playerFishValue;

    public PlayerFish(GameLevel level, int screenY,  Resources resources, String chosenFishColor) {
        playerFishValue = 5;
        difficulty = level;

        if(chosenFishColor.matches("pink")){
            myFish = BitmapFactory.decodeResource(resources, R.drawable.pink_eating_fish);
        }
        else{
            myFish = BitmapFactory.decodeResource(resources, R.drawable.green_eating_fish);
        }

        width = myFish.getWidth();
        height = myFish.getHeight();
        width = width/4;
        height = height/4;

        width = (int) (width*screenRatioX);
        height = (int) (height*screenRatioY);

        myFish = Bitmap.createScaledBitmap(myFish, width, height, false);

        y = screenY / 2;
        x = (int)(64 * screenRatioX);
    }

    public Integer getPlayerFishValue() {
        return playerFishValue;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    public void setPlayerFishValue(){
        playerFishValue++;
    }
}