package com.lipeng.mygithub.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Fragment 统一基类，用于作统一处理
 * @author biginsect
 * @date 2017/12/26
 */

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        initView(mRootView);
        return mRootView;
    }

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

    protected void hideLoading(){

    }

    protected void showLoading(){

    }

    protected TextView getTextView(int id){
        return mRootView.findViewById(id);
    }

    protected Button getButton(int id){
        return mRootView.findViewById(id);
    }

    protected ImageView getImageView(int id){
        return mRootView.findViewById(id);
    }

    protected LinearLayout getLinearLayout(int id){
        return mRootView.findViewById(id);
    }

    protected RelativeLayout getRealtiveLayout(int id){
        return mRootView.findViewById(id);
    }

    protected RecyclerView getRecyclerView(int id){
        return mRootView.findViewById(id);
    }

    protected Toolbar getToolbar(int id){
        return mRootView.findViewById(id);
    }

}
