package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsPage extends AppCompatActivity {

    EditText username;
    Button btn_save;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        this.setTitle("Settings Page");

        username=findViewById(R.id.username);
        btn_save = findViewById(R.id.save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                if(!userName.isEmpty()){
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsPage.this);
                    editor = sharedPreferences.edit();
                    editor.putString("username", userName);
                    editor.apply();
                    Intent i = new Intent(SettingsPage.this, MainActivity.class);
                    startActivity(i);
                }

            }
        });








    }
}