package com.biginsect.easygithub.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.biginsect.easygithub.R;
import com.biginsect.easygithub.base.BaseAdapter;
import com.biginsect.easygithub.base.BaseViewHolder;
import com.biginsect.easygithub.bean.ProjectListUsers;
import com.biginsect.easygithub.view.CircleImageView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

/**
 * @author biginsect
 * @date 2017/12/26
 */

public class HomePageListAdapter extends BaseAdapter<HomePageListAdapter.ProjectListViewHolder, ProjectListUsers> {

    public HomePageListAdapter(Context context){
       super(context);
    }

    @NotNull
    @Override
    public ProjectListViewHolder getViewHolder(@NotNull View itemView) {
        return new ProjectListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull ProjectListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_homepage;
    }

    static class ProjectListViewHolder extends BaseViewHolder{
        /**用户头像*/
        @BindView(R.id.other_picture)  CircleImageView userPicture;
        /**用户名*/
        @BindView(R.id.other_name) TextView userName;
        /**用户动态信息的日期，以days为单位，超过30 days 用日期格式显示*/
        @BindView(R.id.other_update_date) TextView userUpdateDate;
        /**用户动态信息*/
        @BindView(R.id.his_dynamic) TextView userDynamic;

        public ProjectListViewHolder(View view){
            super(view);
        }
    }

}
