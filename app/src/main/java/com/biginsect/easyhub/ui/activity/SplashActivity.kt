package com.biginsect.easyhub.ui.activity

import android.content.Intent
import android.os.Bundle
import com.biginsect.easyhub.ui.base.BaseActivity
import com.biginsect.easyhub.ui.contract.ISplashContract
import com.biginsect.easyhub.ui.presenter.SplashPresenter

/**
 * @author big insect
 * @date 2018/8/30.
 */
class SplashActivity :BaseActivity<ISplashContract.ISplashView, ISplashContract.ISplashPresenter>(),
        ISplashContract.ISplashView{

    override fun showHomePage() {
        finishDelay()
        val uri = intent.data
        if(null == uri){
            startActivity(Intent(activity, HomePageActivity::class.java))
        }
    }

    override fun createPresenter(): ISplashContract.ISplashPresenter {
        presenter = SplashPresenter()
        return presenter
    }

    override fun getLayoutId(): Int {
        return 0
    }

    override fun initActivity() {
        super.initActivity()
        presenter.getUser()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    override fun showLoginPage() {
        finishDelay()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}