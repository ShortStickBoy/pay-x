package com.sunzn.pay.wechat

import com.tencent.mm.opensdk.modelbase.BaseResp

class WXPayErrors : BaseResp.ErrCode {

    companion object {
        var errors: HashMap<String, String> = HashMap()
        const val CODE_NULL_PARAM: String = "1000"
        const val CODE_UN_SUPPORT: String = "2000"

        private const val TEXT_NULL_PARAM = "订单参数不合法"
        private const val TEXT_UN_SUPPORT = "未安装微信或者微信版本太低"

        init {
            errors[CODE_UN_SUPPORT] = TEXT_UN_SUPPORT
            errors[CODE_NULL_PARAM] = TEXT_NULL_PARAM
        }

        fun getText(code: String): String? {
            return errors[code]
        }
    }

}