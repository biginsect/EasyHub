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

    private static DaoSession daoSession;
    private static DaoMaster daoMaster;
    private static SQLiteDatabase db;
    /**是否已经初始化*/
    private boolean isInit;

    private GreenDaoManager(){
    }

    private static final class DaoManagerHolder{
        private static final GreenDaoManager INSTANCE = new GreenDaoManager();
    }

    /**
     * 初始化，放到application的onCreate()方法
     * */
    public void init(Context context){
        if (!isInit) {
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, AppConfig.DB_NAME, null);
            db = devOpenHelper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();

            isInit = true;
        }
    }

    /**调用之前必须进行初始化*/
    public static GreenDaoManager getInstance(){
        return DaoManagerHolder.INSTANCE;
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
