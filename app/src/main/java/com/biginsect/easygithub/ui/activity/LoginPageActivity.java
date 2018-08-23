package com.biginsect.easygithub.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.biginsect.easygithub.R;
import com.biginsect.easygithub.bean.response.AuthToken;
import com.biginsect.easygithub.constant.ToastType;
import com.biginsect.easygithub.ui.base.BaseActivity;
import com.biginsect.easygithub.ui.contract.ILoginContract;
import com.biginsect.easygithub.ui.presenter.LoginPresenter;
import com.biginsect.easygithub.util.BlankUtils;
import com.biginsect.easygithub.util.NetUtils;
import com.biginsect.easygithub.util.ToastUtils;
import com.unstoppable.submitbuttonview.SubmitButton;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

/**
 * @author biginsect
 * @date 2017/12/25
 */

public class LoginPageActivity extends BaseActivity<ILoginContract.ILoginView, ILoginContract.ILoginPresenter>
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
        int viewId = v.getId();
        if (viewId == R.id.btn_login){
            if (!NetUtils.INSTANCE.isNetworkConnected(this)){
                ToastUtils.INSTANCE.showLongToast(this, "Network is not connected!",
                        ToastType.ERROR);
            }else if (loginCheck()){
                //有网络连接
                loginBtn.setEnabled(false);
                presenter.login(name, password);
            }else {
                loginBtn.reset();
            }
        }else if (viewId == R.id.ll_root){
            hideSoftKeyboard(v);
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
        if (BlankUtils.INSTANCE.isBlankString(name)){
            result = false;
            userNameWrapper.setError(getResources().getString(R.string.name_error));
        }else {
            userNameWrapper.setErrorEnabled(false);
        }

        if (BlankUtils.INSTANCE.isBlankString(name)){
            result = false;
            passwordWrapper.setError(getResources().getString(R.string.pass_word_error));
        }else{
            passwordWrapper.setErrorEnabled(false);
        }

        return result;
    }

    @Override
    public void getTokenSuccess(@NotNull AuthToken authToken) {
        loginBtn.doResult(true);
        presenter.getUserInfo(authToken);
    }

    @Override
    public void onLoginSuccess() {
        finishDelay(800);
        HomePageActivity.show(getActivity());
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

        ToastUtils.INSTANCE.showShortToast(this, msg,ToastType.ERROR);
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
