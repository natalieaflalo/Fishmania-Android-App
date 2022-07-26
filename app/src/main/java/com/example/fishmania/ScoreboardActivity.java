package com.example.fishmania;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardActivity extends OptionMenuActivity {
    public static List<ScoreRecord> scoreList = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        MyAdapter adapter = new MyAdapter(this, R.layout.adapter_view_layout,scoreList);
        listView = findViewById(R.id.scoreboard_list);
        listView.setAdapter(adapter);
    }
}