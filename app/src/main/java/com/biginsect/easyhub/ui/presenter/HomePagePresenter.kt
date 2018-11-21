package com.biginsect.easyhub.ui.presenter

import com.biginsect.easyhub.app.AppData
import com.biginsect.easyhub.ui.contract.IHomePageContract
import com.biginsect.easyhub.ui.presenter.base.BasePresenter
import com.biginsect.easyhub.util.PreUtils

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

    override fun isFirstUseAndNoNewsUser(): Boolean {
        val user = AppData.loggedUser
        if (user?.following == 0 && user.publicGists == 0
                && user.publicGists == 0 && PreUtils.isFirstUse()){
            PreUtils.set(PreUtils.FIRST_USE, false)
            return true
        }

        return false
    }
}