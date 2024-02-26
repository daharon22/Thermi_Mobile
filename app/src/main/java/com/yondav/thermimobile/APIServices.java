package com.yondav.thermimobile;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class APIServices {

    private static final String API_SERVICE_URL_PREFIX = "http://172.16.0.60:3000";
    private static final String SIGNUP_SERVICE_URL  = API_SERVICE_URL_PREFIX + "/api/signup";
    private static final String LOGIN_SERVICE_URL  = API_SERVICE_URL_PREFIX + "/api/login";
    private static final String TEMP_SERVICE_URL  = API_SERVICE_URL_PREFIX + "/api/gettemp";
    private static final String ADD_FCM_TOKEN_URL  = API_SERVICE_URL_PREFIX + "/api/addfcmtoken";

    private APIServiceListener listener;
    public APIServices(APIServiceListener l){

        setListener(l);

    }


    public APIServiceListener getListener() {
        return listener;
    }

    public void setListener(APIServiceListener listener) {
        this.listener = listener;
    }


    public void signUp(SignupRequest s){
        boolean success = false;
        String response = "";
        try {

            URL url = new URL(SIGNUP_SERVICE_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(s.getValues()));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            responseCode/=100;

            InputStream is;

            if (responseCode == 2) {
                is= conn.getInputStream();
                success=true;

            }
            else {
                is= conn.getErrorStream();

            }
            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            while ((line=br.readLine()) != null) {
                response+=line;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        final boolean fsuccess = success;
        final String fresponse = response;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                listener.serviceCompleted(fsuccess, fresponse);
            }
        });



    }

    public void logIn(LoginRequest l){
        boolean success = false;
        String response = "";
        try {

            URL url = new URL(LOGIN_SERVICE_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(l.getValues()));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            responseCode/=100;

            InputStream is;

            if (responseCode == 2) {
                is= conn.getInputStream();
                success=true;

            }
            else {
                is= conn.getErrorStream();

            }
            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            while ((line=br.readLine()) != null) {
                response+=line;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        final boolean fsuccess = success;
        final String fresponse = response;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                listener.serviceCompleted(fsuccess, fresponse);
            }
        });





    }
    public void getTemp(TempRequest t){
        boolean success = false;
        String response = "";
        try {

            URL url = new URL(TEMP_SERVICE_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(t.getValues()));
            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            responseCode/=100;

            InputStream is;

            if (responseCode == 2) {
                is= conn.getInputStream();
                success=true;

            }
            else {
                is= conn.getErrorStream();

            }
            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            while ((line=br.readLine()) != null) {
                response+=line;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        final boolean fsuccess = success;
        final String fresponse = response;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                listener.serviceCompleted(fsuccess, fresponse);
            }
        });





    }
    public void addFCM(Map fcm){
        boolean success = false;
        String response = "";
        try {

            URL url = new URL(ADD_FCM_TOKEN_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(fcm));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            responseCode/=100;

            InputStream is;

            if (responseCode == 2) {
                is= conn.getInputStream();
                success=true;

            }
            else {
                is= conn.getErrorStream();

            }
            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            while ((line=br.readLine()) != null) {
                response+=line;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        final boolean fsuccess = success;
        final String fresponse = response;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                listener.serviceCompleted(fsuccess, fresponse);
            }
        });





    }

    private String getPostDataString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }




















}
