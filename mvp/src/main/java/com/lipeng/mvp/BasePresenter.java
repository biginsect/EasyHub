package com.lipeng.mvp;

/**
 * 统一Presenter接口
 * @author lipeng-ds3
 * @date 2017/12/26
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private V mMvpView;


    @Override
    public void attachView(V view) {
        mMvpView = view;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public boolean isViewAttached(){
        return mMvpView != null;
    }

    public void checkViewAttached(){
        if (!isViewAttached()){
            throw new MvpViewNotAttachedException();
        }
    }

    public static class MvpViewNotAttachedException extends RuntimeException{
        public MvpViewNotAttachedException(){
            super("Should call Presenter.attachView() before requesting data .");
        }
    }
}
