package co.yap.wallet.samsung

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import co.yap.widgets.State
import co.yap.yapcore.helpers.SingletonHolder
import co.yap.yapcore.helpers.alert
import com.samsung.android.sdk.samsungpay.v2.SamsungPay
import com.samsung.android.sdk.samsungpay.v2.StatusListener
import com.samsung.android.sdk.samsungpay.v2.card.*
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

            //            MTgwNzI1MTI1MTQ5NTE4eUNv
//            EjmwLn9tTou9kUBM5Sw5VQ
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

                override fun onFail(errorCode: Int, errorData: Bundle?) {
                    context.alert(ErrorCode.getInstance().getSPayError(errorCode, errorData))
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

    fun addYapCardToSamsungPay(payload: String?, success: (State) -> Unit) {
        payload?.let {
            val mNetworkProvider: String = AddCardInfo.PROVIDER_MASTERCARD
            val cardDetail = bundleOf(AddCardInfo.EXTRA_PROVISION_PAYLOAD to it)
            val addCardInfo = AddCardInfo(
                Card.CARD_TYPE_DEBIT,
                mNetworkProvider,
                cardDetail
            )
            mCardManager?.addCard(addCardInfo, object : AddCardListener {
                override fun onSuccess(status: Int, p1: Card?) {
                    success.invoke(State.success("Card successfully added."))
                }

                override fun onFail(errorCode: Int, errorData: Bundle?) {
                    success.invoke(
                        State.error(
                            ErrorCode.getInstance().getSPayError(errorCode, errorData)
                        )
                    )
//                    context.alert(ErrorCode.getInstance().getSPayError(errorCode, errorData))
                }

                override fun onProgress(currentCount: Int, p1: Int, bundleData: Bundle?) {
                    //success.invoke(State.loading("Card adding in progress"))
//                    context.alert(ErrorCode.getInstance().getSPayError(errorCode, errorData))
                }
            })
        }
    }

    fun openFavoriteCard(cardId: String?) {
        val metaData =
            bundleOf(
                PaymentManager.EXTRA_ISSUER_NAME to "YAP PAYMENT SERVICES PROVIDER LLC",
                PaymentManager.EXTRA_PAY_OPERATION_TYPE to PaymentManager.PAY_OPERATION_TYPE_PAYMENT,
                PaymentManager.EXTRA_TRANSACTION_TYPE to PaymentManager.TRANSACTION_TYPE_MST
            )
        val cardInfo =
            com.samsung.android.sdk.samsungpay.v2.payment.CardInfo.Builder().setCardId(cardId)
                .setCardMetaData(metaData).build()
        mPaymentManager?.startSimplePay(cardInfo, object : StatusListener {
            override fun onSuccess(p0: Int, p1: Bundle?) {
            }

            override fun onFail(p0: Int, p1: Bundle?) {
                Log.d("","")
            }
        })
    }
}