package co.yap.networking.leanteach.responsedtos

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LeanPaymentIntentModel(
    @SerializedName("payment_intent_id") var paymentIntentId: String? = null,
) : ApiResponse(), Parcelable