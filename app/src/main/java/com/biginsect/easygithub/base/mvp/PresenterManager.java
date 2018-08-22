package com.biginsect.easygithub.base.mvp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

/**
 * @author big insect
 */

public final class PresenterManager {
    private static boolean DEBUG = false;
    private static final String DEBUG_TAG = "PresenterManager";
    private static final String KEY_ACTIVITY_ID = "com.hannesdorfmann.mosby3.MosbyPresenterManagerActivityId";
    private static final Map<Activity, String> ACTIVITY_ID_MAP = new ArrayMap<>();
    private static final Map<String, ActivityScopedCache> ACTIVITY_SCOPED_CACHE_MAP = new ArrayMap<>();
    private static final Application.ActivityLifecycleCallbacks ACTIVITY_LIFECYCLE_CALLBACKS = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (savedInstanceState != null){
                String activityId = savedInstanceState.getString(KEY_ACTIVITY_ID);
                if (null != activityId){
                    ACTIVITY_ID_MAP.put(activity, activityId);
                }
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            String activityId = ACTIVITY_ID_MAP.get(activity);
            if (null != activityId){
                outState.putString(KEY_ACTIVITY_ID, activityId);
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (!activity.isChangingConfigurations()){
                String activityId = ACTIVITY_ID_MAP.get(activity);
                if (null != activityId){
                    ActivityScopedCache scopedCache = ACTIVITY_SCOPED_CACHE_MAP.get(activityId);
                    if (null != scopedCache){
                        scopedCache.clear();
                        ACTIVITY_SCOPED_CACHE_MAP.remove(activityId);
                    }

                    if (ACTIVITY_SCOPED_CACHE_MAP.isEmpty()){
                        activity.getApplication().unregisterActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE_CALLBACKS);
                        if (DEBUG){
                            Log.d(DEBUG_TAG, "unregisterActivityLifecycleCallbacks");
                        }
                    }
                }
            }

            ACTIVITY_ID_MAP.remove(activity);
        }
    };

    private PresenterManager(){
        throw new UnsupportedOperationException("Can not init");
    }

    @NonNull
    @MainThread
    static ActivityScopedCache getOrCreateActivityScopedCache(@NonNull Activity activity){
        String activityId = ACTIVITY_ID_MAP.get(activity);
        if (null == activityId){
            activityId = UUID.randomUUID().toString();
            ACTIVITY_ID_MAP.put(activity, activityId);
            if (ACTIVITY_ID_MAP.size() == 1){
                activity.getApplication().registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE_CALLBACKS);
                if (DEBUG){
                    Log.d(DEBUG_TAG, "registerActivityLifecycleCallbacks ");
                }
            }
        }

        ActivityScopedCache activityScopedCache = ACTIVITY_SCOPED_CACHE_MAP.get(activityId);
        if (null == activityScopedCache){
            activityScopedCache = new ActivityScopedCache();
            ACTIVITY_SCOPED_CACHE_MAP.put(activityId, activityScopedCache);
        }

        return activityScopedCache;
    }

    @NonNull
    @MainThread
    static ActivityScopedCache getActivityScoped(@NonNull Activity activity){
        String activityId = ACTIVITY_ID_MAP.get(activity);
        return ACTIVITY_SCOPED_CACHE_MAP.get(activityId);
    }

    @Nullable
    public static <P> P getPresenter(@NonNull Activity activity, @NonNull String viewId){
        ActivityScopedCache scopedCache = getActivityScoped(activity);
        return  scopedCache.getPresenter(viewId);
    }

    @NonNull
    public static <VS> VS getViewState(@NonNull Activity activity, @NonNull String viewId){
        ActivityScopedCache scopedCache = getActivityScoped(activity);
        return scopedCache.getViewState(viewId);
    }

    @NonNull
    public static Activity getActivity(@NonNull Context context){
        if (context instanceof Activity){
            return (Activity) context;
        }else {
            while (context instanceof ContextWrapper){
                context = ((ContextWrapper) context).getBaseContext();
            }

            throw new IllegalStateException("Could not find the surrounding Activity");
        }
    }

    static void reset(){
        ACTIVITY_ID_MAP.clear();

        for (ActivityScopedCache scopedCache : ACTIVITY_SCOPED_CACHE_MAP.values()) {
            scopedCache.clear();
        }
    }

    public static void putPresenter(Activity activity, @NonNull String viewId, @NonNull MvpPresenter<? extends MvpView> presenter){
        if (activity == null){
            throw new NullPointerException("Activity is Null");
        }

        ActivityScopedCache scopedCache = getOrCreateActivityScopedCache(activity);
        scopedCache.putPresenter(viewId, presenter);
    }

    public static void putViewState( Activity activity, @NonNull String viewId, @NonNull Object viewState){
        if (activity == null){
            throw new NullPointerException("Activity is Null");
        }
        ActivityScopedCache scopedCache = getOrCreateActivityScopedCache(activity);
        scopedCache.putViewState(viewId, viewState);
    }

    public static void remove(Activity activity, String viewId){
        if (activity == null ){
            throw new NullPointerException("Activity is Null");
        }
        ActivityScopedCache scopedCache = getActivityScoped(activity);
        scopedCache.remove(viewId);
    }
}
