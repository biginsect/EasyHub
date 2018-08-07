package com.lipeng.mygithub.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lipeng.mygithub.R;
import com.lipeng.mygithub.base.mvp.MvpPresenter;
import com.lipeng.mygithub.base.mvp.MvpView;
import com.orhanobut.logger.Logger;

/**
 * @author big insect
 */

public abstract class BaseFragmentActivity<V extends MvpView, P extends MvpPresenter<V>, F extends Fragment>
        extends BaseMvpActivity<V, P>{
    private F fragment;
    private static final String TAG_FRAGMENT = "fragment";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_fragment;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setToolbarBackAvailable();

        Fragment tmp = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (null == tmp){
            fragment = createFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, TAG_FRAGMENT)
                    .commit();
            Logger.d("current fragment is null");
        }else {
            fragment = (F)tmp;
            Logger.d("current fragment is not null");
        }
    }

    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        Logger.d("onAttachFragment");
    }

    /**
     * 创建fragment
     * @return target
     * */
    protected abstract F createFragment();

    protected F getFragment(){
        return fragment;
    }
}
