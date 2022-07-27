package com.example.fishmania;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    public int x,y;
    public Bitmap background;

    public Background (int screenX, int screenY, Resources resources, String chosenBackground) {

        if(chosenBackground=="ocean"){
            background = BitmapFactory.decodeResource(resources, R.drawable.ocean_bg_option);
        }
        else{
            background = BitmapFactory.decodeResource(resources, R.drawable.underwater_bg_option);
        }
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);


    }


}
