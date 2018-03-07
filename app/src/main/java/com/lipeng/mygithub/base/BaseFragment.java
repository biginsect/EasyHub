package com.lipeng.mygithub.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment 统一基类，用于作统一处理
 * @author lipeng
 * @date 2017/12/26
 */

public abstract class BaseFragment extends Fragment{
    protected Context mContext;
    protected View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        initView(mRootView);
        return mRootView;
    }

    /**
     * 获取布局文件id
     * @return id
     * */
    protected abstract int getLayoutId();

    /**
     * 对view做进一步处理
     * @param parentView target
     * */
    protected abstract void initView(View parentView);
}
