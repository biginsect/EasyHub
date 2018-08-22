package com.biginsect.easygithub.ui.activity

import com.biginsect.easygithub.R
import com.biginsect.easygithub.base.BaseMvpActivity
import com.biginsect.easygithub.ui.contract.IDetailPageContract
import com.biginsect.easygithub.ui.presenter.DetailPresenter

/**
 * detail page of other's project
 * @author big insect
 * @date 2018/8/20.
 */
class DetailPageActivity: BaseMvpActivity<IDetailPageContract.IDetailView, IDetailPageContract.IDetailPresenter>(),
        IDetailPageContract.IDetailView {

    override fun createPresenter(): IDetailPageContract.IDetailPresenter {
        presenter = DetailPresenter()
        return presenter
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_detail_page
    }
}