package com.biginsect.easygithub.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author biginsect
 * @date 2018/8/23.
 */

public abstract class BaseListActivity<V extends IBaseContract.IView, P extends IBaseContract.IPresenter<V>, A extends BaseAdapter>
        extends BaseActivity<V, P> {
    protected A mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = getAdapter();
        if (null != mAdapter){
            mAdapter.setOnItemClickListener(new BaseViewHolder.OnItemClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    showDetail(position, view);
                }
            });

            mAdapter.setOnItemLongClickListener(new BaseViewHolder.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(int position, View view) {
                    return onLongClickShow(position, view);
                }
            });
        }
    }

    @Override
    abstract protected int getLayoutId();

    /**
     * 点击列表项展示相关内容
     * @param position
     * @param view
     * */
    protected abstract void showDetail(int position, View view);

    /**
     * 长按item响应
     * @param position
     * @param view
     * @return <p>if false</p> 此次事件还被其他监听器响应
     * <p>if true</p> 此次事件已被消耗，无法响应其他监听器
     * */
    protected abstract boolean onLongClickShow(int position, View view);

    /**
     * 获取适配器
     * @return target
     * */
    protected abstract A getAdapter();
}
