package com.example.fishmania;

import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public abstract class Fish {
    private List<Integer> fishValue;
    private GameLevel gameLevel;

    public Fish(GameLevel level, Resources resources){
        gameLevel = level;
    }

    public GameLevel getGameLevel() {
        return gameLevel;
    }

    public List<Integer> getFishValue(){
        return this.fishValue;
    }

    public void setFishValue(List<Integer> valuesToSet){
        fishValue = valuesToSet;
        CharSequence text = "";
        switch (gameLevel){
            case EASY:
                text = new StringBuffer(fishValue.get(0));
                break;
            case MEDIUM:
                text= new StringBuffer(fishValue.get(0)+" + "+fishValue.get(1));
                break;
            case HARD:
                text= new StringBuffer(fishValue.get(0)+" * "+fishValue.get(1));
        }
        //update text value
    }

}
