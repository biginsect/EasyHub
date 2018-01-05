package com.lipeng.mygithub.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.lipeng.mygithub.R;
import com.lipeng.mygithub.constant.ToastType;
import com.lipeng.mygithub.login.presenter.LoginPresenter;
import com.lipeng.mygithub.login.presenter.LoginPresenterImpl;
import com.lipeng.mygithub.login.view.LoginView;
import com.lipeng.mygithub.homepage.HomePageActivity;
import com.lipeng.mygithub.util.NetworkUtils;
import com.lipeng.mygithub.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 登录页面，
 * @author lipeng
 * @date 2017/12/25
 */

public class LoginPageActivity extends AppCompatActivity implements View.OnClickListener, LoginView {
    @BindView(R.id.et_user_name) EditText userNameEdit;
    @BindView(R.id.et_user_password) EditText userPasswordEdit;
    @BindView(R.id.btn_login)Button loginBtn;
    private TextInputLayout userNameWrapper;
    private TextInputLayout passwordWrapper;


    private LoginPresenter mLoginPresenter;

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

        setTextInputLayout();
        loginBtn.setOnClickListener(this);
        findViewById(R.id.root).setOnClickListener(this);
        mLoginPresenter = new LoginPresenterImpl(this);
    }

    /**
     * 设置用户名和密码编辑框hint提示的浮动动画
     * */
    private void setTextInputLayout(){
        userNameWrapper = findViewById(R.id.til_username_wrapper);
        passwordWrapper = findViewById(R.id.til_password_wrapper);
        userNameWrapper.setHint("UserName");
        passwordWrapper.setHint("Password");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                if (!NetworkUtils.isNetworkConnected(this)){
                    /*无网络连接*/
                    ToastUtils.showLongToast(this, "Network is not connected!",
                            ToastType.ERROR);
                }else {
                    //有网络连接
                    login();
                }
                break;
            case R.id.root:
                mLoginPresenter.hideSoftKeyboard(v);
                break;
            default:
                break;
        }
    }

    /**
     * 登录
     * */
    private void login(){
        /*防止NPE*/
        if (null != userNameWrapper.getEditText() && null != passwordWrapper.getEditText()){
            String getName = userNameWrapper.getEditText().getText().toString();
            String getPassword = passwordWrapper.getEditText().getText().toString();
            if (TextUtils.isEmpty(getName)){
                userNameWrapper.setError("User name should not be empty!");
            }else if (TextUtils.isEmpty(getPassword)){
                passwordWrapper.setError("Pass word should not be empty");
            }else {
                /*账号密码不为空，消除错误提示框*/
                userNameWrapper.setErrorEnabled(false);
                passwordWrapper.setErrorEnabled(false);
                mLoginPresenter.setProgressBarVisibility(View.VISIBLE);
                mLoginPresenter.login(getName, getPassword);
            }
        }
    }


    /**
     * 测试，获取编辑框的hint，目标是实现hint上移的动画效果
     * */
    @OnClick(R.id.et_user_name)
    public void userNameEditClicked(){
        String getUserNameHint = userNameEdit.getHint().toString();
        Log.d(TAG,"  --  --  "+getUserNameHint);
    }

    /**
     * 登录回调
     * */
    @Override
    public void onLoginResult(boolean result, String code) {
        mLoginPresenter.setProgressBarVisibility(View.INVISIBLE);
        if (result){
            mLoginPresenter.skipPage();
            finish();
        }else {
            ToastUtils.showLongToast(this,
                    "username or password is valid", ToastType.ERROR);
        }
    }

    /**
     * 关闭资源
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.destroy();
    }

    /**
     * presenter回调隐藏软键盘
     * 点击username和password两个编辑框外的地方可以隐藏软键盘，这两个EdiText会消费这个点击事件
     * */
    @Override
    public void onHideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null){
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示进度条
     * */
    @Override
    public void onSetProgressBar(int visibility) {

    }

    /**
     * 页面跳转至主页面（homepage）
     * */
    @Override
    public void onSkipPage() {
        HomePageActivity.skip(this);
    }
}
