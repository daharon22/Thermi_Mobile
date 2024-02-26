package com.yondav.thermimobile;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.*;

public class Login extends AppCompatActivity {
    private EditText email, password;

    private Button loginButton;
    private TextView signup;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String tok = sharedPref.getString("token", null);

        if(tok !=null){
            startActivity(new Intent(getApplicationContext(), Home.class));
        }

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signupLink);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Signup.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestLogin();
            }
        });
    }

    private void requestLogin()
    {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        LoginRequest l = new LoginRequest(email.getText().toString(),password.getText().toString(), token);
                        APIServices a = new APIServices(new APIServiceListener() {
                            @Override
                            public void serviceCompleted(boolean success, String res) {
                                try {
                                    JSONObject returned = new JSONObject(res);


                                    if (returned.getBoolean("works")) {
                                        SharedPreferences sharedPref = Login.this.getSharedPreferences(
                                                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("token", returned.getString("token"));
                                        editor.apply();
                                        startActivity(new Intent(getApplicationContext(), Home.class));
                                    }
                                    else
                                    {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Incorrect Email or Password", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }

                                }
                                catch(Exception e){
                                    Log.e("Thermi", "Could not parse response", e);
                                }
                            }
                        });

                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                a.logIn(l);
                            }
                        });


                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        //Log.d(TAG, msg);
                        //Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });



    }
}