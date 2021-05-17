package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    TextView title, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        title=findViewById(R.id.title);
        description=findViewById(R.id.description);

        Intent i = getIntent();
        String Tilte=i.getStringExtra("title");
        title.setText(Tilte);
    }
}