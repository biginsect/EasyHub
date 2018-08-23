package com.biginsect.easygithub.ui.contract

import com.biginsect.easygithub.ui.base.IBaseContract

/**
 * @author big insect
 * @date 2018/8/20.
 */
class IDetailPageContract {
    interface IDetailView: IBaseContract.IView{

    }

    interface IDetailPresenter:IBaseContract.IPresenter<IDetailView>{

    }
}