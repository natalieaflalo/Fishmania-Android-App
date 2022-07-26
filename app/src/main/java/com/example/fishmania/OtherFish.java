package com.example.fishmania;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OtherFish extends Fish{
    private FishGroup fishGroup;
    private int  lowBtmBoundMultiply = 1;
    private int lowTopBoundMultiply = 2;
    private int x, y, fishHeight, fishWidth;
    private Bitmap fish1, fish2, fish3, fish4, fish5;


    public OtherFish(FishGroup fishGroupValue, GameLevel level, Resources resources){
        super(level, resources);
        fish1 = BitmapFactory.decodeResource(resources, R.drawable.other_blue_fish);
        fish2 = BitmapFactory.decodeResource(resources, R.drawable.other_blue_fish);
        fish3 = BitmapFactory.decodeResource(resources, R.drawable.other_blue_fish);
        fish4 = BitmapFactory.decodeResource(resources, R.drawable.other_blue_fish);
        fish5 = BitmapFactory.decodeResource(resources, R.drawable.other_blue_fish);

        fishWidth = fish1.getWidth();
        fishHeight = fish1.getHeight();
        fishWidth/=6;
        fishHeight/=6;
        //fishWidth*=(int)screenRatioX;

        fishGroup=fishGroupValue;
        updateValue(5);
    }

    public void setTextView(){
        CharSequence text = "";
        switch (getGameLevel()){
            case EASY:
                text = new StringBuffer(getFishValue().get(0));
                break;
            case MEDIUM:
                text= new StringBuffer(getFishValue().get(0)+" + "+getFishValue().get(1));
                break;
            case HARD:
                text= new StringBuffer(getFishValue().get(0)+" * "+getFishValue().get(1));
        }
        //update text value
    }

    public void updateValue ( int playerFishValue){
        List<Integer> valuesToSet = new ArrayList<Integer>();
        Random rand = new Random();
        if (fishGroup==FishGroup.HIGH){
            switch (getGameLevel()){
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
            switch (getGameLevel()){
                case EASY:
                    valuesToSet.add(rand.nextInt(playerFishValue) + playerFishValue-4);
                break;
                case MEDIUM:
                    int randomSum= rand.nextInt(playerFishValue) + playerFishValue-4;
                    valuesToSet.add(rand.nextInt(randomSum - 1) + 1);
                    valuesToSet.add(randomSum - valuesToSet.get(0));
                break;
                case HARD:
                    updateLowTopBoundMultiply(playerFishValue);
                    valuesToSet.add(rand.nextInt(lowTopBoundMultiply) + lowBtmBoundMultiply);
                    valuesToSet.add(rand.nextInt(lowTopBoundMultiply) + lowBtmBoundMultiply);
                    break;
            }
            setFishValue(valuesToSet);
        }
    }

    private void updateLowTopBoundMultiply(int playerFishValue){
        if(playerFishValue >= Math.pow(lowTopBoundMultiply+1,2)){
            lowTopBoundMultiply++;
        }
    }
}
