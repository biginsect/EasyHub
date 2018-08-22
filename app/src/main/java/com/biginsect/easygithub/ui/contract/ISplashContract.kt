package com.biginsect.easygithub.ui.contract

import com.biginsect.easygithub.base.mvp.MvpPresenter
import com.biginsect.easygithub.base.mvp.MvpView

/**
 * @author big insect
 */
interface ISplashContract {
    interface ISplashView :MvpView{
        fun showMainPage()

        fun showLoginPage()

        fun showErrorTips(msg: String)
    }

    interface ISplashPresenter: MvpPresenter<ISplashView>{
        fun getUser()
    }
}