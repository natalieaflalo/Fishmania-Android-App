package com.example.fishmania;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public abstract class Fish {
    private List<Integer> fishValue;
    private GameLevel gameLevel;
    private ImageView fishImage;
    private TextView displayedValue;

    public Fish(GameLevel level, ImageView image, TextView textValue){
        gameLevel = level;
        fishImage = image;
        displayedValue = textValue;
    }

    public ImageView getFishImage() {
        return fishImage;
    }

    public void setFishImage(ImageView fishImage) {

        this.fishImage = fishImage;
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
        displayedValue.setText(text);
    }

    public TextView getDisplayedValue() {
        return displayedValue;
    }

    public void setDisplayedValue(CharSequence displayedValue) {
        this.displayedValue.setText(displayedValue);
    }
}
