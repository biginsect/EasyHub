package com.lipeng.mygithub.ui.contract

import android.content.Intent
import com.lipeng.mygithub.base.mvp.MvpPresenter
import com.lipeng.mygithub.base.mvp.MvpView

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
    }

    interface ILoginPresenter :MvpPresenter<ILoginView>{
        fun login(name :String? ,password: String?)

        fun getToken(intent: Intent)
    }
}