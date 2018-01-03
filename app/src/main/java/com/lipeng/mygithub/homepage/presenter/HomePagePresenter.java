package com.lipeng.mygithub.homepage.presenter;

/**
 * 主页面的presenter
 * @author lipeng
 * @date 2018/1/3
 */

public interface HomePagePresenter {
    /**
     * 点击RecyclerView子项跳转至项目详情页面
     * */
    void skipToProjectDetail();

    /**
     * 销毁资源
     * */
    void destroy();
}
