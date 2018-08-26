package com.biginsect.easyhub.ui.presenter

import com.biginsect.easyhub.app.AppData
import com.biginsect.easyhub.ui.base.BasePresenter
import com.biginsect.easyhub.ui.contract.ISettingContract

/**
 * @author big insect
 * @date 2018/8/21.
 */
class SettingPresenter: BasePresenter<ISettingContract.ISettingView>(), ISettingContract.ISettingPresenter{

    override fun logout() {
        daoSession.authUserDao.delete(AppData.authUser)
        AppData.authUser = null
        AppData.loggedUser = null
        if (isViewAttached){
            view.showLoginPage()
        }
    }
}