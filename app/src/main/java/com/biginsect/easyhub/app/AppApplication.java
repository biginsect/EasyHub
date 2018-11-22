package com.biginsect.easyhub.app;

import android.annotation.SuppressLint;
import android.app.Application;

import com.biginsect.easyhub.BuildConfig;
import com.biginsect.easyhub.dao.GreenDaoManager;
import com.biginsect.easyhub.util.NetUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @author biginsect
 */

public class AppApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Application instance;
    private final static String TAG = AppApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Long startTime = System.currentTimeMillis();
        AppData.INSTANCE.getSystemDefaultLocal();
        initLogger();
        NetUtils.INSTANCE.init(this);
        Logger.t(TAG).i("App start time : " + startTime);
        GreenDaoManager.getInstance().init(this);
    }

    public static Application getInstance() {
        return instance;
    }

    private void initLogger() {
        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(0)
                .tag("Easy_Logger")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

}
