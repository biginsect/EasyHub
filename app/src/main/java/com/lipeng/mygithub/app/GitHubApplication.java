package com.lipeng.mygithub.app;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lipeng.mygithub.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @author lipeng
 * @date 2017/12/28
 */

public class GitHubApplication extends Application{
    private final static String TAG = GitHubApplication.class.getSimpleName();
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        application = this;
        long appStartTime = System.currentTimeMillis();
        initLogger();
        Logger.t(TAG).i("App start time :", appStartTime);
    }

    /**
     * 初始化Logger配置
     * */
    private void initLogger(){
        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(0)
                .tag("MyGitHub_Logger")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }

            @Override
            public void log(int priority, @Nullable String tag, @NonNull String message) {
                super.log(priority, tag, message);
            }
        });
    }

    public static Application getInstance(){
        return application;
    }
}
