package com.lipeng.mygithub.ui.contract

import com.lipeng.mygithub.base.mvp.MvpPresenter
import com.lipeng.mygithub.base.mvp.MvpView

/**
 * @author lipeng-ds3
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