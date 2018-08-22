package com.biginsect.easygithub.ui.contract

import com.biginsect.easygithub.base.mvp.MvpPresenter
import com.biginsect.easygithub.base.mvp.MvpView

/**
 * @author big insect
 * @date 2018/8/20.
 */
class IDetailPageContract {
    interface IDetailView: MvpView{

    }

    interface IDetailPresenter:MvpPresenter<IDetailView>{

    }
}