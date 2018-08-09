package com.lipeng.mygithub.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lipeng.mygithub.app.AppConfig;

/**
 * green dao管理
 * @author biginsect
 * @date  2018/8/9.
 */

public class GreenDaoManager {

    private static volatile GreenDaoManager instance;
    private static DaoSession daoSession;
    private static DaoMaster daoMaster;
    private static SQLiteDatabase db;

    private GreenDaoManager(Context context){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, AppConfig.DB_NAME, null);
        db = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static GreenDaoManager getInstance(Context context){
        if (null == instance){
            synchronized (GreenDaoManager.class){
                if (null == instance){
                    instance = new GreenDaoManager(context);
                }
            }
        }

        return instance;
    }

    public  DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public  DaoSession getDaoSession() {
        return daoSession;
    }

    public  SQLiteDatabase getDb() {
        return db;
    }
}
