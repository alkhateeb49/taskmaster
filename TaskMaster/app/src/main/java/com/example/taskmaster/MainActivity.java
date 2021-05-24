package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;

import static com.example.taskmaster.AppDatabase.databaseWriteExecutor;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_add, btn_all, btn_task_1, btn_task_2, btn_task_3, btn_setting;
    TextView username;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("Tutorial", "Could not initialize Amplify", e);
        }

        this.setTitle("Main Activity");
        btn_add = findViewById(R.id.addTask);
        btn_all = findViewById(R.id.allTask);
        btn_task_1= findViewById(R.id.task1);
        btn_task_2= findViewById(R.id.task2);
        btn_task_3= findViewById(R.id.task3);
        btn_setting= findViewById(R.id.settings);
        username=findViewById(R.id.username);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString("username", "Name");
        username.setText(userName + "\'s Tasks");


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddTask.class);
                startActivity(i);
            }
        });

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AllTask.class);
                startActivity(i);
            }
        });

        /*_________*/
        btn_task_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TaskDetail.class);
                i.putExtra("title", "Task One Detail");
                startActivity(i);
            }
        });
        btn_task_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TaskDetail.class);
                i.putExtra("title", "Task Two Detail");
                startActivity(i);
            }
        });
        btn_task_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TaskDetail.class);
                i.putExtra("title", "Task Three Detail");
                startActivity(i);
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingsPage.class);
                startActivity(i);
            }
        });



        /* */
//        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "tasks-database").build();
//        TaskDao taskDao = db.taskDao();
//        List<Tasks> tasks = taskDao.getAll();
        /* */

        /* */
//        String lorem ="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud.";
//        ArrayList<Tasks> tasks = new ArrayList<>();
//        tasks.add(new Tasks("1st Task", lorem , "first state"));
//        tasks.add(new Tasks("2ed Task", lorem, "second state"));
//        tasks.add(new Tasks("3rd Task", lorem, "third state"));
//        tasks.add(new Tasks("4th Task ", lorem, "fourth state"));

//        RecyclerView taskList = findViewById(R.id.recyclerView);
//        taskList.setLayoutManager(new LinearLayoutManager(this));
//        taskList.setAdapter(new TaskAdapter(   this, tasks));
        /* */

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//
//                List<Tasks> taskList = AppDatabase.getDatabase(getApplicationContext()).taskDao().getAll();
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                recyclerView.setAdapter(new TaskAdapter(   MainActivity.this, taskList));
//            }
//        });
        Amplify.DataStore.query(Task.class,
                todos -> {
                    List<Tasks> taskList = new ArrayList<>();
                    while (todos.hasNext()) {
                        Task todo = todos.next();
                        Tasks t = new Tasks();
                        Log.i("Tutorial", "==== Todo ====");
                        Log.i("Tutorial", "Name: " + todo.getTitle());
                        Log.i("Tutorial", "Name: " + todo.getBody());
                        Log.i("Tutorial", "Name: " + todo.getState());
                        t.setTitle(todo.getTitle());
                        t.setBody(todo.getBody());
                        t.setState(todo.getState());
                        taskList.add(t);
                        adapter=new TaskAdapter(   MainActivity.this, taskList);
                    }
                    recyclerView.setAdapter(adapter);
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );



    }
}