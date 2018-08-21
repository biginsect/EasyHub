package com.lipeng.mygithub.ui.presenter

import com.lipeng.mygithub.app.AppData
import com.lipeng.mygithub.base.MvpBasePresenter
import com.lipeng.mygithub.dao.AuthUser
import com.lipeng.mygithub.dao.AuthUserDao
import com.lipeng.mygithub.ui.contract.ISplashContract
import com.lipeng.mygithub.util.ListUtils

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