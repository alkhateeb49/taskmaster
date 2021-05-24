package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.List;

import static com.example.taskmaster.AppDatabase.databaseWriteExecutor;

public class AddTask extends AppCompatActivity {

    EditText title,body,state;
    Button add;
    TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        this.setTitle("Add Task");

        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("Tutorial", "Could not initialize Amplify", e);
        }


        title=findViewById(R.id.title);
        body=findViewById(R.id.body);
        add=findViewById(R.id.addTask);
        state=findViewById(R.id.state);
        total=findViewById(R.id.total);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string_Title = title.getText().toString().trim();
                String string_Body = body.getText().toString().trim();
                String string_State = state.getText().toString().trim();
//                total.setText("Title : "+ string_Title+ "\n"+ "Description :"+string_Body);

                /* */

                databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Tasks tasks=new Tasks();
                        tasks.setTitle(string_Title);
                        tasks.setBody(string_Body);
                        tasks.setState(string_State);
//                        AppDatabase.getDatabase(getApplicationContext()).taskDao().insertAll(tasks);


                        Task task = com.amplifyframework.datastore.generated.model.Task.builder()
                                .title(title.getText().toString())
                                .body(body.getText().toString())
                                .state(state.getText().toString())
                                .build();

                        Amplify.DataStore.save(task,
                                success -> Log.i("Tutorial", "Saved item: " + success.item().getTitle()),
                                error -> Log.e("Tutorial", "Could not save item to DataStore", error)
                        );

                        Intent i=new Intent(AddTask.this,MainActivity.class);
                        startActivity(i);
                    }
                });

                /* */


            }
        });



    }
}