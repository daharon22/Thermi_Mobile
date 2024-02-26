package com.yondav.thermimobile;
import java.util.HashMap;
import java.util.Map;

public class TempRequest {
    private String deviceToken;

    public TempRequest(String t){

        setDeviceToken(t);
    }

    public Map getValues(){
        Map m = new HashMap<String,String>();
        m.put("token", getDeviceToken());

        return m;
    }
    public String getDeviceToken(){
        return deviceToken;
    }

    public void setDeviceToken(String d){
        deviceToken = d;
    }

}
