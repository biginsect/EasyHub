package com.lipeng.mygithub.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lipeng.mygithub.base.contract.BaseContract;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment 统一基类，用于作统一处理
 * @author lipeng
 * @date 2017/12/26
 */

public abstract class BaseFragment<P extends BaseContract.FragmentPresenter> extends Fragment
    implements BaseContract.FragmentView{
    protected Context mContext;
    protected View mRootView;
    protected P mPresenter;
    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this,mRootView);
        if (null != mPresenter){
            mPresenter.onViewInit(mRootView);
        }
        return mRootView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != mPresenter){
            mPresenter.attachView(this);
        }
        if (null != mPresenter){
            mPresenter.onSaveInstanceState(getArguments());
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    /**
     * 获取布局文件id
     * @return id
     * */
    protected abstract int getLayoutId();

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
