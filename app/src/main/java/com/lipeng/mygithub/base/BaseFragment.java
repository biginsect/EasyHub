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

import com.lipeng.mygithub.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author biginsect
 * @date 2017/12/26
 */

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected View mRootView;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        initView(mRootView);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id){
        return (T)mRootView.findViewById(id);
    }

}
