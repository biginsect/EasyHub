package com.biginsect.easyhub.ui.activity

import android.app.Activity
import android.os.Bundle
import com.biginsect.easyhub.R
import com.biginsect.easyhub.ui.activity.base.BaseFragmentActivity
import com.biginsect.easyhub.ui.contract.ISettingContract
import com.biginsect.easyhub.ui.fragment.SettingFragment
import com.biginsect.easyhub.ui.presenter.SettingPresenter

/**
 * @author big insect
 * @date 2018/8/30.
 */
class SettingActivity : BaseFragmentActivity<ISettingContract.ISettingView, ISettingContract.ISettingPresenter, SettingFragment>(),
        ISettingContract.ISettingView, SettingFragment.SettingsCallback{

    override fun createPresenter(): ISettingContract.ISettingPresenter {
        presenter = SettingPresenter()
        return presenter
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setToolbarTitle(getString(R.string.setting))
    }

    override fun createFragment(): SettingFragment {
        return SettingFragment()
    }

    override fun onLogout() {
        setResult(Activity.RESULT_CANCELED)
        presenter.logout()
    }
}