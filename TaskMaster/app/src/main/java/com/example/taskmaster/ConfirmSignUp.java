package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;

public class ConfirmSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sign_up);

        findViewById(R.id.buttonconfirm).setOnClickListener(v ->{

            String code=((EditText)findViewById(R.id.confirmcode)).getText().toString();
            String username=getIntent().getStringExtra("username");

            Amplify.Auth.confirmSignUp(
                    username,
                    code,
                    result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                    error -> Log.e("AuthQuickstart", error.toString())
            );
        });
    }
}