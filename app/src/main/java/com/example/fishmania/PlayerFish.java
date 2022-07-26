package com.example.fishmania;

import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlayerFish extends Fish {

    public PlayerFish(GameLevel level, Resources resources) {
        super(level, resources);
        List<Integer> playerFishValue= new ArrayList<Integer>();
        playerFishValue.add(5);
        setFishValue(playerFishValue);
    }

    public void setFishValue(){
        List <Integer> playerFishValue = getFishValue();
        playerFishValue.set(0, playerFishValue.get(0)+1);
        setFishValue(playerFishValue);
    }
    //public boolean checkIfEatableFish(String value) {return false;}

}