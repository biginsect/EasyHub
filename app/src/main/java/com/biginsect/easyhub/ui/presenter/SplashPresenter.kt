package com.biginsect.easyhub.ui.presenter

import com.biginsect.easyhub.app.AppData
import com.biginsect.easyhub.bean.User
import com.biginsect.easyhub.dao.AuthUser
import com.biginsect.easyhub.dao.AuthUserDao
import com.biginsect.easyhub.http.base.HttpObserver
import com.biginsect.easyhub.http.base.HttpResponse
import com.biginsect.easyhub.http.base.IObservableCreator
import com.biginsect.easyhub.ui.contract.ISplashContract
import com.biginsect.easyhub.ui.presenter.base.BasePresenter
import com.biginsect.easyhub.util.ListUtils
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import retrofit2.Response
import java.util.*

/**
 * @author big insect
 * @date 2018/8/21.
 */
class SplashPresenter : BasePresenter<ISplashContract.ISplashView>(), ISplashContract.ISplashPresenter {

    /**缓存*/
    private lateinit var authUser: AuthUser

    private var isMainPageShowed: Boolean = false

    override fun getUser() {
        val authUserDao = daoSession.authUserDao
        val users = authUserDao.queryBuilder()
                .where(AuthUserDao.Properties.Selected.eq(true))
                .limit(1)
                .list()
        var selectedUser = if (!ListUtils.isEmpty(users)) {
            users[0]
        } else {
            null
        }

        if (null == selectedUser) {
            val firstAccount = authUserDao.queryBuilder()
                    .limit(1)
                    .list()
            selectedUser = if (!ListUtils.isEmpty(firstAccount)) {
                firstAccount[0]
            } else {
                null
            }
        }

        if (null != selectedUser && isExpired(selectedUser)) {
            authUserDao.delete(selectedUser)
            selectedUser = null
        }

        if (null != selectedUser) {
            AppData.authUser = selectedUser
            /***/
            getUserInfo()
        } else if (isViewAttached) {
            view.showLoginPage()
        }
    }

    private fun getUserInfo() {
        val userSubscribe = object : HttpObserver<User> {
            override fun onError(error: Throwable) {
                daoSession.authUserDao.delete(AppData.authUser)
                AppData.authUser = null
                if (isViewAttached) {
                    view.showError(getErrorMsg(error))
                    view.showLoginPage()
                }
            }

            override fun onSuccess(response: HttpResponse<User>) {
                val user = response.body()
                if (null != authUser && null != user) {
                    authUser.loginId = user.login
                    daoSession.authUserDao.update(authUser)
                }

                if (!isMainPageShowed) {
                    isMainPageShowed = true
                }

                if (isViewAttached) {
                    view.showHomePage()
                }
            }

            override fun onSubscribe(d: Disposable) {
                registerDisposable(d)
            }
        }

        executeRxHttp(object : IObservableCreator<User> {
            override fun create(forceNetwork: Boolean): Observable<Response<User>> {
                return userService.getUserInfo(forceNetwork)
            }
        }, userSubscribe, true)
    }

    /**是否在职*/
    private fun isExpired(selectedUser: AuthUser): Boolean {
        return selectedUser.authTime.time + selectedUser.expireIn * 1000 < System.currentTimeMillis()
    }

    private fun saveAceesToken(accessToken: String, scope: String, expireIn: Int) {
        val authUser = AuthUser()
        authUser.selected = true
        authUser.scope = scope
        authUser.expireIn = expireIn
        authUser.authTime = Date()
        authUser.accessToken = accessToken
        daoSession.authUserDao.insert(authUser)
        this.authUser = authUser
    }
}