package com.biginsect.easygithub.ui.presenter

import com.biginsect.easygithub.app.AppData
import com.biginsect.easygithub.ui.base.BasePresenter
import com.biginsect.easygithub.ui.contract.ISettingContract

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