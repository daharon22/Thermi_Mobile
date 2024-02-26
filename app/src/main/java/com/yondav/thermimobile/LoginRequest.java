package com.yondav.thermimobile;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest {
    private String email;
    private String pass;
    private String fcmToken;

    public LoginRequest(String e, String p, String t)
    {
        setEmail(e);
        setPass(p);
        setFcmToken(t);
    }
    public Map getValues(){
        Map m = new HashMap<String,String>();
        m.put("email", getEmail());
        m.put("password", getPass());
        m.put("fcm_token", getFcmToken());

        return m;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
