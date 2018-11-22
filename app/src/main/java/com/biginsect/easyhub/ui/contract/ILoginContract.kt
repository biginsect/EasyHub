package com.biginsect.easyhub.ui.contract

import android.content.Intent
import com.biginsect.easyhub.bean.response.AuthToken
import com.biginsect.easyhub.ui.contract.base.IBaseContract

/**
 * @author big insect
 */
interface ILoginContract {
    interface ILoginView : IBaseContract.IView {
        /**
         * 成功获取到token
         * */
        fun getTokenSuccess(authToken: AuthToken)

        /**
         * 获取token失败
         * */
        fun getTokenFailed(msg: String)

        /**
         * token 验证成功，成功等到，到主页面
         * */
        fun onLoginSuccess()
    }

    interface ILoginPresenter : IBaseContract.IPresenter<ILoginView> {
        /**
         * check之后登录跳转
         * */
        fun login(name: String, password: String)

        /**
         * 鉴权之后获取token
         * */
        fun getToken(intent: Intent)

        /**
         * 获取当前用户信息
         * */
        fun getUserInfo(authToken: AuthToken)

        /**
         * 使用浏览器鉴权
         * */
        fun getOAuthUrl(): String
    }
}