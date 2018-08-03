package com.lipeng.mygithub.ui.contract

import com.lipeng.mygithub.base.mvp.MvpPresenter
import com.lipeng.mygithub.base.mvp.MvpView

/**
 * @author big insect
 */
interface ISplashContract {
    interface ISplashView :MvpView{
        fun showMainPage()
    }

    interface ISplashPresenter: MvpPresenter<ISplashView>{
        fun getUser()
    }
}