package com.example.fishmania;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class HowToPlayDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        CharSequence text = new StringBuffer(getText(R.string.okButton));

        builder.setView(inflater.inflate(R.layout.howtoplay_dialog, null));
        builder.setPositiveButton(text, (dialogInterface, i) -> {});

        return builder.create();
    }
}
