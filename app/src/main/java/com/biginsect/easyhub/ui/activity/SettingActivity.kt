package com.biginsect.easyhub.ui.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.biginsect.easyhub.R
import com.biginsect.easyhub.ui.base.BaseActivity
import com.biginsect.easyhub.ui.contract.ISettingContract
import com.biginsect.easyhub.ui.presenter.SettingPresenter

/**
 * @author big insect
 * @date 2018/8/30.
 */
class SettingActivity : BaseActivity<ISettingContract.ISettingView, ISettingContract.ISettingPresenter>(),
        ISettingContract.ISettingView{

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

    private fun logout(){
        AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(R.string.warning)
                .setMessage(R.string.warning_logout)
                .setNegativeButton(R.string.cancel) { dialog, which -> dialog?.dismiss() }
                .setPositiveButton(R.string.okay){dialog, which ->
                    setResult(Activity.RESULT_CANCELED)
                    presenter.logout()
                    dialog.dismiss()
                }
                .show()
    }
}