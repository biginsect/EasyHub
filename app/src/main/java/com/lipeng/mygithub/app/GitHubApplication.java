package com.lipeng.mygithub.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author lipeng
 * @date 2017/12/28
 */

public class GitHubApplication extends Application{
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        application = this;
    }


    public static Application getInstance(){
        return application;
    }
}
