package com.lipeng.mygithub.ui.contract

import android.content.Intent
import com.lipeng.mygithub.base.mvp.MvpPresenter
import com.lipeng.mygithub.base.mvp.MvpView
import com.lipeng.mygithub.bean.response.AuthToken

/**
 * @author big insect
 */
interface ILoginContract {
    interface ILoginView :MvpView{
        /**
         * 成功获取到token
         * */
        fun getTokenSuccess(authToken: AuthToken)

        /**
         * 获取token失败
         * */
        fun getTokenFailed(msg: String)

        fun showErrorToast(msg: String)

        /**
         * token 验证成功，成功等到，到主页面
         * */
        fun onLoginSuccess()
    }

    interface ILoginPresenter :MvpPresenter<ILoginView>{
        fun login(name :String? ,password: String?)

        fun getToken(intent: Intent)

        /**
         * 获取当前用户信息
         * */
        fun getUserInfo(token: AuthToken)

        /**
         * 使用浏览器鉴权
         * */
        fun getOAuth(): String
    }
}