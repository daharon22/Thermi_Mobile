package com.yondav.thermimobile;

import android.media.Ringtone;

public class Global {
    private static Global global = null;
    private Ringtone ringtone;
    private Global(){

    }
    public static Global getInstance(){
        if(global != null){
            return global;
        }
        global = new Global();
        return global;
    }

    public void setRingtone(Ringtone r){
        ringtone = r;
    }

    public Ringtone getRingtone() {
        return ringtone;

    }
}
