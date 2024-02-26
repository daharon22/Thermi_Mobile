package com.yondav.thermimobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Device1 extends AppCompatActivity {

    TextView temp1;
    TextView temp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device1);

        /*
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String tok = sharedPref.getString("token", null);

        if(tok==null){
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
        */

        temp1= findViewById(R.id.temp1);
        temp2= findViewById(R.id.temp2);

        requestTemp();

        final Handler handler = new Handler();
        final int delay = 10000; // 1000 milliseconds == 1 second

        handler.postDelayed(new Runnable() {
            public void run() {
                requestTemp();
                handler.postDelayed(this, delay);
            }
        }, delay);

    }
    private void requestTemp()
    {
        TempRequest t = new TempRequest("12345");
        APIServices a = new APIServices(new APIServiceListener() {
            @Override
            public void serviceCompleted(boolean success, String res) {
                try {
                    JSONObject returned = new JSONObject(res);


                    if (returned.getBoolean("works")) {


                        temp1.setText(returned.getDouble("temperature_t1")+"");
                        temp2.setText(returned.getDouble("temperature_t2")+"");

                    }
                    else
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Could not access temperatures", Toast.LENGTH_SHORT);
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
                a.getTemp(t);
            }
        });
    }
}