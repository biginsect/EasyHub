package com.biginsect.easyhub.ui.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author biginsect
 * @date 2018/8/22.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener,View.OnLongClickListener {
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public BaseViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public void onClick(View v) {
        if (null != onItemClickListener && getAdapterPosition() != RecyclerView.NO_POSITION){
            onItemClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return null != onItemLongClickListener && getAdapterPosition() != RecyclerView.NO_POSITION
                && onItemLongClickListener.onItemLongClick(getAdapterPosition(), v);
    }

    public interface OnItemClickListener{
        void onItemClick(int position, View view);
    }

    public interface OnItemLongClickListener{
        boolean onItemLongClick(int position, View view);
    }
}
