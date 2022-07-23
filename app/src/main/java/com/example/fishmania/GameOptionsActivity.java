package com.example.fishmania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class GameOptionsActivity extends OptionMenuActivity implements View.OnClickListener{

    SharedPreferences gameOptionsSP;
    Button easyBtn, mediumBtn, hardBtn;
    ImageButton oceanBG, underwaterBG, greenFish, pinkFish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_options);

        //Find and create variable to every button
        easyBtn = findViewById(R.id.buttonEasy);
        mediumBtn = findViewById(R.id.buttonMedium);
        hardBtn = findViewById(R.id.buttonHard);
        oceanBG = findViewById(R.id.oceanOptionImageButton);
        underwaterBG = findViewById(R.id.underwaterOptionImageButton);
        greenFish = findViewById(R.id.greenFishOptionImageButton);
        pinkFish = findViewById(R.id.pinkFishOptionImageButton);

        //Create shared preferences variable and define default values to every key
        gameOptionsSP = getSharedPreferences("GameOptionsPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor defaultEditor = gameOptionsSP.edit();
        defaultEditor.putString("difficulty","easy");
        defaultEditor.putString("background","underwater");
        defaultEditor.putString("fishColor","green");
        defaultEditor.putInt("numberOfFish",0);
        defaultEditor.putInt("finalScore",0);
        defaultEditor.commit();

        mediumBtn.getBackground().setAlpha(90);
        hardBtn.getBackground().setAlpha(90);
        underwaterBG.setBackgroundResource(R.color.pressed_green);
        greenFish.setBackgroundResource(R.color.pressed_green);

        //On click listener to every button
        easyBtn.setOnClickListener(this);
        mediumBtn.setOnClickListener(this);
        hardBtn.setOnClickListener(this);
        oceanBG.setOnClickListener(this);
        underwaterBG.setOnClickListener(this);
        greenFish.setOnClickListener(this);
        pinkFish.setOnClickListener(this);

    }

    public void openGameActivity(View view) {
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor = gameOptionsSP.edit();
        switch (view.getId()){
            case R.id.buttonEasy:
                editor.putString("difficulty","easy");

                easyBtn.setBackgroundResource(R.color.dodgerblue);
                mediumBtn.getBackground().setAlpha(90);
                hardBtn.getBackground().setAlpha(90);
                break;
            case R.id.buttonMedium:
                editor.putString("difficulty","medium");
                easyBtn.getBackground().setAlpha(90);
                mediumBtn.setBackgroundResource(R.color.dodgerblue);
                hardBtn.getBackground().setAlpha(90);
                break;
            case R.id.buttonHard:
                editor.putString("difficulty","hard");
                easyBtn.getBackground().setAlpha(90);
                mediumBtn.getBackground().setAlpha(90);
                hardBtn.setBackgroundResource(R.color.dodgerblue);
                break;
            case R.id.oceanOptionImageButton:
                editor.putString("background","ocean");
                oceanBG.setBackgroundResource(R.color.pressed_green);
                underwaterBG.setBackgroundResource(R.color.transparent);
                break;
            case R.id.underwaterOptionImageButton:
                editor.putString("background","underwater");
                oceanBG.setBackgroundResource(R.color.transparent);
                underwaterBG.setBackgroundResource(R.color.pressed_green);
                break;
            case R.id.greenFishOptionImageButton:
                editor.putString("fishColor","green");
                greenFish.setBackgroundResource(R.color.pressed_green);
                pinkFish.setBackgroundResource(R.color.transparent);
                break;
            case R.id.pinkFishOptionImageButton:
                editor.putString("fishColor","pink");
                greenFish.setBackgroundResource(R.color.transparent);
                pinkFish.setBackgroundResource(R.color.pressed_green);
                break;
        }
        editor.commit();
    }
}