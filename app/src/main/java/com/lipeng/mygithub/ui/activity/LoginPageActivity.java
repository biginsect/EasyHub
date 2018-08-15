package com.lipeng.mygithub.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lipeng.mygithub.R;
import com.lipeng.mygithub.base.BaseMvpActivity;
import com.lipeng.mygithub.bean.response.AuthResponse;
import com.lipeng.mygithub.constant.ToastType;
import com.lipeng.mygithub.ui.contract.ILoginContract;
import com.lipeng.mygithub.ui.presenter.LoginPresenter;
import com.lipeng.mygithub.util.NetUtils;
import com.lipeng.mygithub.util.ToastUtils;
import com.unstoppable.submitbuttonview.SubmitButton;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

/**
 * 登录页面，
 * @author biginsect
 * @date 2017/12/25
 */

public class LoginPageActivity extends BaseMvpActivity<ILoginContract.ILoginView, ILoginContract.ILoginPresenter>
        implements View.OnClickListener, ILoginContract.ILoginView {
    @BindView(R.id.et_user_name) EditText userNameEdit;
    @BindView(R.id.et_user_password) EditText userPasswordEdit;
    @BindView(R.id.btn_login)SubmitButton loginBtn;
    private TextInputLayout userNameWrapper;
    private TextInputLayout passwordWrapper;
    private String name;
    private String password;

    @Override
    protected void initView(Bundle savedInstanceState){
        super.initView(savedInstanceState);
        setTextInputLayout();
        loginBtn.setOnClickListener(this);
        findViewById(R.id.ll_root).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_page;
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
                if (!NetUtils.INSTANCE.isNetworkConnected(this)){
                    /*无网络连接*/
                    ToastUtils.showLongToast(this, "Network is not connected!",
                            ToastType.ERROR);
                }else if (loginCheck()){
                    //有网络连接
                    loginBtn.setEnabled(false);
                    presenter.login(name, password);
                }else {
                    loginBtn.reset();
                }
                break;
            case R.id.ll_root:
                hideSoftKeyboard(v);
                break;
            default:
                break;
        }
    }

    /**
     * 登录
     * */
    private boolean loginCheck(){
        /**登录验证结果*/
        boolean result = true;
        /*防止NPE*/
        name = userNameEdit.getText().toString();
        password = userPasswordEdit.getText().toString();
        if (TextUtils.isEmpty(name) || name.trim().equals("")){
            result = false;
            userNameWrapper.setError(getResources().getString(R.string.name_error));
        }else {
            userNameWrapper.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(password) || password.trim().equals("")){
            result = false;
            passwordWrapper.setError(getResources().getString(R.string.pass_word_error));
        }else{
            passwordWrapper.setErrorEnabled(false);
        }

        return result;
    }

    /**
     * 登录回调
     * */
    @Override
    public void onLoginResult(boolean result, @NonNull String code) {
        if (result){
            HomePageActivity.show(this);
            finish();
        }else {
            ToastUtils.showLongToast(this,
                    "username or password is valid", ToastType.ERROR);
        }
    }

    @Override
    public void getTokenSuccess(@NotNull AuthResponse authResponse) {
        loginBtn.doResult(true);
        presenter.getUserInfo(authResponse);
    }

    @Override
    public void getTokenFailed(@NotNull String msg) {
        loginBtn.doResult(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginBtn.reset();
                loginBtn.setEnabled(true);
            }
        }, 1000);

        ToastUtils.showShortToast(this, msg,ToastType.ERROR);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        presenter.getToken(intent);
        /**更新Intent，防止下次收到的是old data*/
        setIntent(null);
    }

    @NonNull
    @Override
    public ILoginContract.ILoginPresenter createPresenter() {
        presenter = new LoginPresenter();
        return presenter;
    }

    public static void show(Context context){
        context.startActivity(new Intent(context, LoginPageActivity.class));
    }

}
