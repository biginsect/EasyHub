package com.lipeng.mygithub.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;

import com.lipeng.mygithub.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * 主页面
 * @author lipeng
 * @date 2017/12/22
 * */
public class HomePageActivity extends BaseActivity implements View.OnClickListener{
    /**记录按下返回键的时间*/
    private long mLastBackPressedTime = 0;
    /**两次back键间隔时间*/
    private final static long TIME_INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initView();
    }

    /**
     * 初始化布局文件 view等
     * */
    private void initView(){
        setActionbar();
    }

    private void setActionbar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setCustomView(R.layout.home_page_actionbar);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        /*两次按下back键的时间小于1s，则finish*/
        if (System.currentTimeMillis() - mLastBackPressedTime < TIME_INTERVAL){
            finish();
        }else {
            mLastBackPressedTime = System.currentTimeMillis();
            Toasty.info(this,"Press again to exit.").show();
        }
    }
}
