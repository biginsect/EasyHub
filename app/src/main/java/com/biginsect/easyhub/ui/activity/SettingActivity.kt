package com.biginsect.easyhub.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import com.biginsect.easyhub.R
import com.biginsect.easyhub.ui.activity.base.BaseFragmentActivity
import com.biginsect.easyhub.ui.contract.ISettingContract
import com.biginsect.easyhub.ui.fragment.SettingFragment
import com.biginsect.easyhub.ui.presenter.SettingPresenter
import com.thirtydegreesray.dataautoaccess.annotation.AutoAccess
import kotlinx.android.synthetic.main.activity_base_fragment.*

/**
 * @author big insect
 * @date 2018/8/30.
 */
class SettingActivity : BaseFragmentActivity<ISettingContract.ISettingView, ISettingContract.ISettingPresenter, SettingFragment>(),
        ISettingContract.ISettingView, SettingFragment.SettingsCallback {

    @AutoAccess
    private var recreated = false

    companion object {
        fun show(activity: Activity, requestCode: Int) {
            val intent = Intent(activity, SettingActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun createPresenter(): ISettingContract.ISettingPresenter {
        presenter = SettingPresenter()
        return presenter
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setToolbarTitle(getString(R.string.setting))
        if (recreated) {
            layout_root.post { startAnimation() }
            setResult(Activity.RESULT_OK)
            recreated = false
        }
    }

    override fun createFragment(): SettingFragment {
        return SettingFragment()
    }

    override fun onLogout() {
        setResult(Activity.RESULT_CANCELED)
        presenter.logout()
    }

    override fun onRecreate() {
        recreated = true
        recreate()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (recreated) {
            super.onSaveInstanceState(outState)
        } else {
            finish()
        }
    }

    private fun startAnimation() {
        val cx = layout_root.width / 2
        val cy = layout_root.height / 2
        val endRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(layout_root, cx, cy, 0f, endRadius)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
            }
        })
        layout_root.visibility = View.VISIBLE
        anim.start()
    }
}