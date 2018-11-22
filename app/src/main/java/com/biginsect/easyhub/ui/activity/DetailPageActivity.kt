package com.biginsect.easyhub.ui.activity

import com.biginsect.easyhub.R
import com.biginsect.easyhub.ui.activity.base.BaseActivity
import com.biginsect.easyhub.ui.contract.IDetailPageContract
import com.biginsect.easyhub.ui.presenter.DetailPresenter

/**
 * 个人项目的详细内容
 * @author big insect
 * @date 2018/8/20.
 */
class DetailPageActivity : BaseActivity<IDetailPageContract.IDetailView, IDetailPageContract.IDetailPresenter>(),
        IDetailPageContract.IDetailView {

    override fun createPresenter(): IDetailPageContract.IDetailPresenter {
        presenter = DetailPresenter()
        return presenter
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_detail_page
    }
}