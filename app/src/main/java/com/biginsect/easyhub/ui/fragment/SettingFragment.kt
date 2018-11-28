package com.biginsect.easyhub.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.biginsect.easyhub.R
import com.biginsect.easyhub.util.PreUtils
import java.util.*


/**
 * @author big insect
 * @date 2018/10/10.
 */

class SettingFragment : PreferenceFragmentCompat(),
        Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    private var callback: SettingFragment.SettingsCallback? = null
    private val logout = "logout"

    private lateinit var nameList: List<String>
    private lateinit var idList: List<String>

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as SettingsCallback
        /**符号“*” 表示伸展可变数量参数, kotlin 必须*/
        nameList = Arrays.asList(*resources.getStringArray(R.array.start_pages_name))
        idList = Arrays.asList(*resources.getStringArray(R.array.start_pages_id))
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_setting)
        findPreference(PreUtils.THEME).onPreferenceClickListener = this
        findPreference(PreUtils.LANGUAGE).onPreferenceClickListener = this
        findPreference(PreUtils.START_PAGE).onPreferenceClickListener = this
        findPreference(PreUtils.START_PAGE).summary = nameList[getStartPageIndex()]
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        return true
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {

        return when (preference?.key) {
            PreUtils.THEME ->{
                showThemeChooser()
                true
            }

            PreUtils.LANGUAGE ->{
                showLanguageList()
                true
            }

            logout -> {
                logout()
                true
            }

            PreUtils.START_PAGE -> {
                showChooseStartPageDialog()
                true
            }

            else -> false
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    /**
     * 重设主题 or 背景颜色之后重新载入app
     * */
    private fun recreateMain() {
        callback?.onRecreate()
    }

    private fun showThemeChooser(){

    }

    private fun showLanguageList(){

    }

    private fun logout() {
        AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle(R.string.warning)
                .setMessage(R.string.warning_logout)
                .setNegativeButton(R.string.cancel) { dialog, _ -> dialog?.dismiss() }
                .setPositiveButton(R.string.okay) { dialog, _ ->
                    callback?.onLogout()
                    dialog?.dismiss()
                }
                .show()
    }

    private fun showChooseStartPageDialog() {
        AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle(R.string.start_page)
                .setSingleChoiceItems(R.array.start_pages_name, getStartPageIndex()) { dialog, which ->
                    dialog.dismiss()
                    PreUtils.set(PreUtils.START_PAGE, idList[which])
                    findPreference(PreUtils.START_PAGE).summary = nameList[which]
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .show()
    }

    private fun getStartPageIndex(): Int {
        val startPage = PreUtils.getStartPage()
        return idList.indexOf(startPage)
    }

    interface SettingsCallback {
        fun onLogout()

        fun onRecreate()
    }
}