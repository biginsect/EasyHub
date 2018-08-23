package com.biginsect.easygithub.ui.contract

import com.biginsect.easygithub.ui.base.IBaseContract

/**
 * @author big insect
 */
interface ISplashContract {
    interface ISplashView :IBaseContract.IView{
        fun showHomePage()

    }

    interface ISplashPresenter: IBaseContract.IPresenter<ISplashView>{
        fun getUser()
    }
}