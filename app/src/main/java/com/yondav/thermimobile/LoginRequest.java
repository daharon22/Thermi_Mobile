package com.yondav.thermimobile;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest {
    private String email;
    private String pass;

    public LoginRequest(String e, String p)
    {
        setEmail(e);
        setPass(p);
    }
    public Map getValues(){
        Map m = new HashMap<String,String>();
        m.put("email", getEmail());
        m.put("password", getPass());

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
}
