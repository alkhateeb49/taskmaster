package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddTask extends AppCompatActivity {

    EditText title,body;
    Button add;
    TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        title=findViewById(R.id.title);
        body=findViewById(R.id.body);
        add=findViewById(R.id.addTask);
        total=findViewById(R.id.total);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string_Title = title.getText().toString().trim();
                String string_Body = body.getText().toString().trim();
                total.setText("Title : "+ string_Title+ "\n"+ "Description :"+string_Body);
            }
        });


    }
}