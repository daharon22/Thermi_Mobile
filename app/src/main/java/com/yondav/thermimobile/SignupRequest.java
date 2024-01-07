package com.yondav.thermimobile;

import java.util.HashMap;
import java.util.Map;

public class SignupRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public SignupRequest(String fn, String ln, String e, String p){
        setFirstname(fn);
        setLastname(ln);
        setEmail(e);
        setPassword(p);
    }

    public Map getValues(){
        Map m = new HashMap<String,String>();
        m.put("First_Name", getFirstname());
        m.put("Last_Name", getLastname());
        m.put("Email", getEmail());
        m.put("Password", getPassword());
        return m;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
