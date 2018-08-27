package com.biginsect.easyhub.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.biginsect.easyhub.app.AppConfig;

/**
 * @author biginsect
 * @date  2018/8/9.
 */

public class GreenDaoManager {

    private static DaoSession daoSession;
    private static DaoMaster daoMaster;
    private static SQLiteDatabase db;
    /**flag*/
    private boolean isInit = false;

    private GreenDaoManager(){
    }

    private static final class DaoManagerHolder{
        private static final GreenDaoManager INSTANCE = new GreenDaoManager();
    }

    public static GreenDaoManager getInstance(){
        return DaoManagerHolder.INSTANCE;
    }

    public void init(Context context){
        if (!isInit) {
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, AppConfig.DB_NAME, null);
            db = devOpenHelper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();

            isInit = true;
        }
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
