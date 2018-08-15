package com.lipeng.mygithub.ui.contract

import android.content.Intent
import com.lipeng.mygithub.base.mvp.MvpPresenter
import com.lipeng.mygithub.base.mvp.MvpView
import com.lipeng.mygithub.bean.response.AuthResponse

/**
 * @author big insect
 */
interface ILoginContract {
    interface ILoginView :MvpView{
        /**
         * 登录结果回调
         * @param result 成功 true，失败false
         * @param code 标识码
         * */
        fun onLoginResult(result : Boolean, code :String)

        /**
         * 成功获取到token
         * */
        fun getTokenSuccess(authResponse: AuthResponse)

        /**
         * 获取token失败
         * */
        fun getTokenFailed(msg: String)

        fun getUserInfoFailed(msg: String)

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
        fun getUserInfo(response: AuthResponse)
    }
}