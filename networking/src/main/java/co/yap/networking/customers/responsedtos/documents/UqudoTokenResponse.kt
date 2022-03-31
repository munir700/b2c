package co.yap.networking.customers.responsedtos.documents

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UqudoTokenResponse(
    @SerializedName("access_token")
    val accessToken : String?,
    @SerializedName("token_type")
    val tokenType : String?,
    @SerializedName("expires_in")
    val expiresIn : String?,
    @SerializedName("scope")
    val scope : String?,
    @SerializedName("jti")
    val jti : String?
) : ApiResponse(), Parcelable
