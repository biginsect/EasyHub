package com.lipeng.mygithub.homepage.presenter;

import com.lipeng.mygithub.homepage.view.HomePageView;

/**
 * 主页面presenter实现类
 * @author lipeng
 * @date 2018/1/3
 */

public class HomePagePresenterImpl implements HomePagePresenter {
    private HomePageView homePageView;

    public HomePagePresenterImpl(HomePageView homePageView){
        this.homePageView = homePageView;
    }

    /**
     * 页面跳转
     * */
    @Override
    public void skipToProjectDetail() {
        homePageView.onSkipToProjectDetail();
    }

    @Override
    public void destroy() {
        homePageView = null;
    }
}
