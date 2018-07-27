package com.lipeng.mygithub.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lipeng.mygithub.base.mvp.ActivityMvpDelegate;
import com.lipeng.mygithub.base.mvp.ActivityMvpDelegateImpl;
import com.lipeng.mygithub.base.mvp.MvpDelegateCallback;
import com.lipeng.mygithub.base.mvp.MvpPresenter;
import com.lipeng.mygithub.base.mvp.MvpView;

/**
 * @author big insect
 */

public abstract class BaseMvpActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends BaseActivity implements MvpView, MvpDelegateCallback<V, P>{
    protected ActivityMvpDelegate mMvpDelegate;
    protected P presenter;
    protected boolean retainInstance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMvpDelegate().onRestart();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        getMvpDelegate().onContentChanged();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getMvpDelegate().onPostCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public abstract P createPresenter();

    protected ActivityMvpDelegate<V, P> getMvpDelegate(){
        if (mMvpDelegate == null){
            mMvpDelegate = new ActivityMvpDelegateImpl(this, true, this);
        }
        return mMvpDelegate;
    }

    @Nullable
    @Override
    public P getPresenter() {
        return this.presenter;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public V getMvpView() {
        return (V)this;
    }
}
