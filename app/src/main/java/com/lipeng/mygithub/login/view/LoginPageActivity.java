package com.lipeng.mygithub.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.lipeng.mygithub.R;
import com.lipeng.mygithub.login.presenter.LoginPresenter;
import com.lipeng.mygithub.login.presenter.LoginPresenterImpl;
import com.lipeng.mygithub.ui.activity.HomePageActivity;
import com.lipeng.mygithub.util.NetworkUtils;
import com.lipeng.mygithub.util.PageSkipUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 登录页面，
 * @author lipeng
 * @date 2017/12/25
 */

public class LoginPageActivity extends AppCompatActivity implements View.OnClickListener, LoginView{
    @BindView(R.id.user_name_edit) EditText userNameEdit;
    @BindView(R.id.user_pass_word_edit) EditText userPasswordEdit;
    @BindView(R.id.login_btn)Button loginBtn;

    LoginPresenter loginPresenter;

    /**打印日志标识*/
    private final static String TAG = "LoginPageActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        init();
    }

    private void init(){
        ButterKnife.bind(this);

        loginBtn.setOnClickListener(this);
        findViewById(R.id.root).setOnClickListener(this);
        loginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                handleNetwork();
                login();
                break;
            case R.id.root:
                hideSoftKeyboard(v);
                break;
            default:
                break;
        }
    }

    /**
     * 检查网络状态，无连接则提示，有连接则跳转页面
     * */
    private void handleNetwork(){
        if (!NetworkUtils.isNetworkConnected(this)) {
            Toasty.error(this, "No network is connected!").show();
        }
    }

    /**
     * 登录
     * */
    private void login(){
        String getName = userNameEdit.getText().toString();
        String getPassword = userPasswordEdit.getText().toString();

        if (TextUtils.isEmpty(getName) || TextUtils.isEmpty(getPassword)){
            //用户和密码不能为空
            Toasty.error(this,
                    "name or password should not be null!").show();
        }else {//检查账号密码是否正确
            loginPresenter.setProgressBarVisibility(View.VISIBLE);
            loginPresenter.login(getName, getPassword);
        }
    }

    /**
     * 点击编辑框以外区域，隐藏软键盘
     * @param view 目标view
     * */
    private void hideSoftKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null){
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 测试，获取编辑框的hint，目标是实现hint上移的动画效果
     * */
    @OnClick(R.id.user_name_edit)
    public void userNameEditClicked(){
        String getUserNameHint = userNameEdit.getHint().toString();
        Log.d(TAG,"  --  --  "+getUserNameHint);
    }

    @Override
    public void loginResult(boolean result, String code) {
        loginPresenter.setProgressBarVisibility(View.INVISIBLE);
        if (result){
            PageSkipUtils.skipWithNoData(LoginPageActivity.this, HomePageActivity.class);
        }else {
            Toasty.error(this,
                    "username or password is valid").show();
        }
    }
}
