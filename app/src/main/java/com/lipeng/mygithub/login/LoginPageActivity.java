package com.lipeng.mygithub.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lipeng.mygithub.R;
import com.lipeng.mygithub.base.BaseMvpActivity;
import com.lipeng.mygithub.constant.ToastType;
import com.lipeng.mygithub.contract.ILoginContract;
import com.lipeng.mygithub.login.presenter.LoginPresenter;
import com.lipeng.mygithub.util.NetUtils;
import com.lipeng.mygithub.util.ToastUtils;
import com.unstoppable.submitbuttonview.SubmitButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登录页面，
 * @author lipeng
 * @date 2017/12/25
 */

public class LoginPageActivity extends BaseMvpActivity<ILoginContract.ILoginView, ILoginContract.ILoginPresenter>
        implements View.OnClickListener, ILoginContract.ILoginView {
    @BindView(R.id.et_user_name) EditText userNameEdit;
    @BindView(R.id.et_user_password) EditText userPasswordEdit;
    @BindView(R.id.btn_login)SubmitButton loginBtn;
    private TextInputLayout userNameWrapper;
    private TextInputLayout passwordWrapper;
    String getName;
    String getPassword;

    /**打印日志标识*/
    private final static String TAG = "LoginPageActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
     protected void init(){
        ButterKnife.bind(this);

        setTextInputLayout();
        loginBtn.setOnClickListener(this);
        findViewById(R.id.root).setOnClickListener(this);
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
                    presenter.login(getName, getPassword);
                }else {
                    loginBtn.reset();
                }
                break;
            case R.id.root:
                presenter.hideSoftKeyboard(v);
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
        boolean checkResult = true;
        /*防止NPE*/
        if (null != userNameWrapper.getEditText() && null != passwordWrapper.getEditText()){
            getName = userNameWrapper.getEditText().getText().toString();
            getPassword = passwordWrapper.getEditText().getText().toString();
            if ((TextUtils.isEmpty(getName)) && TextUtils.isEmpty(getPassword)){
                ToastUtils.showLongToast(this, "UserName or password should not be null!",
                        ToastType.ERROR);
                checkResult = false;
            }
        }
        return checkResult;
    }

    /**
     * 登录回调
     * */
    @Override
    public void onLoginResult(boolean result, String code) {
        if (result){
            presenter.jump();
            finish();
        }else {
            ToastUtils.showLongToast(this,
                    "username or password is valid", ToastType.ERROR);
        }
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


    @NonNull
    @Override
    public ILoginContract.ILoginPresenter createPresenter() {
        presenter = new LoginPresenter();
        return presenter;
    }


    @Override
    public void onJump() {

    }
}
