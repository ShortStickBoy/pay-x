package com.sunzn.pay.base

import android.app.Activity

object Consumer {

    fun <T : PayParams> pay(mode: PayMode<T>, activity: Activity, params: T, listener: PayListener) {
        mode.pay(activity, params, listener)
    }

}
