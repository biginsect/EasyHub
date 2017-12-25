package com.lipeng.mygithub.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lipeng.mygithub.R;
import com.lipeng.mygithub.util.NetworkUtils;
import com.lipeng.mygithub.util.PageSkipUtils;
import com.lipeng.mygithub.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登录页面，
 * @author lipeng
 * @date 2017/12/25
 */

public class LoginPage extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.user_name_edit) EditText userNameEdit;
    @BindView(R.id.user_pass_word_edit) EditText userPasswordEdit;
    @BindView(R.id.login_btn)Button loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ButterKnife.bind(this);

        loginBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                handleNetwork();
                login();
                break;
            default:
                break;
        }
    }

    /**
     * 检查网络状态，无连接则提示，有连接则跳转页面
     * */
    private void handleNetwork(){
        if (!NetworkUtils.isNetwrokConnected(this)) {
            ToastUtils.showShortToast(this, "No network is connected!");
        }
    }

    /**
     * 检查账号密码是否正确
     * */
    private void login(){
        String name = "123";
        String password = "123";
        String getName = userNameEdit.getText().toString();
        String getPassword = userPasswordEdit.getText().toString();

        if (TextUtils.isEmpty(getName) || TextUtils.isEmpty(getPassword)){
            ToastUtils.showShortToast(this, "name or password should not be null!");
        }else {//检查账号密码是否正确
            if (name.equals(getName) || password.equals(getPassword)){
                //全部正确，页面跳转
                PageSkipUtils.skipWithNoData(LoginPage.this, HomePageActivity.class);
            }else {
                ToastUtils.showLongToast(this, "username or password is valid");
            }
        }
    }
}
