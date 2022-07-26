package com.example.fishmania;

import static com.example.fishmania.GameView.screenRatioX;
import static com.example.fishmania.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class PlayerFish extends Fish {
    public boolean isGoingUp = false;
    public int x, y;
    public Bitmap myFish;
    public int width, height;
    public GameLevel difficulty;

    public PlayerFish(GameLevel level, int screenY,  Resources resources, String chosenFishColor) {
        super(level, resources);
        List<Integer> playerFishValue= new ArrayList<Integer>();
        playerFishValue.add(5);
        setFishValue(playerFishValue);
        difficulty=level;

        if(chosenFishColor=="pink"){
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

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    public boolean checkIfEatableFish(List <Integer> otherFishValue) {
        
        return true;
    }

    public void setFishValue(){
        List <Integer> playerFishValue = getFishValue();
        playerFishValue.set(0, playerFishValue.get(0)+1);
        setFishValue(playerFishValue);
    }

}