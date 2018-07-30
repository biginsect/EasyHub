package com.lipeng.mygithub.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lipeng.mygithub.base.mvp.MvpPresenter;
import com.lipeng.mygithub.base.mvp.MvpView;

import org.jetbrains.annotations.NotNull;

/**
 * @author biginsect
 * @date 2018/7/30
 */

public abstract class BaseMvpListActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends BaseMvpActivity<V,P>{
    protected BaseAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != mAdapter){
            mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(@NotNull Object tag) {
                    showDetail(tag);
                }
            });
        }
    }

    /**
     * 布局id
     * @return id
     * */
    @Override
    protected abstract int getLayoutId() ;

    /**
     * 点击列表项展示相关内容
     * @param tag 传入的相关tag
     * */
    protected abstract void showDetail(Object tag);
}
