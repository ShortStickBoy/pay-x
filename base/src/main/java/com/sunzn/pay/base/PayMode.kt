package com.sunzn.pay.base

import android.app.Activity

interface PayMode<T : PayParams> {

    fun pay(activity: Activity, params: T, listener: PayListener)

}
