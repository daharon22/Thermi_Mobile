package com.yondav.thermimobile;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Home extends AppCompatActivity {

    private Button thermiButton;
    private final int REQUEST_PERMISSION_POST_NOTIFICATIONS=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        createNotificationChannel();
        requestNotifPermission();
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String tok = sharedPref.getString("token", null);

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
                        Map m = new HashMap<String,String>();
                        m.put("fcm_token", token);
                        m.put("tok", tok);
                        APIServices a = new APIServices(new APIServiceListener() {
                            @Override
                            public void serviceCompleted(boolean success, String res) {
                                try {
                                    JSONObject returned = new JSONObject(res);



                                    Toast toast = Toast.makeText(getApplicationContext(), "Added fcm tok", Toast.LENGTH_SHORT);
                                    toast.show();


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
                                a.addFCM(m);
                            }
                        });

                    }
                });
        /*
        final Handler handler = new Handler();
        final int delay = 10000; // 1000 milliseconds == 1 second

        handler.postDelayed(new Runnable() {
            public void run() {
                sendNotif("Test1","Testing!");
                handler.postDelayed(this, delay);
            }
        }, delay);


        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String tok = sharedPref.getString("token", null);

        if(tok==null){
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
        */
        thermiButton = findViewById(R.id.thermiButton);


        thermiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Device1.class));

            }
        });
    }

    private void sendNotif(String textTitle, String textContent){
        // Create an explicit intent for an Activity in your app.
        Intent intent = new Intent(this, Device1.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){

            NotificationManagerCompat.from(this).notify(1, builder.build());

        }



    }
    private boolean requestNotifPermission(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.POST_NOTIFICATIONS)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.POST_NOTIFICATIONS, REQUEST_PERMISSION_POST_NOTIFICATIONS);
            } else {
                requestPermission(Manifest.permission.POST_NOTIFICATIONS, REQUEST_PERMISSION_POST_NOTIFICATIONS);
            }
            return false;
        }
        else{
            return true;
        }


    }

    private void showExplanation(String title, String message, final String permission, final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.tempAlertsChannelName);
            String description = getString(R.string.tempAlertsChannelDescription);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}