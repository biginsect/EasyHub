package com.lipeng.mvp.lce;

import android.support.annotation.UiThread;

import com.lipeng.mvp.MvpView;

/**
 * 内容的展示大多是基于网络数据，此通用接口用于处理网络请求
 * @author lipeng
 * @date 2017/12/26
 */

public interface LceView<M> extends MvpView {
    /**
     * 正在加载数据，显示progressbar或提示框等内容
     * */
    @UiThread
    void showLoading();

    /**
     * 加载完成，消除提示框和progressbar
     * */
    @UiThread
    void dismissLoading();

    /**
     * 加载完成，显示内容
     * */
    @UiThread
    void showContent(M data);

    /**
     * 加载失败
     * */
    @UiThread
    void showError(Throwable e);
}
