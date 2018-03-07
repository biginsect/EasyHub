package com.lipeng.mygithub.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * adapter基类
 * @author lipeng
 * @date 2018/1/8
 */

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected final ArrayList<T> mDataList = new ArrayList<>();

    public List<T> getDataList() {
        return mDataList;
    }

    public BaseAdapter(){

    }

    /**
     * 添加数据
     * */
    public void appendToList(List<T> dataList){
        if (null != dataList && !dataList.isEmpty()){
            mDataList.ensureCapacity(this.mDataList.size() + dataList.size());
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    /**
     * 刷新列表
     * */
    public void refresh(List<T> list){
        if (null != list && !list.isEmpty()){
            mDataList.clear();
            mDataList.ensureCapacity(list.size());
            mDataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < this.mDataList.size()){
            return this.mDataList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getExView(position, convertView, parent);
    }

    /**
     * 自行实现具体视图内容
     * @param position 列表的子项在数据集中的位置
     * @param convertView 复用的视图
     * @param parent 父布局
     * @return 返回构建后的视图
     * */
    protected abstract View getExView(int position, View convertView, ViewGroup parent);
}
