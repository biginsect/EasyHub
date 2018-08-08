package com.lipeng.mygithub.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lipeng.mygithub.base.mvp.MvpPresenter;
import com.lipeng.mygithub.base.mvp.MvpView;

import org.jetbrains.annotations.NotNull;

/**
 * @author biginsect
 * @date 2018/7/30
 */

public abstract class BaseMvpListActivity<V extends MvpView, P extends MvpPresenter<V>, A extends BaseAdapter>
        extends BaseMvpActivity<V,P>{
    protected A mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = getAdapter();
        if (null != mAdapter){
            mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(@NotNull Object tag) {
                    showDetail(tag);
                }
            });

            mAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(@NonNull Object tag) {
                    return onLongClickShow(tag);
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

    /**
     * 长按item响应
     * @param tag
     * @return <p>if false</p> 此次事件还被其他监听器响应
     * <p>if true</p> 此次事件已被消耗，无法响应其他监听器
     * */
    protected abstract boolean onLongClickShow(Object tag);

    /**
     * 获取适配器
     * @return target
     * */
    protected abstract A getAdapter();
}
