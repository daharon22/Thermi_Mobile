package com.yondav.thermimobile;

import java.util.HashMap;
import java.util.Map;

public class TempConfig {
    private String deviceToken;
    private String temp1;
    private String temp2;

    public TempConfig(String t, String t1, String t2){

        setDeviceToken(t);
        setmax1(t1);
        setmax2(t2);
    }

    public Map getValues(){
        Map m = new HashMap<String,String>();
        m.put("token", getDeviceToken());
        m.put("max1", getmax1());
        m.put("max2", getmax2());

        return m;
    }
    public String getDeviceToken(){
        return deviceToken;
    }

    public void setDeviceToken(String d){
        deviceToken = d;
    }

    public String getmax1(){
        return temp1;
    }

    public void setmax1(String t){
        temp1 = t;
    }
    public String getmax2(){
        return temp2;
    }

    public void setmax2(String t){
        temp2 = t;
    }

}