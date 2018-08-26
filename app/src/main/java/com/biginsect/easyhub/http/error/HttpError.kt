package com.biginsect.easyhub.http.error

/**
 * @author big insect
 */
open class HttpError(errorCode : Int): Error(ErrorCode.getErrorMsg(errorCode))