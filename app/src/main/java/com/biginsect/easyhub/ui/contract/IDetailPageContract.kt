package com.biginsect.easyhub.ui.contract

import com.biginsect.easyhub.ui.base.IBaseContract

/**
 * @author big insect
 * @date 2018/8/20.
 */
interface IDetailPageContract {
    interface IDetailView: IBaseContract.IView{

    }

    interface IDetailPresenter:IBaseContract.IPresenter<IDetailView>{

    }
}