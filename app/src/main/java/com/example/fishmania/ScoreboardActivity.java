package com.example.fishmania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardActivity extends OptionMenuActivity {
    List<ScoreRecord> scoreList = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        //Example score list
        ScoreRecord first = new ScoreRecord(LocalDateTime.now(),5,5,"EASY");
        ScoreRecord second = new ScoreRecord(LocalDateTime.now(),17,5,"HARD");
        ScoreRecord third = new ScoreRecord(LocalDateTime.now(),5,5,"MEDIUM");
        ScoreRecord fourth = new ScoreRecord(LocalDateTime.now(),6,7,"HARD");
        ScoreRecord fifth = new ScoreRecord(LocalDateTime.now(),22,68,"EASY");
        ScoreRecord sixth = new ScoreRecord(LocalDateTime.now(),5,300,"MEDIUM");

        //ArrayList of examples
        scoreList.add(first);
        scoreList.add(second);
        scoreList.add(third);
        scoreList.add(fourth);
        scoreList.add(fifth);
        scoreList.add(sixth);

        MyAdapter adapter = new MyAdapter(this, R.layout.adapter_view_layout,scoreList);
        listView = findViewById(R.id.scoreboard_list);
        listView.setAdapter(adapter);
    }

    protected ScoreRecord createNewScoreRecord(){
        //Shared preferences of game options pull
        SharedPreferences finalRecordSP = getApplicationContext().getSharedPreferences("GameOptionsPrefs", Context.MODE_PRIVATE);

        //Retrieve information from shared preferences about latest game score
        String difficultyRecord = finalRecordSP.getString("difficulty","");
        int numOfFishRecord = finalRecordSP.getInt("numberOfFish", Integer.parseInt(""));
        int finalScoreRecord = finalRecordSP.getInt("finalScore", Integer.parseInt(""));

        ScoreRecord newRecord = new ScoreRecord(LocalDateTime.now(),numOfFishRecord,finalScoreRecord,difficultyRecord);
        return newRecord;
    }
}