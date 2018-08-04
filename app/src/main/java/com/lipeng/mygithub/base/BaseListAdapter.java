package com.lipeng.mygithub.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author li
 * @date 2018/6/19
 */

public abstract class BaseListAdapter<VH extends RecyclerView.ViewHolder, D extends Object> extends RecyclerView.Adapter{
    private Context mContext;
    private ArrayList<D> mDataList = new ArrayList<>();
    private OnItemClickListener mItemListener;

    public BaseListAdapter(Context context){
        mContext = context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return getViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (null != mItemListener){
            mItemListener.onItemClick(position);
        }
    }

    public void setOnItemListener(OnItemClickListener listener){
        mItemListener = listener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0:mDataList.size();
    }

    protected abstract VH getViewHolder(View itemView, int viewType);

    protected abstract int getLayoutId();

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
