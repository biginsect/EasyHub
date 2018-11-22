package com.biginsect.easyhub.ui.contract

import com.biginsect.easyhub.ui.contract.base.IBaseContract

/**
 * @author big insect
 * @date 2018/10/8.
 */

interface IActivityContract {
    interface IActivityView : IBaseContract.IView

    interface IActivityPresenter : IBaseContract.IPresenter<IActivityView>
}