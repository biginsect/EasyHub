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
    protected A adapter;

    @Override
    protected void initFragment(Bundle savedInstanceState) {
        adapter = getAdapter();
        if (null != adapter){
            adapter.setOnItemClickListener(this);
            adapter.setOnItemClickListener(this);
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
