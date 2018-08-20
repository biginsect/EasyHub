package com.lipeng.mygithub.ui.contract

import com.lipeng.mygithub.base.mvp.MvpPresenter
import com.lipeng.mygithub.base.mvp.MvpView

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