package com.biginsect.easyhub.ui.base;


import android.os.Bundle;
import android.view.View;

/**
 * @author biginsect
 * @date 2018/8/31.
 */

public abstract class BaseListFragment <V extends IBaseContract.IView, P extends IBaseContract.IPresenter<V>, A extends BaseAdapter>
        extends BaseFragment<V, P> implements IBaseContract.IView,
        BaseViewHolder.OnItemClickListener,
        BaseViewHolder.OnItemLongClickListener{
    protected A mAdapter;

    @Override
    protected void initFragment(Bundle savedInstanceState) {
        mAdapter = getAdapter();
        if (null != mAdapter){
            mAdapter.setOnItemClickListener(this);
            mAdapter.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(int position, View view) {

    }

    @Override
    public boolean onItemLongClick(int position, View view) {
        return false;
    }

    @Override
    protected abstract int getLayoutId();

    protected abstract A getAdapter();
}
