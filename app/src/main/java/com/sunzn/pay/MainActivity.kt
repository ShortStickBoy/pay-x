package com.sunzn.pay

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sunzn.pay.alipay.AliPay
import com.sunzn.pay.alipay.AliPayParams
import com.sunzn.pay.base.Consumer
import com.sunzn.pay.base.PayListener
import com.sunzn.pay.wechat.WXPay
import com.sunzn.pay.wechat.WXPayParams

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.ali_pay).setOnClickListener {
            val aliPay = AliPay()
            val params = AliPayParams(OrderProxy.buildOrder("2019072565963638"))
            Consumer.pay(aliPay, this@MainActivity, params, object : PayListener {
                override fun onSuccess() {
                    Toast.makeText(this@MainActivity, "成功", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(errCode: String?, errText: String?) {
                    Toast.makeText(this@MainActivity, errText, Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() {
                    Toast.makeText(this@MainActivity, "成功", Toast.LENGTH_SHORT).show()
                }

            })
        }
        findViewById<Button>(R.id.wx_pay).setOnClickListener {
            val wxPay = WXPay.instance
            val params = WXPayParams()
            params.appId = "2019072565963638"
            params.partnerId = "2088501567727734"

            params.prepayId = "wx20160926184820acbd9357100240402425"
            params.packageValue = "Sign=WXPay"
            params.nonceStr = "0531a4a42fa846fe8a7563847cd24c2a"
            params.timeStamp = "1474886901"
            params.sign = "ECE311C3DF76E009E6F37F05C350625F"
            Consumer.pay(wxPay, this@MainActivity, params, object : PayListener {
                override fun onSuccess() {
                    Toast.makeText(this@MainActivity, "成功", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(errCode: String?, errText: String?) {
                    Toast.makeText(this@MainActivity, errText, Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() {
                    Toast.makeText(this@MainActivity, "成功", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

}
