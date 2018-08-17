package com.lipeng.mygithub.ui.presenter;

import com.lipeng.mygithub.app.AppData;
import com.lipeng.mygithub.base.MvpBasePresenter;
import com.lipeng.mygithub.ui.contract.ISettingContract;

/**
 * @author biginsect
 * @date 2018/8/17.
 */

public class SettingPresenter extends MvpBasePresenter<ISettingContract.ISettingView>
        implements ISettingContract.ISettingPresenter{

    @Override
    public void logout() {
        daoSession.getAuthUserDao().delete(AppData.INSTANCE.getAuthUser());
        AppData.INSTANCE.setAuthUser(null);
        AppData.INSTANCE.setLoggedUser(null);
        if (isViewAttached()){
            getView().showLoginPage();
        }
    }
}
