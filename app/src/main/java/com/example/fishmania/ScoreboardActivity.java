package com.example.fishmania;

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

        scoreList.add(createNewScoreRecord());

        MyAdapter adapter = new MyAdapter(this, R.layout.adapter_view_layout,scoreList);
        listView = findViewById(R.id.scoreboard_list);
        listView.setAdapter(adapter);
    }

    private ScoreRecord createNewScoreRecord(){
        //Shared preferences of game options pull
        SharedPreferences finalRecordSP = getApplicationContext().getSharedPreferences("GameOptionsPrefs", Context.MODE_PRIVATE);

        //Retrieve information from shared preferences about latest game score
        String difficultyRecord = finalRecordSP.getString("difficulty","");
        int numOfFishRecord = finalRecordSP.getInt("numberOfFish", 0);
        int finalScoreRecord = finalRecordSP.getInt("finalScore", 0);
        ScoreRecord newRecord = new ScoreRecord(LocalDateTime.now(),numOfFishRecord,finalScoreRecord,difficultyRecord);

        return newRecord;
    }
}