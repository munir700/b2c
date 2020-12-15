package co.yap.modules.dashboard.cards.home.viewmodels

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import co.yap.modules.others.helper.Constants
import co.yap.networking.cards.responsedtos.Card
import co.yap.wallet.samsung.SamsungPayStatus
import co.yap.wallet.samsung.SamsungPayStatusManager
import co.yap.wallet.samsung.SamsungPayWalletManager
import co.yap.yapcore.BR
import co.yap.yapcore.interfaces.OnItemClickListener

class YapCardItemViewModel(
    private val context: Context,
    val paymentCard: Card?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) : BaseObservable() {
    //    var sPayStatus: MutableLiveData<SamsungPayStatus>? =
//        MutableLiveData(SamsungPayStatus.SPAY_NOT_SUPPORTED)
    @get:Bindable
    var sPayStatus: SamsungPayStatus? = SamsungPayStatus.SPAY_NOT_SUPPORTED
        set(value) {
            field = value
            notifyPropertyChanged(BR.sPayStatus)
        }

    init {
        if (Constants.CARD_TYPE_DEBIT == paymentCard?.cardType) {
            getSPayStatus()
        }
    }

    // Custom logic if there any and add only observable or mutable dataList if your really need it.
    // You can also add methods for callbacks from xml
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, paymentCard!!, position)
    }

    fun handlePressOnSamsung(view: View) {
//        SamsungPayWalletManager.getInstance(context).getWalletInfo { status, cards ->
//            val clientDeviceId = status
//            Log.d("", "")
//        }
//        return
        when (sPayStatus) {
            SamsungPayStatus.SPAY_READY -> {
                paymentCard?.let { onItemClickListener?.onItemClick(view, it, position) }
            }
            SamsungPayStatus.ERROR_SPAY_SETUP_NOT_COMPLETE -> SamsungPayStatusManager.getInstance(
                context
            ).activateSamsungPay()
            SamsungPayStatus.ERROR_SPAY_APP_NEED_TO_UPDATE -> SamsungPayStatusManager.getInstance(
                context
            ).updateSamsungPay()
        }
    }

    private fun getSPayStatus() {
        SamsungPayStatusManager.getInstance(context).getSamsungPayStatus {
            sPayStatus = it
        }

    }
}