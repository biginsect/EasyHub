package com.lipeng.mygithub.base.http.error

/**
 * @author big insect
 */
open class HttpError(errorCode : Int): Error(ErrorCode.getErrorMsg(errorCode))