package com.lipeng.mygithub.ui.contract

import com.lipeng.mygithub.base.mvp.MvpPresenter
import com.lipeng.mygithub.base.mvp.MvpView

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