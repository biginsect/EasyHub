package com.biginsect.easyhub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.biginsect.easyhub.R
import com.biginsect.easyhub.bean.response.AuthToken
import com.biginsect.easyhub.constant.ToastType
import com.biginsect.easyhub.ui.base.BaseActivity
import com.biginsect.easyhub.ui.contract.ILoginContract
import com.biginsect.easyhub.ui.presenter.LoginPresenter
import com.biginsect.easyhub.util.BlankUtils
import com.biginsect.easyhub.util.NetUtils
import com.biginsect.easyhub.util.ToastUtils
import kotlinx.android.synthetic.main.activity_login_page.*

/**
 * @author big insect
 * @date 2018/8/30.
 */
class LoginActivity :BaseActivity<ILoginContract.ILoginView, ILoginContract.ILoginPresenter>(),
        ILoginContract.ILoginView, View.OnClickListener{
    private lateinit var name :String
    private lateinit var pwd :String

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        til_username_wrapper.hint = "UserName"
        til_password_wrapper.hint = "Password"
        btn_login.setOnClickListener(this)
        ll_root.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login_page
    }

    override fun createPresenter(): ILoginContract.ILoginPresenter {
        presenter = LoginPresenter()
        return presenter
    }

    override fun onClick(v: View?) {
        val viewId = v?.id

        if (viewId == R.id.btn_login){
            login()
        }else if (viewId == R.id.ll_root){
            hideSoftKeyboard(v)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (null != intent){
            presenter.getToken(intent)
            setIntent(null)
        }
    }

    override fun onLoginSuccess() {
        finishDelay(1000)
        startActivity(Intent(activity, HomePageActivity::class.java))
    }

    override fun getTokenSuccess(authToken: AuthToken) {
        btn_login.doResult(true)
        presenter.getUserInfo(authToken)
    }

    override fun getTokenFailed(msg: String) {
        btn_login.doResult(false)
        Handler().postDelayed({
            btn_login.reset()
            btn_login.isEnabled = true
        }, 1000)
        ToastUtils.showShortToast(this, msg, ToastType.ERROR)
    }

    private fun login(){
        if (!NetUtils.isNetworkConnected(this)){
            ToastUtils.showLongToast(this, "Network is not connected!", ToastType.ERROR)
        }else if (checkLogin()){
            btn_login.isEnabled = false
            presenter.login(name, pwd)
        }else{
            btn_login.reset()
        }
    }

    private fun checkLogin():Boolean{
        var result = true
        name = et_user_name.text.toString()
        pwd = et_user_password.text.toString()

        if(BlankUtils.isBlankString(name)){
            result = false
            til_username_wrapper.error = getString(R.string.name_error)
        }else{
            til_username_wrapper.isErrorEnabled = false
        }

        if (BlankUtils.isBlankString(pwd)){
            result = false
            til_password_wrapper.error = getString(R.string.pass_word_error)
        }else{
            til_password_wrapper.isErrorEnabled = false
        }

        return result
    }
}