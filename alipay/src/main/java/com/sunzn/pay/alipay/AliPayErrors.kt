package com.sunzn.pay.alipay

class AliPayErrors {

    companion object {
        var errors: HashMap<String, String> = HashMap()

        const val CODE_SUCCESS = "9000"
        const val CODE_HANDLING = "8000"
        const val CODE_FAILURE = "4000"
        const val CODE_REPEAT = "5000"
        const val CODE_CANCEL = "6001"
        const val CODE_NETWORK = "6002"
        const val CODE_UNKNOWN = "6004"

        private const val TEXT_SUCCESS = "订单支付成功"
        private const val TEXT_HANDLING = "正在处理中"
        private const val TEXT_FAILURE = "订单支付失败"
        private const val TEXT_REPEAT = "重复请求"
        private const val TEXT_CANCEL = "用户中途取消"
        private const val TEXT_NETWORK = "网络连接出错"
        private const val TEXT_UNKNOWN = "支付结果未知"
        private const val TEXT_ERROR = "未知错误"

        init {
            errors[CODE_SUCCESS] = TEXT_SUCCESS
            errors[CODE_HANDLING] = TEXT_HANDLING
            errors[CODE_FAILURE] = TEXT_FAILURE
            errors[CODE_REPEAT] = TEXT_REPEAT
            errors[CODE_CANCEL] = TEXT_CANCEL
            errors[CODE_NETWORK] = TEXT_NETWORK
            errors[CODE_UNKNOWN] = TEXT_UNKNOWN
        }

        fun getText(code: String?): String? {
            return errors[code] ?: TEXT_ERROR
        }
    }

}