package com.lipeng.mygithub.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lipeng.mygithub.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * adapter基类
 * @author lipeng
 * @date 2018/1/8
 */

public abstract class AbstractAdapter<T> extends BaseAdapter {
    protected final ArrayList<T> mDataList = new ArrayList<>();

    public List<T> getDataList() {
        return mDataList;
    }

    public AbstractAdapter(){

    }

    /**
     * 添加数据
     * */
    public void appendToList(List<T> dataList){
        if (ListUtils.isEmpty(dataList)){
            mDataList.ensureCapacity(this.mDataList.size() + dataList.size());
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    /**
     * 刷新列表
     * */
    public void refresh(List<T> list){
        if (ListUtils.isEmpty(list)){
            mDataList.clear();
            mDataList.ensureCapacity(list.size());
            mDataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return ListUtils.getSize(mDataList);
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
        return getExtraView(position, convertView, parent);
    }

    /**
     * 自行实现具体视图内容
     * @param position 列表的子项在数据集中的位置
     * @param convertView 复用的视图
     * @param parent 父布局
     * @return 返回构建后的视图
     * */
    protected abstract View getExtraView(int position, View convertView, ViewGroup parent);
}
