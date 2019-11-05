package com.sunzn.pay.wechat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

open class WXPayActivity : Activity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WXPay.instance.getWXAPI().handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        WXPay.instance.getWXAPI().handleIntent(intent, this)
    }

    override fun onResp(baseReq: BaseResp?) {
        if (baseReq?.type == ConstantsAPI.COMMAND_PAY_BY_WX) {
            WXPay.instance.onResp(baseReq.errCode, baseReq.errStr)
        }
    }

    override fun onReq(baseResp: BaseReq?) {
        // TODO
    }

}