package com.example.fishmania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.Locale;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;


public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.background_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Button btn1 = (Button) findViewById(R.id.buttonExit);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    public void setLocal(Activity activity, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        switch (item.getItemId()){
            case R.id.english:
                setLocal(MainActivity.this,"en");
                finish();
                startActivity(intent);
                break;
            case R.id.hebrew:
                setLocal(MainActivity.this,"he");
                finish();
                startActivity(intent);
                break;
            case R.id.music:
                if(mediaPlayer.isPlaying()){
                    onPause();
                }
                else {
                    onResume();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openChooseLevelActivity(View view) {
        Intent intent = new Intent(this,ChooseLevelActivity.class);
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

}