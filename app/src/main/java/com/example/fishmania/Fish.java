package com.example.fishmania;

import java.util.List;

public abstract class Fish {
    private List<Integer> fishValue;
    private GameLevel gameLevel;

    public Fish(Integer value, GameLevel level){
        gameLevel = level;
        int numberOfElements = gameLevel.getNumberOfElements(level);

        for(int i=0; i<numberOfElements; i++){
            fishValue.add(value);
        }
    }

    private void test(int level){
        if(level == 1){
            gameLevel = GameLevel.EASY;
        }
    }

    public List<Integer> getFishValue(){
        return this.fishValue;
    }

    public void setFishValue(int fishValue){

    }

}
