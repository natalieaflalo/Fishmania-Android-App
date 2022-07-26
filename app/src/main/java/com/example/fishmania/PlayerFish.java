package com.example.fishmania;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

public class PlayerFish extends Fish {
    int x, y;
    Bitmap myFish;
    int width, height;

    public PlayerFish(GameLevel level, int screenY,  Resources resources) {
        super(level, resources);
        List<Integer> playerFishValue= new ArrayList<Integer>();
        playerFishValue.add(5);
        setFishValue(playerFishValue);

        myFish = BitmapFactory.decodeResource(resources, R.drawable.pink_eating_fish);

        width = myFish.getWidth();
        height = myFish.getHeight();
        width = width/4;
        height = height/4;

        width = (int) width*screenRatioX;
        height = (int) height*screenRatioY;

        myFish = Bitmap.createScaledBitmap(myFish, width, height, false);

        y = screenY / 2;
        x = (int) 64 * screenRatioX;
    }

    public void setFishValue(){
        List <Integer> playerFishValue = getFishValue();
        playerFishValue.set(0, playerFishValue.get(0)+1);
        setFishValue(playerFishValue);
    }

}