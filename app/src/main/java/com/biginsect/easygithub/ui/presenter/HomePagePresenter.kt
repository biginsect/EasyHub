package com.biginsect.easygithub.ui.presenter

import com.biginsect.easygithub.app.AppData
import com.biginsect.easygithub.ui.base.BasePresenter
import com.biginsect.easygithub.ui.contract.IHomePageContract

/**
 * @author big insect
 */
class HomePagePresenter : BasePresenter<IHomePageContract.IHomePageView>() ,IHomePageContract.IHomePagePresenter {

    override fun logout() {
        daoSession.authUserDao.delete(AppData.authUser)
        AppData.authUser = null
        AppData.loggedUser = null
        if (isViewAttached){
            view.restartApp()
        }
    }
}