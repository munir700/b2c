package co.yap.wallet.samsung

import android.content.Context
import androidx.core.os.bundleOf
import co.yap.yapcore.helpers.SingletonHolder
import com.samsung.android.sdk.samsungpay.v2.PartnerInfo
import com.samsung.android.sdk.samsungpay.v2.SamsungPay
import com.samsung.android.sdk.samsungpay.v2.SpaySdk

class PartnerInfoHolder private constructor(private val context: Context) {
    val partnerInfo: PartnerInfo

    init {
//        String serviceId = "553f9a2fbd0244e4a9c7ab";
        //val serviceId = "421cb4f7453842509db829"
        val serviceId = "9f2b7fca270c4f3c81d99e"
        //        String serviceId = context.getResources().getString(R.string.gradle_product_id);
        partnerInfo = PartnerInfo(
            serviceId,
            bundleOf(
                SamsungPay.PARTNER_SERVICE_TYPE to SpaySdk.ServiceType.APP2APP.toString(),
                SamsungPay.EXTRA_ISSUER_NAME to "YAP PAYMENT SERVICES PROVIDER LLC"
            )
        )
    }

    companion object : SingletonHolder<PartnerInfoHolder, Context>(::PartnerInfoHolder)
}