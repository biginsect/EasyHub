package com.biginsect.easyhub.app

import com.biginsect.easyhub.bean.User
import com.biginsect.easyhub.dao.AuthUser
import com.thirtydegreesray.dataautoaccess.annotation.AutoAccess
import java.util.*

/**
 * 用户数据相关
 * @author big insect
 */
object AppData {

    @AutoAccess(dataName = "appData_loggedUser")
    var loggedUser: User? = null

    @AutoAccess(dataName = "appData_authUser")
    var authUser: AuthUser? = null

    @AutoAccess(dataName = "appData_systemDefaultLocal")
    val systemDefaultLocal: Locale = Locale.getDefault()

    val accessToken = authUser?.accessToken
}