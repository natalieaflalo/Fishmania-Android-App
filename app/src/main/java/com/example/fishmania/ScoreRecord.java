package com.example.fishmania;

import java.time.LocalDateTime;

public class ScoreRecord {
    private LocalDateTime  date;
    private int num_of_fish;
    private int final_score;
    private String game_level;

    public ScoreRecord(int num_of_fish, int final_score, String game_level) {
        this.date = LocalDateTime.now();;
        this.num_of_fish = num_of_fish;
        this.final_score = final_score;
        this.game_level = game_level;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime  date) {
        this.date = date;
    }

    public int getNum_of_fish() {
        return num_of_fish;
    }

    public void setNum_of_fish(int num_of_fish) {
        this.num_of_fish = num_of_fish;
    }

    public int getFinal_score() {
        return final_score;
    }

    public void setFinal_score(int final_score) {
        this.final_score = final_score;
    }

    public String getGame_level() {
        return game_level;
    }

    public void setGame_level(String game_level) {
        this.game_level = game_level;
    }


}
