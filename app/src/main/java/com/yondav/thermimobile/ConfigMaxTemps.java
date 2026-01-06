package com.yondav.thermimobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConfigMaxTemps extends AppCompatActivity {
    private EditText maxTemp1, maxTemp2;
    private Button configbut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_max_temps);

        maxTemp1 = findViewById(R.id.max1);
        maxTemp2 = findViewById(R.id.max2);
        configbut = findViewById(R.id.config);

        configbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTemps();

            }
        });

    }

    private void setTemps(){
        String m1 = maxTemp1.getText().toString();
        String m2 = maxTemp2.getText().toString();
        //double max1 = Double.parseDouble(m1);
        //double max2 = Double.parseDouble(m2);
        TempConfig config = new TempConfig("12345", m1, m2);
        //TODO: create a feature that checks that you have put in doubles or ints
        APIServices a = new APIServices(new APIServiceListener() {
            @Override
            public void serviceCompleted(boolean success, String res) {

                try {
                    JSONObject returned = new JSONObject(res);


                    if (returned.getString("works").equals("true")) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Success! Please press the button on your device to update it!", Toast.LENGTH_SHORT);
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
                a.configTemp(config);
            }
        });

    }
}