package com.biginsect.easyhub.ui.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.biginsect.easyhub.constant.ToastType;
import com.biginsect.easyhub.mvp.BaseMvpFragment;
import com.biginsect.easyhub.ui.activity.LoginActivity;
import com.biginsect.easyhub.util.ToastUtils;
import com.thirtydegreesray.dataautoaccess.DataAutoAccess;

import org.jetbrains.annotations.NotNull;



/**
 * @author biginsect
 * @date 2017/12/26
 */

public abstract class BaseFragment<V extends IBaseContract.IView, P extends IBaseContract.IPresenter<V>>
        extends BaseMvpFragment<V, P> implements IBaseContract.IView{
    protected View mRootView;
    protected P presenter;
    private ProgressDialog mProgressDialog;

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
        initView(mRootView);
        initFragment(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        Intent intent = new Intent(getActivity(), LoginActivity.class);
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

    @NotNull
    @Override
    public ProgressDialog getProgressDialog(@NotNull String msg) {
        if (null == mProgressDialog){
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(msg);

        return mProgressDialog;
    }

    @Override
    public void showProgressDialog(@NotNull String msg) {
        getProgressDialog(msg);
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (null != mProgressDialog){
            mProgressDialog.dismiss();
        }
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
