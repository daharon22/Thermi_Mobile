package com.yondav.thermimobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Signup extends AppCompatActivity {

    private EditText firstname, lastname, email, password;

    private Button signupButton;

    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String tok = sharedPref.getString("token", null);

        if(tok !=null){
            startActivity(new Intent(getApplicationContext(), Home.class));
        }

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signupButton = findViewById(R.id.signupButton);
        login = findViewById(R.id.loginLink);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean works = validateInputs();
                if(works){
                    //Toast.makeText(getApplicationContext(), "All good", Toast.LENGTH_LONG).show();
                    requestSignUp();
                }
            }
        });



    }

    private void requestSignUp(){
        SignupRequest s = new SignupRequest(firstname.getText().toString(),
                lastname.getText().toString(),
                email.getText().toString(),
                password.getText().toString());
        APIServices a = new APIServices(new APIServiceListener() {
            @Override
            public void serviceCompleted(boolean success, String res) {

                try {
                    JSONObject returned = new JSONObject(res);


                    if (returned.getString("works").equals("true")) {
                        startActivity(new Intent(getApplicationContext(), Home.class));
                    }
                    else if(returned.getString("works").equals("email in use"))
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Email already in use", Toast.LENGTH_SHORT);
                        toast.show();
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
                                 a.signUp(s);
                             }
                         });

    }

    private boolean validateInputs()
    {
        boolean allValid = requireTextField(firstname, R.string.firstname_hint) &
                requireTextField(lastname, R.string.lastname_hint) &
                requireTextField(email, R.string.email_hint) &
                requireTextField(password, R.string.password_hint);
        return allValid;
    }

    private boolean requireTextField(EditText field, int resid){

        if(TextUtils.isEmpty(field.getText().toString()))
        {
            String s = getResources().getString(R.string.fieldIsRequired);
            s = String.format(s,getResources().getString(resid));


            field.setError(s);
            return false;
        }
        return true;
    }
}