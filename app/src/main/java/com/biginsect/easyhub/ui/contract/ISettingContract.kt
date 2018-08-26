package com.biginsect.easyhub.ui.contract

import com.biginsect.easyhub.ui.base.IBaseContract

/**
 * @author big insect
 * @date 2018/8/17.
 */
interface ISettingContract {
    interface ISettingView: IBaseContract.IView

    interface ISettingPresenter: IBaseContract.IPresenter<ISettingView> {
        /**
         * 退出登录
         * */
        fun logout()
    }
}