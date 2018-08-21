package com.lipeng.mygithub.ui.presenter

import com.lipeng.mygithub.app.AppData
import com.lipeng.mygithub.base.MvpBasePresenter
import com.lipeng.mygithub.ui.contract.ISettingContract

/**
 * @author big insect
 * @date 2018/8/21.
 */
class SettingPresenter: MvpBasePresenter<ISettingContract.ISettingView>(), ISettingContract.ISettingPresenter{

    override fun logout() {
        daoSession.authUserDao.delete(AppData.authUser)
        AppData.authUser = null
        AppData.loggedUser = null
        if (isViewAttached){
            view.showLoginPage()
        }
    }
}