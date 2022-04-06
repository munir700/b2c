package co.yap.modules.kyc.uqudo

import android.content.Intent
import co.yap.networking.customers.responsedtos.EidData
import co.yap.networking.customers.responsedtos.UqudoHeader
import co.yap.networking.customers.responsedtos.V2DocumentDTO
import java.util.*

interface IUqudoManager {
    fun initializeUqudo()
    fun initiateUqudoScanning(): Intent?
    fun isAccessTokenExpired(): Boolean
    fun decodeEncodedUqudoToken(encodedToken: String, sucess: () -> Unit)
    fun getPayloadData(): EidData?
    fun getHeaderData(): UqudoHeader?
    fun downloadImage(success: (success: Boolean) -> Unit)
    fun getFrontImagePath(): String?
    fun getBackImagePath(): String?
    fun getFormatDateFromUqudo(string: String?): Date
    fun getUqudoIdentity() : V2DocumentDTO?
    fun deleteEidImages()
    fun resetData()
}