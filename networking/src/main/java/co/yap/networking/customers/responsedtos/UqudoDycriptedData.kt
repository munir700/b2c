package co.yap.networking.customers.responsedtos

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class UqudoHeader(
    @SerializedName("kid") var kid: String? = null,
    @SerializedName("alg") var alg: String? = null
) : Parcelable

@Keep
@Parcelize
data class UqudoPayLoad(
    @SerializedName("aud") var aud: String? = null,
    @SerializedName("data") var data: EidData? = null,
    @SerializedName("iss") var iss: String? = null,
    @SerializedName("exp") var exp: Int? = null,
    @SerializedName("iat") var iat: Int? = null,
    @SerializedName("jti") var jti: String? = null
) : Parcelable

@Parcelize
data class EidData(
    @SerializedName("documents") var documents: MutableList<IdentityDocument> = mutableListOf()
) : Parcelable

@Parcelize
data class IdentityDocument(
    @SerializedName("face") var face: String? = null,
    @SerializedName("documentType") var documentType: String? = null,
    @SerializedName("scan") var scan: Scan? = null,
    @SerializedName("reading") var reading: String? = null
) : Parcelable

@Parcelize
data class Scan(
    @SerializedName("edited") var edited: Boolean? = null,
    @SerializedName("faceImageId") var faceImageId: String? = null,
    @SerializedName("frontImageId") var frontImageId: String? = null,
    @SerializedName("back") var back: EidBack? = EidBack(),
    @SerializedName("front") var front: EidFront? = EidFront(),
    @SerializedName("backImageId") var backImageId: String? = null

) : Parcelable

@Parcelize
data class EidBack(
    @SerializedName("documentCode") var documentCode: String? = null,
    @SerializedName("dateOfExpiry") var dateOfExpiry: String? = null,
    @SerializedName("secondaryId") var secondaryId: String? = null,
    @SerializedName("documentNumber") var documentNumber: String? = null,
    @SerializedName("sex") var sex: String? = null,
    @SerializedName("dateOfBirth") var dateOfBirth: String? = null,
    @SerializedName("opt1") var opt1: String? = null,
    @SerializedName("opt2") var opt2: String? = null,
    @SerializedName("primaryId") var primaryId: String? = null,
    @SerializedName("mrzText") var mrzText: String? = null,
    @SerializedName("issuer") var issuer: String? = null,
    @SerializedName("nationality") var nationality: String? = null,
    @SerializedName("mrzVerified") var mrzVerified: Boolean? = null
) : Parcelable

@Parcelize
data class EidFront(
    @SerializedName("nationality") var nationality: String? = null,
    @SerializedName("chipAvailable") var chipAvailable: Boolean? = null,
    @SerializedName("identityNumber") var identityNumber: String? = null,
    @SerializedName("fullName") var fullName: String? = null,
    @SerializedName("identityNumberVerified") var identityNumberVerified: Boolean? = null
) : Parcelable
