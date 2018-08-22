package com.biginsect.easygithub.http.error

/**
 * @author big insect
 */
open class HttpError(errorCode : Int): Error(ErrorCode.getErrorMsg(errorCode))