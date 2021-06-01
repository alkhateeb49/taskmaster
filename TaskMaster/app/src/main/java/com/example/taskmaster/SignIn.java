package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        findViewById(R.id.buttonlogin).setOnClickListener(v->{
            String username=((EditText)findViewById(R.id.singinusername)).getText().toString();
            String password=((EditText)findViewById(R.id.singinpassword)).getText().toString();


            Amplify.Auth.signIn(
                    username,
                    password,
                    result -> Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete"),
                    error -> Log.e("AuthQuickstart", error.toString())
            );


            Intent i = new Intent(this,MainActivity.class);
            i.putExtra("username",username);
            startActivity(i);

        });



    }
}