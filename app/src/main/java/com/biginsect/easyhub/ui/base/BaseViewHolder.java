package com.biginsect.easyhub.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author biginsect
 * @date 2018/8/22.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener,View.OnLongClickListener {
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public BaseViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    @Override
    public void onClick(View v) {
        if (null != mOnItemClickListener && getAdapterPosition() != RecyclerView.NO_POSITION){
            mOnItemClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return null != mOnItemLongClickListener && getAdapterPosition() != RecyclerView.NO_POSITION
                && mOnItemLongClickListener.onItemLongClick(getAdapterPosition(), v);
    }

    public interface OnItemClickListener{
        void onItemClick(int position, View view);
    }

    public interface OnItemLongClickListener{
        boolean onItemLongClick(int position, View view);
    }
}
