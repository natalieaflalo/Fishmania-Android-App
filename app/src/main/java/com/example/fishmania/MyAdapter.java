package com.example.fishmania;

import static androidx.core.content.res.TypedArrayUtils.getText;
import static java.lang.String.valueOf;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<ScoreRecord> {

    private LayoutInflater inflater;
    private List<ScoreRecord> scoreList;
    private int viewResourceId ;
    private Context myContext;

    public MyAdapter(Context context, int textViewId, List<ScoreRecord> scoreRecords){
        super(context, textViewId, scoreRecords);
        scoreList = scoreRecords;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewResourceId = textViewId;
        myContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        convertView = inflater.inflate(viewResourceId, null);
        ScoreRecord scoreRecord = scoreList.get(position);

        if(scoreRecord != null){
            TextView date = convertView.findViewById(R.id.adapter_date);
            TextView numberOfFish = convertView.findViewById(R.id.adapter_num_of_fish);
            TextView finalValue = convertView.findViewById(R.id.adapter_final_value);
            TextView gameLevel = convertView.findViewById(R.id.adapter_game_level);

            date.setText(valueOf(scoreRecord.getDate()));

            CharSequence charNumberOfFish = new StringBuffer(" " + scoreRecord.getNum_of_fish());
            numberOfFish.append(charNumberOfFish);

            CharSequence charFinalValue = new StringBuffer(" " + scoreRecord.getFinal_score());
            finalValue.append(charFinalValue);

            CharSequence charGameLevel = new StringBuffer(" " + scoreRecord.getGame_level());
            gameLevel.append(charGameLevel);

        }

        return convertView;
    }
}