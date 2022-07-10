package com.example.fishmania;

import java.util.Random;

public class OtherFish extends Fish{
    private FishGroup fishGroup;


    public OtherFish(FishGroup fishGroupValue, GameLevel level){
        super(1, level);
        fishGroup=fishGroupValue;
        updateValue(5);
    }
    private void createRandomFishValues (int playerFishValue) {
        Random rand = new Random();
        switch (fishGroup) {
            case HIGH:
                setFishValue(rand.nextInt(playerFishValue + 6) + playerFishValue + 1);
                break;
            case LOW:
                setFishValue(rand.nextInt(playerFishValue) + 1);
                break;
        }
    }

    public void updateValue ( int playerFishValue){

        switch (gameLevel) {
            case EASY:
                setFishValue(playerFishValue,1);
                break;
            case MEDIUM:
            case HARD:
                setFishValue(playerFishValue, 2);
                break;
        }
    }
}
