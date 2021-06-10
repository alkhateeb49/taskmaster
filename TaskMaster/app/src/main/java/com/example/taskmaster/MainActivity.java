package com.example.taskmaster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import static com.example.taskmaster.AppDatabase.databaseWriteExecutor;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

     FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
        @Override
        public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
            if (!task.isSuccessful()) {
                Log.w("FCM token ...", "Fetching FCM is failed", task.getException());
                return;
            }
            String token = task.getResult();
            Log.d("FCM TOKEN ...",task.getResult());
        }
    });


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);
        }
    }

    protected void uploadFile(Context context){
        File file = new File(context.getFilesDir(), "key0");

        try{
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.append("test");
            bufferedWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        Amplify.Storage.uploadFile(
                "key0",
                file,
                result -> Log.i("uploadFile", "Successfully Uploaded: "+ result.getKey()),
                error -> Log.e("uploadFile", "Storage Failure: "+error)
        );

    }

    Button btn_add, btn_all, btn_task_1, btn_task_2, btn_task_3, btn_setting,btn_signup,btn_signin,btn_logout;
    TextView username;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        try {
//            Amplify.addPlugin(new AWSDataStorePlugin());
//            Amplify.configure(getApplicationContext());
//
//            Log.i("Tutorial", "Initialized Amplify");
//        } catch (AmplifyException e) {
//            Log.e("Tutorial", "Could not initialize Amplify", e);
//        }

        try {
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        Amplify.Auth.signInWithWebUI(
                this,
                result -> Log.i("AuthQuickStart", result.toString()),
                error -> Log.e("AuthQuickStart", error.toString())
        );



//        AuthSignUpOptions options = AuthSignUpOptions.builder()
//                .userAttribute(AuthUserAttributeKey.email(), "my@email.com")
//                .build();
//        Amplify.Auth.signUp("username", "Password123", options,
//                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
//                error -> Log.e("AuthQuickStart", "Sign up failed", error)
//        );

//        Amplify.DataStore.observe(Task.class,
//                started -> Log.i("Tutorial", "Observation began."),
//                change -> Log.i("Tutorial", change.item().toString()),
//                failure -> Log.e("Tutorial", "Observation failed.", failure),
//                () -> Log.i("Tutorial", "Observation complete.")
//        );
//        Amplify.Auth.fetchAuthSession(
//                result -> Log.i("AmplifyQuickstart", result.toString()),
//                error -> Log.e("AmplifyQuickstart", error.toString())
//        );

        this.setTitle("Main Activity");
        btn_add = findViewById(R.id.addTask);
        btn_all = findViewById(R.id.allTask);
        btn_task_1= findViewById(R.id.task1);
        btn_task_2= findViewById(R.id.task2);
        btn_task_3= findViewById(R.id.task3);
        btn_setting= findViewById(R.id.settings);
        username=findViewById(R.id.username);
        btn_signup=findViewById(R.id.signup);
        btn_signin=findViewById(R.id.signin);
        btn_logout=findViewById(R.id.logout);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString("username", "Name");
        username.setText(userName + "\'s Tasks");


        if(getIntent().getStringExtra("username")!=null){
            btn_signup.setVisibility(View.GONE);
            btn_signin.setVisibility(View.GONE);
            btn_logout.setVisibility(View.VISIBLE);

        }
        else{
            btn_logout.setVisibility(View.GONE);
            btn_signup.setVisibility(View.VISIBLE);
            btn_signin.setVisibility(View.VISIBLE);
        }


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddTask.class);
                startActivity(i);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignIn.class);
                startActivity(i);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signOut(
                        AuthSignOutOptions.builder().globalSignOut(true).build(),
                        () -> Log.i("AuthQuickstart", "Signed out globally"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );
                Intent i = new Intent(MainActivity.this, MainActivity.class);
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

//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//
//                List<Tasks> taskList = AppDatabase.getDatabase(getApplicationContext()).taskDao().getAll();
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                recyclerView.setAdapter(new TaskAdapter(   MainActivity.this, taskList));
//            }
//        });
//        Amplify.DataStore.query(Task.class,
//                todos -> {
//                    List<Tasks> taskList = new ArrayList<>();
//                    while (todos.hasNext()) {
//                        Task todo = todos.next();
//                        Tasks t = new Tasks();
//                        Log.i("Tutorial", "==== Todo ====");
//                        Log.i("Tutorial", "Name: " + todo.getTitle());
//                        Log.i("Tutorial", "Name: " + todo.getBody());
//                        Log.i("Tutorial", "Name: " + todo.getState());
//                        t.setTitle(todo.getTitle());
//                        t.setBody(todo.getBody());
//                        t.setState(todo.getState());
//                        taskList.add(t);
//                        adapter=new TaskAdapter(   MainActivity.this, taskList);
//                    }
//                    recyclerView.setAdapter(adapter);
//                },
//                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
//        );



    }


//
//    @RequiresApi(api = Build.VERSION_CODES.Q)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
//            Amplify.Auth.handleWebUISignInResponse(data);
//
//        }
//        if(requestCode==9999){
//            File file=new File(getApplicationContext().getFilesDir(),"uploads");
//            try{
//                InputStream inputStream=getContentResolver().openInputStream(data.getData());
//                FileUtils.copy(inputStream,new FileOutputStream(file));
//                uploadFile(file,"thisIsMyKey");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void getFileFromMobileStorage(){
//        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//        i.setType("*/*");
//        startActivityForResult(i,9999);
//    }
//
//    public void uploadFile(File file, String fileName){
//        Amplify.Storage.uploadFile(
//                fileName,
//                file,
//                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
//                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
//        );
//    }
}