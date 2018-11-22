package com.biginsect.easyhub.ui.activity.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.biginsect.easyhub.R
import com.biginsect.easyhub.ui.contract.base.IBaseContract
import com.orhanobut.logger.Logger

/**
 * @author big insect
 * @date 2018/9/3.
 */

abstract class BaseFragmentActivity<V : IBaseContract.IView, P : IBaseContract.IPresenter<V>, F : Fragment>
    : BaseActivity<V, P>() {

    protected lateinit var fragment: F
        private set

    private val tagFragment = "fragment"

    @Suppress("UNCHECKED_CAST")
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setToolbarBackAvailable()

        val tmpFragment = supportFragmentManager.findFragmentByTag(tagFragment)
        if (null == tmpFragment) {
            fragment = createFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment, tagFragment)
                    .commit()
            Logger.d("current fragment is null")
        } else {
            fragment = tmpFragment as F
            Logger.d("current fragment is not null")
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        Logger.d("onAttachFragment")
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_base_fragment
    }

    protected abstract fun createFragment(): F
}