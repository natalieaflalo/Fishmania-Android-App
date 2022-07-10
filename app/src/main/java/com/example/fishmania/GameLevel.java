package com.example.fishmania;

public enum GameLevel {
    EASY(1), MEDIUM(2), HARD(2);

    private int numericValue;

    private GameLevel(int value){
        this.numericValue = value;
    }

    public int getNumberOfElements(GameLevel gameLevel)
    {
        return gameLevel.numericValue;
    }
}
