package com.example.fishmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OtherFish extends Fish{
    private FishGroup fishGroup;


    public OtherFish(FishGroup fishGroupValue, GameLevel level){
        super(1, level);
        fishGroup=fishGroupValue;
        updateValue(5,level);
    }
    private int createRandomFishValues (int playerFishValue) {
        Random rand = new Random();
        switch (fishGroup) {
            case HIGH:
                return (rand.nextInt(playerFishValue + 6) + playerFishValue + 1);
            case LOW:
                return (rand.nextInt(playerFishValue) + 1);
        }
        return 0;
    }

    public void updateValue ( int playerFishValue, GameLevel gameLevel){
        List<Integer> valuesToSet = new ArrayList<Integer>();
        switch (gameLevel) {
            case EASY:
                valuesToSet.add(createRandomFishValues(playerFishValue));
                setFishValue(valuesToSet,1);
                break;
            case MEDIUM:
            case HARD:
                valuesToSet.add(createRandomFishValues(playerFishValue));
                valuesToSet.add(createRandomFishValues(playerFishValue));
                setFishValue(valuesToSet, 2);
                break;
        }
    }
}
