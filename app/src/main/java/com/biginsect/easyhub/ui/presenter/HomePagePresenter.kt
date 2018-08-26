package com.biginsect.easyhub.ui.presenter

import com.biginsect.easyhub.app.AppData
import com.biginsect.easyhub.ui.base.BasePresenter
import com.biginsect.easyhub.ui.contract.IHomePageContract

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