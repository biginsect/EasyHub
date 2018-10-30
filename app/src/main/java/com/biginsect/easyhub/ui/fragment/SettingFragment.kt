package com.biginsect.easyhub.ui.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.biginsect.easyhub.R


/**
 * @author big insect
 * @date 2018/10/10.
 */

class SettingFragment : PreferenceFragmentCompat(),
        Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    private lateinit var callback: SettingFragment.SettingsCallback
    private val logout = "logout"
    private val startPage = "startPage"

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as SettingsCallback
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        findPreference(logout).onPreferenceClickListener = this
        findPreference(startPage).onPreferenceClickListener = this
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        return true
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        return when(preference?.key){
            logout ->{
                logout()
                true
            }

            startPage -> {
                true
            }

            else -> false
        }
    }

    private fun logout(){
        AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle(R.string.warning)
                .setMessage(R.string.warning_logout)
                .setNegativeButton(R.string.cancel) { dialog, _ -> dialog?.dismiss() }
                .setPositiveButton(R.string.okay) {dialog, _ ->
                    callback.onLogout()
                    dialog?.dismiss() }
                .show()
    }

    interface SettingsCallback{
        fun onLogout()
    }
}