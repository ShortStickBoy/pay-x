package com.sunzn.pay.base

interface PayListener {

    fun onSuccess()

    fun onFailure(errCode: String?, errText: String?)

    fun onCancel()

}
