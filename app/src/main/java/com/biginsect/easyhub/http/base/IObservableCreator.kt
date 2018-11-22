package com.biginsect.easyhub.http.base

import io.reactivex.Observable
import retrofit2.Response

/**
 * @author big insect
 * @date 2018/8/24.
 */
interface IObservableCreator<T> {

    fun create(forceNetwork: Boolean): Observable<Response<T>>
}