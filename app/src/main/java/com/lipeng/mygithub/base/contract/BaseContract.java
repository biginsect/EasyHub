package com.lipeng.mygithub.base.contract;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * 用于fragment的mvp
 * @author lipeng
 * @date 2018/3/23
 */

public interface BaseContract {

    interface FragmentView{
        /**
         * 设置progressbar
         * */
        void setProgressBar();
        /**
         * 显示progressbar
         * */
        void showLoading();
        /**
         * 隐藏progressbar
         * */
        void hideLoading();
    }

    interface FragmentPresenter<V extends FragmentView>{
        /**
         * 存储数据
         * @param outState target
         * */
        void onSaveInstanceState(Bundle outState);

        /**
         * 恢复数据
         * @param outState target
         * */
        void onRestoreInstanceState(Bundle outState);

        /**
         * 设置V层
         * @param view target
         * */
        void attachView(@NonNull V view);

        /**
         * 解除V层，关闭资源
         * */
        void detachView();

        /**
         * 初始化布局
         * @param view target
         * */
        void onViewInit(View view);
    }
}
