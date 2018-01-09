package com.lipeng.mygithub.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author lipeng
 * @date 2017/12/28
 */

public class GitHubApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        setRealmConfig();
        Fresco.initialize(this);
    }

    /**
     * 数据库realm配置
     * configuration需要自行设置
     * */
    private void setRealmConfig(){
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);
    }
}
