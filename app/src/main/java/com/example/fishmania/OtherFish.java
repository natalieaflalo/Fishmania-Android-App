package com.example.fishmania;

import static com.example.fishmania.GameView.screenRatioX;
import static com.example.fishmania.GameView.screenRatioY;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OtherFish{
    //Other Fish Information
    private Bitmap fish1, fish2, fish3, fish4, fish5;
    public int x, y, fishHeight, fishWidth;
    public int speed = 15;
    public FishGroup fishGroup;
    private GameLevel difficulty;
    //List of All Other Fish
    public List<Integer> otherFishValueList;
    //Bounds for Random Factor in Hard Difficulty
    private int  lowBtmBoundMultiply = 1;
    private int lowTopBoundMultiply = 2;

    public OtherFish(FishGroup fishGroupValue, GameLevel level, Resources resources){
        //Initiate Other Fish
        fish1 = BitmapFactory.decodeResource(resources, R.drawable.other_blue_fish);
        fish2 = BitmapFactory.decodeResource(resources, R.drawable.other_blue_fish);
        fish3 = BitmapFactory.decodeResource(resources, R.drawable.other_blue_fish);
        fish4 = BitmapFactory.decodeResource(resources, R.drawable.other_blue_fish);
        fish5 = BitmapFactory.decodeResource(resources, R.drawable.other_blue_fish);


        //Scale Fish Size and Location
        fishWidth = fish1.getWidth();
        fishHeight = fish1.getHeight();
        fishWidth/=8;
        fishHeight/=8;
        fishWidth = (int) (fishWidth * screenRatioX);
        fishHeight = (int) (fishHeight * screenRatioY);
        fish1 = Bitmap.createScaledBitmap(fish1, fishWidth, fishHeight, false);
        fish2 = Bitmap.createScaledBitmap(fish2, fishWidth, fishHeight, false);
        fish3 = Bitmap.createScaledBitmap(fish3, fishWidth, fishHeight, false);
        fish4 = Bitmap.createScaledBitmap(fish4, fishWidth, fishHeight, false);
        fish5 = Bitmap.createScaledBitmap(fish5, fishWidth, fishHeight, false);
        y = -fishHeight;
        //Initiate Fish Information
        difficulty = level;
        fishGroup=fishGroupValue;
        updateValue(5);
    }

    public List<Integer> getOtherFishValueList() {
        return otherFishValueList;
    }

    public void setOtherFishValueList(List<Integer> otherFishValueList) {
        this.otherFishValueList = otherFishValueList;
    }

    //Animation Functions
    public Bitmap getFish () {
        switch (fishGroup) {
            case LOW:
                return fish1;
            case HIGH:
                return fish2;
        }
        return null;
    }

    public Rect getCollisionShape () {
        return new Rect(x, y, x + fishWidth, y + fishHeight);
    }

    //Text Value Functions
    public String setTextView(){
        String text = "";
        switch (difficulty){
            case EASY:
                text = text+String.valueOf(getOtherFishValueList().get(0));
                break;
            case MEDIUM:
                text= text+String.valueOf(getOtherFishValueList().get(0)) + " + " + String.valueOf(getOtherFishValueList().get(1));
                break;
            case HARD:
                text= text+String.valueOf(getOtherFishValueList().get(0)) + " * " + String.valueOf(getOtherFishValueList().get(1));
                break;
        }
        return text;
    }

    public void updateValue ( int playerFishValue){
        List<Integer> valuesToSet = new ArrayList<Integer>();
        Random rand = new Random();
        //Update Fish Value by Game Difficulty and if They can be Eaten
        if (fishGroup==FishGroup.HIGH){
            switch (difficulty){
                case EASY:
                    valuesToSet.add(rand.nextInt(playerFishValue + 6) + playerFishValue + 1);
                break;
                case MEDIUM:
                    int randomSum= rand.nextInt(playerFishValue + 6) + playerFishValue + 1;
                    valuesToSet.add(rand.nextInt(randomSum) + 1);
                    valuesToSet.add(randomSum - valuesToSet.get(0));
                    break;
                case HARD:
                    updateLowTopBoundMultiply(playerFishValue);
                    valuesToSet.add(rand.nextInt(lowTopBoundMultiply + 8) + lowTopBoundMultiply + 1);
                    valuesToSet.add(rand.nextInt(lowTopBoundMultiply + 8) + lowTopBoundMultiply + 1);
                    break;
            }
        }
        else {
            switch (difficulty){
                case EASY:
                    valuesToSet.add(rand.nextInt(playerFishValue) + playerFishValue-4);
                    break;
                case MEDIUM:
                    int randomSum= rand.nextInt(playerFishValue-1) + playerFishValue-4;
                    valuesToSet.add(rand.nextInt(randomSum)+1);
                    valuesToSet.add(randomSum - valuesToSet.get(0));
                    break;
                case HARD:
                    updateLowTopBoundMultiply(playerFishValue);
                    valuesToSet.add(rand.nextInt(lowTopBoundMultiply) + lowBtmBoundMultiply);
                    valuesToSet.add(rand.nextInt(lowTopBoundMultiply) + lowBtmBoundMultiply);
                    break;
            }
        }
        setOtherFishValueList(valuesToSet);
    }

    private void updateLowTopBoundMultiply(int playerFishValue){
        if(playerFishValue >= Math.pow(lowTopBoundMultiply+1,2)){
            lowTopBoundMultiply++;
        }
    }
}
