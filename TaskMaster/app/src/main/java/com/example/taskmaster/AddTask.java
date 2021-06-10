package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import static com.example.taskmaster.AppDatabase.databaseWriteExecutor;

public class AddTask extends AppCompatActivity {

    EditText title,body,state;
    Button add,upload;
    TextView total;
    double altitude =0;
    double longitude = 0;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        this.setTitle("Add Task");

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



        title=findViewById(R.id.title);
        body=findViewById(R.id.body);
        add=findViewById(R.id.addTask);
        upload=findViewById(R.id.buttonupload);
        state=findViewById(R.id.state);
        total=findViewById(R.id.total);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1212);
        }
        else {

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                altitude = location.getAltitude();
                                longitude = location.getLongitude();

                                Toast.makeText(AddTask.this,location.getAltitude()+"",Toast.LENGTH_SHORT).show();


                                Geocoder geocoder = new Geocoder(AddTask.this, Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(altitude,longitude,1);
                                    if(addresses != null && addresses.size()>0){
//                                            Toast.makeText(AddTaskActivity.this, "country Nmae: "+ addresses.get(0).getAddressLine(0).split(",")[1], Toast.LENGTH_SHORT).show();

                                    }

//                                      countryName = getApplicationContext().getResources().getConfiguration().locale.getDisplayCountry();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

        }


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


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFileFromMobileStorage();
            }
        });


    }



    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);
        }
        if(requestCode==9999){
            File file=new File(getApplicationContext().getFilesDir(),"uploads");
            try{
                InputStream inputStream=getContentResolver().openInputStream(data.getData());
                FileUtils.copy(inputStream,new FileOutputStream(file));
                uploadFile(file,"thisIsMyKey");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getFileFromMobileStorage(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("*/*");
        startActivityForResult(i,9999);
    }

    public void uploadFile(File file, String fileName){
        Amplify.Storage.uploadFile(
                fileName,
                file,
                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1212) {

            if (grantResults.length > 0 ){

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        altitude = location.getAltitude();
                                        longitude = location.getLongitude();
                                    }
                                }
                            });
                }


            }
        }
    }

}