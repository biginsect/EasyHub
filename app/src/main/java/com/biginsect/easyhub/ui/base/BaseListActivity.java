package com.biginsect.easyhub.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author biginsect
 * @date 2018/8/23.
 */

public abstract class BaseListActivity<V extends IBaseContract.IView, P extends IBaseContract.IPresenter<V>, A extends BaseAdapter>
        extends BaseActivity<V, P>
        implements BaseViewHolder.OnItemLongClickListener, BaseViewHolder.OnItemClickListener{
    protected A mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = getAdapter();
        if (null != mAdapter){
            mAdapter.setOnItemClickListener(this);
            mAdapter.setOnItemLongClickListener(this);
        }
    }

    @Override
    abstract protected int getLayoutId();

    @Override
    public boolean onItemLongClick(int position, View view) {
        return false;
    }

    @Override
    public void onItemClick(int position, View view) {

    }


    /**
     * 获取适配器
     * @return target
     * */
    protected abstract A getAdapter();
}
