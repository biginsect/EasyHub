package com.biginsect.easygithub.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.biginsect.easygithub.R;
import com.biginsect.easygithub.base.BaseMvpActivity;
import com.biginsect.easygithub.ui.contract.ISettingContract;
import com.biginsect.easygithub.ui.presenter.SettingPresenter;

/**
 * @author biginsect
 * @date 2018/8/17.
 */

public class SettingActivity extends BaseMvpActivity<ISettingContract.ISettingView, ISettingContract.ISettingPresenter>
        implements ISettingContract.ISettingView{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @NonNull
    @Override
    public ISettingContract.ISettingPresenter createPresenter() {
        presenter = new SettingPresenter();
        return presenter;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setToolbarTitle(getString(R.string.setting));
    }

    @Override
    public void showLoginPage() {
        getActivity().finishAffinity();
        startActivity(new Intent(getActivity(), LoginPageActivity.class));
    }

    private void logout(){
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(R.string.warning)
                .setMessage(R.string.warning_logout)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(Activity.RESULT_CANCELED);
                        presenter.logout();
                    }
                })
                .show();
    }
}
