package com.lipeng.mygithub.ui.presenter

import com.lipeng.mygithub.app.AppData
import com.lipeng.mygithub.base.MvpBasePresenter
import com.lipeng.mygithub.ui.contract.IHomePageContract

/**
 * @author big insect
 */
class HomePagePresenter : MvpBasePresenter<IHomePageContract.IHomePageView>() ,IHomePageContract.IHomePagePresenter {

    override fun logout() {
        daoSession.authUserDao.delete(AppData.authUser)
        AppData.authUser = null
        AppData.loggedUser = null
        if (isViewAttached){
            view.restartApp()
        }
    }
}