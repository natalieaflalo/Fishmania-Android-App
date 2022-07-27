package com.example.fishmania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    public void openMenuActivity(View view) {
        startActivity( new Intent(this, MainActivity.class));
    }
}