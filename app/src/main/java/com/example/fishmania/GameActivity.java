package com.example.fishmania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.time.LocalDateTime;

public class GameActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    int numOfFishEaten, finalScore;
    LocalDateTime dateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Remove top action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //Shared preferences of game options pull
        SharedPreferences gameOptionsSP = getApplicationContext().getSharedPreferences("GameOptionsPrefs", Context.MODE_PRIVATE);

        //Dynamic difficulty game from shared preferences
        String chosenDifficulty = gameOptionsSP.getString("difficulty","");

        //Dynamic background image from share preferences
        String chosenBackground = gameOptionsSP.getString("background","");
        relativeLayout = findViewById(R.id.game_activity_id);
        if(chosenBackground=="ocean"){
            relativeLayout.setBackgroundResource(R.drawable.ocean_bg_option);
        }
        else{
            relativeLayout.setBackgroundResource(R.drawable.underwater_bg_option);
        }

        //Dynamic PlayerFish image from shared preferences
        String chosenFishColor = gameOptionsSP.getString("fishColor","");
        ImageView playerFish = findViewById(R.id.playerFish);
        if(chosenFishColor=="green"){
            playerFish.setImageResource(R.drawable.green_eating_fish);
        }
        else{
            playerFish.setImageResource(R.drawable.pink_eating_fish);
        }
    }
}