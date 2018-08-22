package com.biginsect.easygithub.ui.presenter

import com.biginsect.easygithub.app.AppData
import com.biginsect.easygithub.base.MvpBasePresenter
import com.biginsect.easygithub.dao.AuthUser
import com.biginsect.easygithub.dao.AuthUserDao
import com.biginsect.easygithub.ui.contract.ISplashContract
import com.biginsect.easygithub.util.ListUtils

/**
 * @author big insect
 * @date 2018/8/21.
 */
class SplashPresenter: MvpBasePresenter<ISplashContract.ISplashView>(), ISplashContract.ISplashPresenter {

    /**缓存*/
    private lateinit var authUser: AuthUser

    private var isMainPageShowed: Boolean = false

    override fun getUser() {
        val authUserDao = daoSession.authUserDao
        val users = authUserDao.queryBuilder()
                .where(AuthUserDao.Properties.Selected.eq(true))
                .limit(1)
                .list()
        var selectedUser = if (!ListUtils.isEmpty(users)){users[0] }else {null }

        if (null == selectedUser){
            val firstAccount = authUserDao.queryBuilder()
                    .limit(1)
                    .list()
            selectedUser = if (!ListUtils.isEmpty(firstAccount)){ firstAccount[0]}else{ null}
        }

        if (null != selectedUser && isExpired(selectedUser)){
            authUserDao.delete(selectedUser)
            selectedUser = null
        }

        if (null != selectedUser){
            AppData.authUser = selectedUser
            /***/

        }else if (isViewAttached){
            view.showLoginPage()
        }
    }

    /**是否在职*/
    private fun isExpired(selectedUser: AuthUser): Boolean{
        return selectedUser.authTime.time + selectedUser.expireIn * 1000 < System.currentTimeMillis()
    }
}