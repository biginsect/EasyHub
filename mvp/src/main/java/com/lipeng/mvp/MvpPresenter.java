package com.lipeng.mvp;

import android.support.annotation.UiThread;

/**
 * Presenter
 * @author lipeng
 * @date 2017/12/26
 */

public interface MvpPresenter<V extends MvpView> {
    /**
     * 数据集或者事件到达此presenter时调用的方法
     * @param view 目标
     * */
    @UiThread
    void attachView(V view);

    /**
     * view销毁则会调用，通常会被<code>Activity.detachView()</code>或者<code>Fragment.onDestroy()</code>
     * 这两个方法调用
     * */
    @UiThread
    void detachView();
}
