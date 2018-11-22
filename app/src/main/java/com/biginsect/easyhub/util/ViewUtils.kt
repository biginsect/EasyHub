package com.biginsect.easyhub.util

import android.support.annotation.IdRes
import android.view.Menu

/**
 * @author big insect
 * @date 2018/10/31.
 */

object ViewUtils {


    fun selectMenuItem(menu: Menu, @IdRes itemId: Int, findStub: Boolean) {
        var find = false
        val menuSize = menu.size()
        for (i in 0..menuSize) {
            if (!findStub) {
                menu.getItem(i).isChecked = menu.getItem(i).itemId == itemId
            } else {
                if (menu.getItem(i).itemId == itemId) {
                    find = true
                }
            }
        }

        if (findStub) {
            if (find) {
                selectMenuItem(menu, itemId, false)
            } else {
                for (i in 0..menuSize) {
                    val subMenu = menu.getItem(i).subMenu
                    if (subMenu != null) {
                        selectMenuItem(subMenu, itemId, true)
                    }
                }
            }
        }
    }
}