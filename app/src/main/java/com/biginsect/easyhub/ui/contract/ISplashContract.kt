package com.biginsect.easyhub.ui.contract

import com.biginsect.easyhub.ui.contract.base.IBaseContract

/**
 * @author big insect
 */
interface ISplashContract {
    interface ISplashView : IBaseContract.IView {
        fun showHomePage()

    }

    interface ISplashPresenter : IBaseContract.IPresenter<ISplashView> {
        fun getUser()
    }
}