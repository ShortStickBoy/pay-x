package com.sunzn.pay.wechat

import android.app.Activity
import android.content.Context
import com.sunzn.pay.base.PayListener
import com.sunzn.pay.base.PayMode
import com.tencent.mm.opensdk.constants.Build
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * https://developers.weixin.qq.com/doc/oplatform/Downloads/Android_Resource.html
 */
class WXPay private constructor() : PayMode<WXPayParams> {

    private var create: Boolean = false

    private lateinit var mWXApi: IWXAPI

    private lateinit var mListener: PayListener

    fun getWXAPI(): IWXAPI {
        return mWXApi
    }

    override fun pay(activity: Activity, params: WXPayParams, listener: PayListener) {

        mListener = listener

        if (params.appId.isEmpty() || params.partnerId.isEmpty() || params.prepayId.isEmpty() || params.packageValue.isEmpty() || params.nonceStr.isEmpty() || params.sign.isEmpty()) {
            WXPayErrors.getText(WXPayErrors.CODE_NULL_PARAM)?.let { listener.onFailure(WXPayErrors.CODE_NULL_PARAM, it) }
        }

        if (!create) {
            initWXApi(activity, params.appId)
        }

        if (!mWXApi.isWXAppInstalled && mWXApi.wxAppSupportAPI >= Build.PAY_SUPPORTED_SDK_INT) {
            WXPayErrors.getText(WXPayErrors.CODE_UN_SUPPORT)?.let { listener.onFailure(WXPayErrors.CODE_UN_SUPPORT, it) }
        }

        val req = PayReq()
        req.appId = params.appId
        req.partnerId = params.partnerId
        req.prepayId = params.prepayId
        req.packageValue = params.packageValue
        req.nonceStr = params.nonceStr
        req.timeStamp = params.timeStamp
        req.sign = params.sign

        mWXApi.sendReq(req)
    }

    fun onResp(errCode: Int, errText: String) {
        when (errCode) {
            BaseResp.ErrCode.ERR_OK -> mListener.onSuccess()
            BaseResp.ErrCode.ERR_COMM -> mListener.onFailure(errCode.toString(), errText)
            BaseResp.ErrCode.ERR_USER_CANCEL -> mListener.onCancel()
            else -> mListener.onFailure(errCode.toString(), errText)
        }
    }

    private fun initWXApi(context: Context, appId: String) {
        mWXApi = WXAPIFactory.createWXAPI(context.applicationContext, appId)
        mWXApi.registerApp(appId)
        create = true
    }

    companion object {
        val instance: WXPay by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            WXPay()
        }
    }

}