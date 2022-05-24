package co.yap.networking.leanteach.responsedtos

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LeanOnBoardModel(
    @SerializedName("customer_id") var customerId: String? = null,
    @SerializedName("destination_id") var destinationId: String? = null
) : ApiResponse(), Parcelable