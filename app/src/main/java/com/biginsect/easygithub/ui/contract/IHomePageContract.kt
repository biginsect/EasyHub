package com.biginsect.easygithub.ui.contract

import com.biginsect.easygithub.base.mvp.MvpPresenter
import com.biginsect.easygithub.base.mvp.MvpView

/**
 * @author big insect
 */
interface IHomePageContract {
    interface IHomePageView : MvpView{
        /**
         * 重启
         * */
        fun restartApp()
    }

    interface IHomePagePresenter : MvpPresenter<IHomePageView>{
        /**
         * 退出
         * */
        fun logout()
    }
}