package com.example.fishmania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends OptionMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1 = findViewById(R.id.buttonExit);
        btn1.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });
    }

    public void openGameOptionActivity(View view) {
        Intent intent = new Intent(this,GameOptionsActivity.class);
        startActivity(intent);
    }

    public void openScoreboardActivity(View view) {
        Intent intent = new Intent(this,ScoreboardActivity.class);
        startActivity(intent);
    }

    public void openLoginActivity(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void openHowToPlayDialog(View view) {
        HowToPlayDialog instructionsDialog = new HowToPlayDialog();
        instructionsDialog.show(getSupportFragmentManager(), "instructions_dialog");
    }
}