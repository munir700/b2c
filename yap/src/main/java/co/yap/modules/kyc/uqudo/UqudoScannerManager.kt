package co.yap.modules.kyc.uqudo

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.Keep
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.EidData
import co.yap.networking.customers.responsedtos.UqudoHeader
import co.yap.networking.customers.responsedtos.V2DocumentDTO
import co.yap.networking.customers.responsedtos.documents.UqudoTokenResponse
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.isFutureDate
import co.yap.yapcore.helpers.DateUtils.nextYear
import co.yap.yapcore.helpers.SingletonHolder
import co.yap.yapcore.helpers.extentions.saveEidTemp
import co.yap.yapcore.helpers.jwtparser.JWT
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import io.uqudo.sdk.core.DocumentBuilder
import io.uqudo.sdk.core.UqudoBuilder
import io.uqudo.sdk.core.UqudoSDK
import io.uqudo.sdk.core.domain.model.DocumentType
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * To improve the Code quality and prevent code duplication a separate composer for Uqudo is created
 * you must need to  access Singlaton instanse of UqudoScannerManager
 * i.e UqudoScannerManager.getInstance(context)
 * @see SingletonHolder
 * created by Gulwisha Ahmed
 * */
class UqudoScannerManager private constructor(val context: Context) : IUqudoManager {
    private val FRONT_IMAGE_RESOURCE_PATH: Int = 1
    private val BACK_IMAGE_RESOURCE_PATH: Int = 2
    private val UQUDO_BASE_URL: String = "https://id.uqudo.io/api/v1/info/img/"
    private var uqudoPayloadData: MutableLiveData<EidData> = MutableLiveData()
    private var uqudoAccessToken: MutableLiveData<UqudoTokenResponse> = MutableLiveData()
    private var uqudoScannedToken: MutableLiveData<String> = MutableLiveData()
    private var imagePaths: HashMap<Int, String> = hashMapOf()
    private var uqudoHeader: MutableLiveData<UqudoHeader> = MutableLiveData()
    private var tokenInitiatedTime: MutableLiveData<Date> = MutableLiveData()
    private var dateOfBirth: MutableLiveData<Date> = MutableLiveData()
    private var dateOfExpiry: MutableLiveData<Date> = MutableLiveData()
    private val TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private val DATE_INPUT_FORMAT = "yyMMdd"
    private val DATE_OUTPUT_FORMAT = "yyyy-mm-dd"

    companion object : SingletonHolder<UqudoScannerManager, Context>(::UqudoScannerManager)

    fun fetchDocumentBackDate() = getPayloadData()?.documents?.get(0)?.scan?.back
    private fun fetchDocumentFrontDate() = getPayloadData()?.documents?.get(0)?.scan?.front
    override fun getDateOfBirth(): Date? =
        getFormatDateFromUqudo(fetchDocumentBackDate()?.dateOfBirth, UqudoFlags.DATE_OF_BIRTH)

    override fun getExpiryDate(): Date? =
        getFormatDateFromUqudo(fetchDocumentBackDate()?.dateOfExpiry, UqudoFlags.EXPIRY_DATE)

    override fun initializeUqudo() = UqudoSDK.init(context.applicationContext)
    override fun setUqudoToken(uqudoTokenResponse: UqudoTokenResponse) {
        tokenInitiatedTime.value = Calendar.getInstance().time
        uqudoAccessToken.value = uqudoTokenResponse
    }

    override fun getUqudoAccessToken(): MutableLiveData<UqudoTokenResponse> = uqudoAccessToken
    override fun initiateUqudoScanning(): Intent? {
        var uqudoIntent: Intent? = null
        uqudoAccessToken.value?.accessToken?.let { token ->
            try {
                val doc = DocumentBuilder(context.applicationContext)
                    .setDocumentType(DocumentType.UAE_ID).disableHelpPage().enableReading()
                    .enableScanReview(frontSide = true, backSide = true)
                    .build()
                uqudoIntent = UqudoBuilder.Enrollment()
                    .setToken(token)
                    .disableSecureWindow()
                    .add(doc)
                    .build(context.applicationContext)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
        return uqudoIntent
    }

    override fun decodeEncodedUqudoToken(encodedToken: String, sucess: () -> Unit) {
        imagePaths.clear()
        uqudoScannedToken.value = encodedToken
        val jwt = JWT(encodedToken)
        val gson = Gson()
        val data = jwt.claims["data"]?.asObject(EidData::class.java)
        val payLoad = gson.toJson(data)
        val payLoadObj: EidData = gson.fromJson(payLoad, EidData::class.java)
        uqudoPayloadData.postValue(payLoadObj)
        sucess.invoke()
    }

    override fun isAccessTokenExpired(): Boolean {
        val timeInSec = uqudoAccessToken.value?.expiresIn?.toInt()?.toLong() ?: 0
        var seconds: Long = 0
        tokenInitiatedTime.value?.let {
            val formatter = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
            val defaultDateString = formatter.format(it)
            val defaultDate = formatter.parse(defaultDateString)
            val date = Calendar.getInstance().time
            val currentDateString = formatter.format(date)
            val currentDate = formatter.parse(currentDateString)
            val diff: Long = currentDate.time - defaultDate.time
            seconds = (diff / 1000)
        }

        return (timeInSec <= seconds)
    }

    override fun downloadImage(success: (success: Boolean) -> Unit) {
        uqudoPayloadData.value?.let { payload ->
            imagePaths.clear()
            downloadImagewithGlide(
                payload.documents.get(0).scan?.frontImageId ?: ""
            )
            { isFrontImageDownloaded, frontImage ->
                if (isFrontImageDownloaded) {
                    frontImage?.let { context.saveEidTemp(it) }
                        ?.let { imagePaths[FRONT_IMAGE_RESOURCE_PATH] = it }
                    downloadImagewithGlide(
                        payload.documents.get(0).scan?.backImageId ?: ""
                    ) { isBackImageDownloaded, backImage ->
                        if (isBackImageDownloaded) {
                            backImage?.let { context.saveEidTemp(it) }
                                ?.let { imagePaths[BACK_IMAGE_RESOURCE_PATH] = it }
                            success.invoke(true)
                        } else {
                            success.invoke(false)
                        }
                    }
                } else {
                    success.invoke(false)
                }
            }
        }

    }

    private fun downloadImagewithGlide(
        imageId: String,
        downloaded: (sucess: Boolean, bitmap: Bitmap?) -> Unit
    ) {
        val url = GlideUrl(
            UQUDO_BASE_URL + imageId, LazyHeaders.Builder()
                .addHeader(
                    "Authorization",
                    "Bearer ${uqudoAccessToken.value?.accessToken}"
                )
                .build()
        )
        Glide.with(context.applicationContext)
            .asBitmap()
            .load(url)
            .error(url).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    downloaded.invoke(true, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    downloadImagewithGlide(imageId) { _, _ -> }
                }
            })
    }

    override fun getFormatDateFromUqudo(string: String?, flags: UqudoFlags): Date? {
        val inputSDF = SimpleDateFormat(DATE_INPUT_FORMAT, Locale.getDefault())
        val dob = DateUtils.stringToDate(string ?: "", "yyMMdd")
        val date: Date?
        try {

            date = if (isFutureDate(dob) == true && flags == UqudoFlags.DATE_OF_BIRTH) nextYear(
                dob,
                -100
            ) else inputSDF.parse(string ?: "")
            inputSDF.applyPattern(DATE_OUTPUT_FORMAT)
            if (flags == UqudoFlags.EXPIRY_DATE) dateOfExpiry.value = date else dateOfBirth.value =
                date
            return date
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return Date()
    }

    fun getUqudoIdentity(isAmendment: Boolean = false): V2DocumentDTO? =
        uqudoPayloadData.value?.let { uqudoIdentity ->
            V2DocumentDTO(
                filePaths = ArrayList(imagePaths.values),
                documentType = "EMIRATES_ID",
                firstName = "",
                middleName = "",
                lastName = "",
                dateExpiry = dateOfExpiry.value ?: Date(),
                dob = dateOfBirth.value ?: Date(),
                fullName = fetchDocumentFrontDate()?.fullName ?: "",
                gender = fetchDocumentBackDate()?.sex.toString(),
                digit3CountryCode = fetchDocumentBackDate()?.nationality ?: "UAE",
                nationality = fetchDocumentFrontDate()?.nationality ?: "",
                identityNo = fetchDocumentFrontDate()?.identityNumber,
                isAmendment = isAmendment
            )
        }

    override fun getPayloadData(): EidData? = uqudoPayloadData.value

    override fun getHeaderData(): UqudoHeader? = uqudoHeader.value
    override fun getFrontImagePath(): String? = imagePaths[FRONT_IMAGE_RESOURCE_PATH]

    override fun getBackImagePath(): String? = imagePaths[BACK_IMAGE_RESOURCE_PATH]

    override fun isExpiryDateValid(expirationDate: Date): Boolean {
        return !DateUtils.isDatePassed(expirationDate).also {
            it
        }
    }

    override fun resetData() {
        uqudoPayloadData = MutableLiveData()
        uqudoAccessToken = MutableLiveData()
        uqudoScannedToken = MutableLiveData()
        uqudoHeader = MutableLiveData()
        tokenInitiatedTime = MutableLiveData()
    }

    override fun deleteEidImages() {
        getUqudoIdentity()?.filePaths?.let {
            it.forEach { filePath ->
                File(filePath).deleteRecursively()
            }
        }
    }
}

@Keep
enum class UqudoFlags {
    EXPIRY_DATE, DATE_OF_BIRTH, NONE
}