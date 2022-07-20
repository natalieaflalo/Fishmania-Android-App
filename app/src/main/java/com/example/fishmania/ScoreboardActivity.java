package com.example.fishmania;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ScoreboardActivity extends OptionMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        ListView scoreboardList = (ListView) findViewById(R.id.scoreboard_list);

        ScoreRecord first = new ScoreRecord(5,5,"EASY");
        ScoreRecord second = new ScoreRecord(17,5,"HARD");
        ScoreRecord third = new ScoreRecord(5,5,"MEDIUM");
        ScoreRecord fourth = new ScoreRecord(6,7,"HARD");
        ScoreRecord fifth = new ScoreRecord(22,68,"EASY");
        ScoreRecord sixth = new ScoreRecord(5,300,"MEDIUM");

        ArrayList<ScoreRecord> scoreList = new ArrayList<>();
        scoreList.add(first);
        scoreList.add(second);
        scoreList.add(third);
        scoreList.add(fourth);
        scoreList.add(fifth);
        scoreList.add(sixth);



    }
}