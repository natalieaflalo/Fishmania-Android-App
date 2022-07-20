package com.example.fishmania;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SignInActivity extends OptionMenuActivity {
    private List<EditText> inputEditText = new ArrayList<EditText>();
    private List<String> signInUserInfo = new ArrayList<String>();
    private boolean isInputIsValid;
    private boolean isPasswordAreValid;
    private boolean isChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initEditTextList();
    }

    private void initEditTextList() {
        inputEditText.clear();
        inputEditText.add(findViewById(R.id.editTextName));
        inputEditText.add(findViewById(R.id.editTextEmail));
        inputEditText.add(findViewById(R.id.editTextAge));
        initStringList();
    }

    private void initStringList() {
        signInUserInfo.clear();

        for (EditText editText: inputEditText){
            String userInput = editText.getText().toString();

            signInUserInfo.add(userInput);
        }
    }

    public void enableGetStartedButton(View view) {
        initEditTextList();
        boolean isUserData = checkUserData();
        boolean isPasswordsMatching = isPasswordMatch();

        if(isUserData && isPasswordsMatching){
            //continue
        }
    }

    private boolean checkUserData() {
        boolean isDataValid = true;

        for (String input: signInUserInfo) {
            if(input.matches("")){
                isDataValid = false;
                Toast toast = Toast.makeText(this, "One or more fields are missing", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
                break;
            }
        }

        return isDataValid;
    }

    private boolean isPasswordMatch() {
        EditText password = findViewById(R.id.editTextPassword);
        EditText passwordVerify = findViewById(R.id.editTextVerifyPassword);
        String passwordString = password.getText().toString();
        String passwordVerifyString = passwordVerify.getText().toString();
        boolean isPasswordMatch = true;

        if(passwordString.matches("") || passwordVerifyString.matches("")) {
            isPasswordMatch = false;
            Toast toast = Toast.makeText(this, "Password Not Matching", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
        else{
            isPasswordMatch = passwordString.matches(passwordVerifyString);

            if(!isPasswordMatch){
                Toast toast = Toast.makeText(this, "Password Not Matching", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
            }
        }

        return isPasswordMatch;
    }
}