package com.biginsect.easyhub.ui.presenter

import android.content.Intent
import com.biginsect.easyhub.app.AppConfig
import com.biginsect.easyhub.app.AppData
import com.biginsect.easyhub.bean.User
import com.biginsect.easyhub.bean.request.AuthRequest
import com.biginsect.easyhub.bean.response.AuthToken
import com.biginsect.easyhub.bean.response.OAuthToken
import com.biginsect.easyhub.dao.AuthUser
import com.biginsect.easyhub.dao.AuthUserDao
import com.biginsect.easyhub.http.api.LoginService
import com.biginsect.easyhub.http.base.GitHubRetrofit
import com.biginsect.easyhub.http.base.HttpObserver
import com.biginsect.easyhub.http.base.HttpResponse
import com.biginsect.easyhub.http.base.HttpSubscriber
import com.biginsect.easyhub.ui.contract.ILoginContract
import com.biginsect.easyhub.ui.presenter.base.BasePresenter
import com.biginsect.easyhub.util.ListUtils
import io.reactivex.disposables.Disposable
import okhttp3.Credentials
import java.util.*

/**
 * login p层
 * @author big insect
 * @date 2018/8/21.
 */
class LoginPresenter: BasePresenter<ILoginContract.ILoginView>(), ILoginContract.ILoginPresenter{

    override fun login(name: String, password: String) {
        val authRequest = AuthRequest.createAuth()
        val token = Credentials.basic(name, password)

        val authSubscriber = HttpSubscriber(object :HttpObserver<AuthToken>{
            override fun onError(error: Throwable) {
                if (isViewAttached){
                    view.showError(getErrorMsg(error))
                }
            }

            override fun onSuccess(response: HttpResponse<AuthToken>) {
                val authToken = response.body()
                if (isViewAttached){
                    if (null != authToken){
                        view.getTokenSuccess(authToken)
                    }else{
                        view.getTokenFailed(response.originalResponse.message())
                    }
                }
            }

            override fun onSubscribe(d: Disposable) {
                registerDisposable(d)
            }
        })

        val observable = getLoginService(token).authorize(authRequest)
        executeRxHttp(observable, authSubscriber)
    }

    override fun getToken(intent: Intent) {
        val uri = intent.data
        if (null != uri){
            val code = uri.getQueryParameter("code")
            val state = uri.getQueryParameter("state")
            getToken(code, state)
        }
    }

    override fun getUserInfo(authToken: AuthToken) {
        val userSubscriber = HttpSubscriber(object: HttpObserver<User>{
            override fun onError(error: Throwable) {
                if (isViewAttached){
                    view.showError(getErrorMsg(error))
                }
            }

            override fun onSuccess(response: HttpResponse<User>) {
                val user = response.body()
                if (isViewAttached){
                    if (null != user){
                        saveUserInfo(authToken, user)
                        view.onLoginSuccess()
                    }
                }
            }

            override fun onSubscribe(d: Disposable) {
                registerDisposable(d)
            }
        })

        val observable = getUserService(authToken.token).getUserInfo(true)
        executeRxHttp(observable, userSubscriber)
    }

    override fun getOAuthUrl(): String {
        val randomState = UUID.randomUUID().toString()

        return "${AppConfig.OAUTH_URL}?client_id=${AppConfig.CLIENT_ID}" +
                "&scope=${AppConfig.OAUTH2_SCOPE}&state=$randomState"
    }

    private fun saveUserInfo(authToken: AuthToken, user: User){
        val updateUser = "UPDATE ${daoSession.authUserDao.tablename} SET " +
                "${AuthUserDao.Properties.Selected.columnName} = 0"
        daoSession.authUserDao.database.execSQL(updateUser)

        val deleteExists = "DELETE FROM ${daoSession.authUserDao.tablename} WHERE " +
                "${AuthUserDao.Properties.LoginId.columnName} = '${user.login}'"
        daoSession.authUserDao.database.execSQL(deleteExists)

        val authUser = AuthUser()
        val scope = ListUtils.listToString(authToken.scopes)
        val date = Date()

        authUser.accessToken = authToken.token
        authUser.scope = scope
        authUser.authTime = date
        /**360天*/
        authUser.expireIn = 360 * 24 * 60 * 60
        authUser.selected = true
        authUser.loginId = user.login
        authUser.name = user.name
        authUser.avatar = user.avatarUrl

        daoSession.authUserDao.insert(authUser)
        AppData.authUser = authUser
        AppData.loggedUser = user
    }

    /**
     * @param code 登录后跳转至github授权网页，需要用户授权并返回参数code
     * @param state 随机字符串
     * */
    private fun getToken(code: String, state: String){
        val tokenSubscriber = HttpSubscriber(object: HttpObserver<OAuthToken>{
            override fun onError(error: Throwable) {
                if (isViewAttached){
                    view.getTokenFailed(getErrorMsg(error))
                }
            }

            override fun onSuccess(response: HttpResponse<OAuthToken>) {
                val oAuthToken = response.body()
                if (isViewAttached){
                    if (null != oAuthToken){
                        view.getTokenSuccess(AuthToken.createAuthToken(oAuthToken))
                    }else{
                        view.getTokenFailed(response.originalResponse.message())
                    }
                }
            }

            override fun onSubscribe(d: Disposable) {
                registerDisposable(d)
            }
        })

        val observable = getLoginService().getAccessToken(AppConfig.CLIENT_ID,
                AppConfig.CLIENT_SECRET, code, state)

        executeRxHttp(observable, tokenSubscriber)
    }

    private fun getLoginService():LoginService{
        return GitHubRetrofit.createRetrofit(AppConfig.GIT_HUB_BASE_URL, null)
                .create(LoginService::class.java)
    }

    private fun getLoginService(token: String):LoginService{
        return GitHubRetrofit.createRetrofit(AppConfig.BASE_API_URL, token)
                .create(LoginService::class.java)
    }
}