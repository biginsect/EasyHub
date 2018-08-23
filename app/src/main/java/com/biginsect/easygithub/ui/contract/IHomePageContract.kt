package com.biginsect.easygithub.ui.contract

import com.biginsect.easygithub.ui.base.IBaseContract

/**
 * @author big insect
 */
interface IHomePageContract {
    interface IHomePageView : IBaseContract.IView{
        /**
         * 重启
         * */
        fun restartApp()
    }

    interface IHomePagePresenter : IBaseContract.IPresenter<IHomePageView>{
        /**
         * 退出
         * */
        fun logout()
    }
}