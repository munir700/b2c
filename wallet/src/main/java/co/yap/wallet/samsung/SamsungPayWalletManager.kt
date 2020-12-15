package co.yap.wallet.samsung

import android.content.Context
import android.os.Bundle
import android.util.Log
import co.yap.yapcore.helpers.SingletonHolder
import co.yap.yapcore.helpers.alert
import com.samsung.android.sdk.samsungpay.v2.SamsungPay
import com.samsung.android.sdk.samsungpay.v2.StatusListener
import com.samsung.android.sdk.samsungpay.v2.card.Card
import com.samsung.android.sdk.samsungpay.v2.card.CardManager
import com.samsung.android.sdk.samsungpay.v2.card.GetCardListener
import com.samsung.android.sdk.samsungpay.v2.payment.PaymentManager

class SamsungPayWalletManager private constructor(private val context: Context) {
    companion object : SingletonHolder<SamsungPayWalletManager, Context>(::SamsungPayWalletManager)

    internal var mSamsungPay: SamsungPay? =
        SamsungPay(context, PartnerInfoHolder.getInstance(context).partnerInfo)
    private var mPaymentManager: PaymentManager? =
        PaymentManager(context, PartnerInfoHolder.getInstance(context).partnerInfo)
    private var mCardManager: CardManager? =
        CardManager(context, PartnerInfoHolder.getInstance(context).partnerInfo)

    fun getWalletInfo(response: (Int, Bundle?) -> Unit) {
        val keys =
            mutableListOf(SamsungPay.WALLET_DM_ID, SamsungPay.DEVICE_ID, SamsungPay.WALLET_USER_ID)
        mSamsungPay?.getWalletInfo(keys, object : StatusListener {
            override fun onSuccess(status: Int, walletData: Bundle?) {
                walletData?.let {
                    if (status == SamsungPay.ERROR_NONE) {
                        val clientDeviceId = it.getString(SamsungPay.DEVICE_ID)
                        val clientWalletAccountId = it.getString(SamsungPay.WALLET_USER_ID)
                        response.invoke(status, walletData)
                    }
                }
            }

            override fun onFail(errorCode: Int, errorData: Bundle?) {
                context.alert(ErrorCode.getInstance().getSPayError(errorCode, errorData))
                // Check the extra error codes in the errorData bundle for all the reasons in
                // SamsungPay.EXTRA_ERROR_REASON, when provided
//                when (status) {
//                    SamsungPay.SPAY_NOT_READY ->
//                        context.alert("Samsung Pay is not completely activated. Open Samsung Pay app  signed in with a valid Samsung Account and activate.")
//                    SamsungPay.SPAY_NOT_ALLOWED_TEMPORALLY -> context.alert("Samsung Pay is not allowed temporally!")
//                }
            }
        })
    }

    fun getAllCards(response: (SamsungPayStatus, MutableList<Card>?) -> Unit) {
        mCardManager?.getAllCards(
            null,
            object : GetCardListener {
                override fun onSuccess(cardList: MutableList<Card>?) {
                    Log.d("", "")
                    response.invoke(SamsungPayStatus.SPAY_READY, cardList)
                }

                override fun onFail(errorCode: Int, bundle: Bundle?) {
                    context.alert(ErrorCode.getInstance().getSPayError(errorCode, bundle))
                    response.invoke(SamsungPayStatus.SPAY_NOT_READY, null)
//                    when (errorCode) {
//                        SamsungPay.SPAY_NOT_READY -> {
//                            context.alert("Samsung Pay is not completely activated. Open Samsung Pay app  signed in with a valid Samsung Account and activate.")
//                            response.invoke(SamsungPayStatus.SPAY_NOT_READY, null)
//                        }
//                        SamsungPay.SPAY_NOT_ALLOWED_TEMPORALLY -> {
//                            context.alert("Samsung Pay is not allowed temporally!")
//                            response.invoke(SamsungPayStatus.SPAY_NOT_READY, null)
//                        }
//                    }
                }

            })
    }
}