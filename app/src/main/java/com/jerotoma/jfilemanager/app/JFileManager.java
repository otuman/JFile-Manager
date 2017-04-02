package com.jerotoma.jfilemanager.app;

import android.app.Application;

/**
 * Created by Otoman on 01/04/2017.
 */

public class JFileManager extends Application {

   private static  JFileManager jFileManager;

    @Override
    public void onCreate() {
        super.onCreate();
        jFileManager = this;
    }

    public static JFileManager getInstance(){
        return jFileManager;
    }

}
