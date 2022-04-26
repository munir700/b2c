package co.yap.modules.kyc.uqudo

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.EidData
import co.yap.networking.customers.responsedtos.UqudoHeader
import co.yap.networking.customers.responsedtos.V2DocumentDTO
import co.yap.networking.customers.responsedtos.documents.UqudoTokenResponse
import java.util.*

interface IUqudoManager {
    fun initializeUqudo()
    fun initiateUqudoScanning(): Intent?
    fun isAccessTokenExpired(): Boolean
    fun decodeEncodedUqudoToken(encodedToken: String, sucess: () -> Unit)
    fun getPayloadData(): EidData?
    fun getHeaderData(): UqudoHeader?
    suspend fun downloadImage(success: (success: Boolean,String?) -> Unit)
    fun getFrontImagePath(): String?
    fun getBackImagePath(): String?
    fun getFormatDateFromUqudo(string: String?, flags: UqudoFlags): Date?
    fun deleteEidImages()
    fun resetData()
    fun getDateOfBirth(): Date?
    fun getExpiryDate(): Date?
    fun setUqudoToken(uqudoTokenResponse: UqudoTokenResponse)
    fun getUqudoAccessToken(): MutableLiveData<UqudoTokenResponse>
    fun isExpiryDateValid(expirationDate: Date): Boolean
    fun noImageDownloaded():Boolean
}