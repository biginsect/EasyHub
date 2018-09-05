package com.biginsect.easyhub.ui.contract

import com.biginsect.easyhub.ui.contract.base.IBaseContract

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