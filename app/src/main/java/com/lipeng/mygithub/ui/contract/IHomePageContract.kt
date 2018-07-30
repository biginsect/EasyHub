package com.lipeng.mygithub.ui.contract

import com.lipeng.mygithub.base.mvp.MvpPresenter
import com.lipeng.mygithub.base.mvp.MvpView

/**
 * @author big insect
 */
interface IHomePageContract {
    interface IHomePageView : MvpView{

    }

    interface IHomePagePresenter : MvpPresenter<IHomePageView>{

    }
}