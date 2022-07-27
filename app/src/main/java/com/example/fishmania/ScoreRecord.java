package com.example.fishmania;

import java.time.LocalDateTime;

public class ScoreRecord {
    private LocalDateTime  date;
    private int num_of_fish;
    private int final_score;
    private String game_level;

    public ScoreRecord(LocalDateTime date_time, int num_of_fish, int final_score, String game_level) {
        this.date = date_time;
        this.num_of_fish = num_of_fish;
        this.final_score = final_score;
        this.game_level = game_level;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getNum_of_fish() {
        return num_of_fish;
    }

    public int getFinal_score() {
        return final_score;
    }

    public String getGame_level() {return game_level;}


}
