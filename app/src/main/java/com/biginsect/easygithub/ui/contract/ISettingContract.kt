package com.biginsect.easygithub.ui.contract

import com.biginsect.easygithub.base.mvp.MvpPresenter
import com.biginsect.easygithub.base.mvp.MvpView

/**
 * @author big insect
 * @date 2018/8/17.
 */
interface ISettingContract {
    interface ISettingView: MvpView {
        fun showLoginPage()
    }

    interface ISettingPresenter: MvpPresenter<ISettingView> {
        /**
         * 退出登录
         * */
        fun logout()
    }
}