package com.lipeng.mygithub.app

import com.lipeng.mygithub.bean.User
import com.lipeng.mygithub.dao.AuthUser
import com.thirtydegreesray.dataautoaccess.annotation.AutoAccess
import java.util.*

/**
 * 用户数据相关
 * @author big insect
 */
object AppData {
    @AutoAccess(dataName = "appData_loggedUser") var loggedUser :User? = null

    @AutoAccess(dataName = "appData_authUser") var authUser :AuthUser? = null

    @AutoAccess(dataName = "appData_systemDefaultLocal")
    var systemDefaultLocal :Locale? = null
     private set(value){
        if (field == null){
            field = Locale.getDefault()
        }
    }
}