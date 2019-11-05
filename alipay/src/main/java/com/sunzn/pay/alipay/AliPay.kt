package com.sunzn.pay.alipay

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log

import com.alipay.sdk.app.PayTask
import com.sunzn.pay.base.PayListener
import com.sunzn.pay.base.PayMode

class AliPay : PayMode<AliPayParams> {

    private var mListener: PayListener? = null

    companion object {
        private const val SDK_PAY_FLAG = 1
    }

    override fun pay(activity: Activity, params: AliPayParams, listener: PayListener) {

        this.mListener = listener

        val payRunnable = Runnable {
            val task = PayTask(activity)
            val result = task.payV2(params.orderInfo, true)
            Log.i("msp", result.toString())

            val msg = Message()
            msg.what = SDK_PAY_FLAG
            msg.obj = result
            mHandler.sendMessage(msg)
        }

        // 必须异步调用
        val payThread = Thread(payRunnable)
        payThread.start()
    }

    @Suppress("UNCHECKED_CAST")
    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == SDK_PAY_FLAG) {

                val payResult = AliPayResult(msg.obj as Map<String, String>)
                /*
                 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                val resultInfo = payResult.result// 同步返回需要验证的信息
                val resultStatus: String? = payResult.resultStatus
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, AliPayErrors.CODE_SUCCESS)) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    mListener?.onSuccess()
                } else if (TextUtils.equals(resultStatus, AliPayErrors.CODE_CANCEL)) {
                    mListener?.onCancel()
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    mListener?.onFailure(resultStatus, AliPayErrors.getText(resultStatus))
                }
            }
        }
    }

}
