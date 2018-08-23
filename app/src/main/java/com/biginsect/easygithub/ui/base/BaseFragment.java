package com.biginsect.easygithub.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.biginsect.easygithub.constant.ToastType;
import com.biginsect.easygithub.mvp.BaseMvpFragment;
import com.biginsect.easygithub.ui.activity.LoginPageActivity;
import com.biginsect.easygithub.util.ToastUtils;
import com.thirtydegreesray.dataautoaccess.DataAutoAccess;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author biginsect
 * @date 2017/12/26
 */

public abstract class BaseFragment<V extends IBaseContract.IView, P extends IBaseContract.IPresenter<V>>
        extends BaseMvpFragment<V, P> implements IBaseContract.IView{
    protected View mRootView;
    private Unbinder unbinder;
    protected P presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataAutoAccess.getData(this, savedInstanceState);
        DataAutoAccess.getData(this, getArguments());
        if (null != presenter){
            presenter.onRestoreInstanceState(savedInstanceState);
            presenter.onRestoreInstanceState(getArguments());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        DataAutoAccess.saveData(this, outState);
        if (null != presenter){
            presenter.onSaveInstanceState(outState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        initView(mRootView);
        initFragment(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public P getPresenter() {
        return presenter;
    }

    protected abstract void initFragment(Bundle savedInstanceState);

    /**
     * 初始化布局
     * @param view
     * */
    protected abstract void initView(View view);

    /**
     * 布局Id
     * @return id
     * */
    protected abstract int getLayoutId();

    @Override
    abstract public P createPresenter();

    @Override
    public void showLoginPage() {
        getActivity().finishAffinity();
        Intent intent = new Intent(getActivity(), LoginPageActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(@NotNull String msg) {
        ToastUtils.INSTANCE.showShortToast(getActivity(), msg, ToastType.ERROR);
    }

    @Override
    public void showInfo(@NotNull String msg) {
        ToastUtils.INSTANCE.showShortToast(getActivity(), msg, ToastType.INFO);
    }

    @Override
    public void showWarning(@NotNull String msg) {
        ToastUtils.INSTANCE.showShortToast(getActivity(), msg, ToastType.WARNING);
    }

    @Override
    public void showSuccess(@NotNull String msg) {
        ToastUtils.INSTANCE.showShortToast(getActivity(), msg, ToastType.SUCCESS);
    }

    @Override
    public void hideLoading(){

    }

    @Override
    public void showLoading(){

    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id){
        return (T)mRootView.findViewById(id);
    }

}
