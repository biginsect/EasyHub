package com.lipeng.mygithub.ui.activity

import com.lipeng.mygithub.R
import com.lipeng.mygithub.base.BaseMvpActivity
import com.lipeng.mygithub.ui.contract.IDetailPageContract
import com.lipeng.mygithub.ui.presenter.DetailPresenter

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