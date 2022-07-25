package com.example.fishmania;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends OptionMenuActivity {
    private EditText userName;
    private EditText userEmail;
    private EditText userAge;
    private EditText userPassword;
    private Button signUp;
    private FirebaseAuth mAuthSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userName = findViewById(R.id.editTextUserName);
        userEmail = findViewById(R.id.editTextEmail);
        userAge = findViewById(R.id.editTextAge);
        userPassword = findViewById(R.id.editTextPassword);
        signUp = findViewById(R.id.buttonSignUpHere);

        mAuthSignUp = FirebaseAuth.getInstance();

        signUp.setOnClickListener(v -> {
            String name = userName.getText().toString().trim();
            String email = userEmail.getText().toString().trim();
            String age = userAge.getText().toString().trim();
            String password = userPassword.getText().toString().trim();

            if(name.isEmpty()){
                userName.setError(getText(R.string.nameIsEmpty));
                userName.requestFocus();
                return;
            }
            if(email.isEmpty()) {
                userEmail.setError(getText(R.string.emailEmpty));
                userEmail.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                userEmail.setError(getText(R.string.enterValidEmail));
                userEmail.requestFocus();
                return;
            }

            if(age.isEmpty()){
                userAge.setError(getText(R.string.ageIsEmpty));
                userAge.requestFocus();
                return;
            }
            if(password.isEmpty()) {
                userPassword.setError(getText(R.string.passwordEmpty));
                userPassword.requestFocus();
                return;
            }
            if(password.length()<6) {
                userPassword.setError(getText(R.string.passwordLength));
                userPassword.requestFocus();
                return;
            }

            mAuthSignUp.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this,R.string.registered, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                }
                else {
                    Toast.makeText(SignUpActivity.this,R.string.notRegister,Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}