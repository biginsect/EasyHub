package com.lipeng.mygithub.detailpage.presenter;

import com.lipeng.mygithub.detailpage.view.DetailPageView;

/**
 * 详情页presenter实现类
 * @author lipeng
 * @date 2018/1/3
 */

public class DetailPagePresenterImpl implements DetailPagePresenter {
    private DetailPageView detailPageView;

    public DetailPagePresenterImpl(DetailPageView detailPageView){
        this.detailPageView = detailPageView;
    }

    @Override
    public void star() {
        detailPageView.onStar();
    }

    @Override
    public void destroy() {
        detailPageView = null;
    }
}
