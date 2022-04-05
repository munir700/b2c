package co.yap.modules.kyc.uqudo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.EidData
import co.yap.networking.customers.responsedtos.UqudoHeader
import co.yap.networking.customers.responsedtos.UqudoPayLoad
import co.yap.networking.customers.responsedtos.V2DocumentDTO
import co.yap.networking.customers.responsedtos.documents.UqudoTokenResponse
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.SingletonHolder
import co.yap.yapcore.helpers.extentions.saveEidTemp
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
class UqudoScannerManager private constructor(val context: Activity) : IUqudoManager {
    private val FRONT_IMAGE_RESOURCE_PATH: Int = 1
    private val BACK_IMAGE_RESOURCE_PATH: Int = 2
    private val UQUDO_BASE_URL: String = "https://id.dev.uqudo.io/api/v1/info/img/"
    private var uqudoPayloadData: MutableLiveData<UqudoPayLoad> = MutableLiveData()
    private var uqudoAccessToken: MutableLiveData<UqudoTokenResponse> = MutableLiveData()
    private var uqudoScannedToken: MutableLiveData<String> = MutableLiveData()
    private var imagePaths: HashMap<Int, String> = hashMapOf()
    private var uqudoHeader: MutableLiveData<UqudoHeader> = MutableLiveData()
    private var tokenInitiatedTime: MutableLiveData<Date> = MutableLiveData()
    private val TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private val DATE_INPUT_FORMAT = "yyMMdd"

    private val DATE_OUTPUT_FORMAT = "yyyy-mm-dd"

    companion object : SingletonHolder<UqudoScannerManager, Activity>(::UqudoScannerManager)

    fun fetchDocumentBackDate() = getPayloadData()?.documents?.get(0)?.scan?.back
    fun fetchDocumentFrontDate() = getPayloadData()?.documents?.get(0)?.scan?.front
    fun getDateOfBirth(): Date = getFormatDateFromUqudo(fetchDocumentBackDate()?.dateOfBirth)

    fun getExpiryDate(): Date = getFormatDateFromUqudo(fetchDocumentBackDate()?.dateOfExpiry)

    override fun initializeUqudo() = UqudoSDK.init(context.applicationContext)
    fun setUqudoToken(uqudoTokenResponse: UqudoTokenResponse) {
        tokenInitiatedTime.value = Calendar.getInstance().time
        uqudoAccessToken.value = uqudoTokenResponse
    }

    fun getUqudoAccessToken() = uqudoAccessToken
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
        uqudoScannedToken.value = encodedToken
        decodeUqudoScannedToken {
            sucess.invoke()
        }
    }

    private fun decodeUqudoScannedToken(success: () -> Unit) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) Toast.makeText(
            context,
            "Requires SDK 26",
            Toast.LENGTH_SHORT
        ).show() else {
            val parts = uqudoScannedToken.value?.split("\\.".toRegex())
            parts?.let { parts ->
                try {
                    val decoder = Base64.getUrlDecoder()
                    val charset = charset("UTF-8")
                    val base64EncodedHeader: String = parts[0]
                    val base64EncodedBody: String = parts[1]
                    val header =
                        String(decoder.decode(base64EncodedHeader.toByteArray(charset)), charset)
                    val payload =
                        String(decoder.decode(base64EncodedBody.toByteArray(charset)), charset)
                    val gson = Gson()
                    val headerObj: UqudoHeader = gson.fromJson(header, UqudoHeader::class.java)
                    val payLoadObj: UqudoPayLoad = gson.fromJson(payload, UqudoPayLoad::class.java)
                    uqudoPayloadData.postValue(payLoadObj)
                    uqudoHeader.postValue(headerObj)
                    success.invoke()
                } catch (e: Exception) {
                    "Error parsing JWT: $e"
                }
            }
        }
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

    override fun downloadImage(
        success: (success: Boolean) -> Unit
    ) {
        uqudoPayloadData.value?.let { payload ->
            imagePaths.clear()
            downloadImagewithGlide(
                payload.data?.documents?.get(0)?.scan?.frontImageId ?: ""
            )
            { isFrontImageDownloaded, frontImage ->
                if (isFrontImageDownloaded) {
                    frontImage?.let { context.saveEidTemp(it) }
                        ?.let { imagePaths[FRONT_IMAGE_RESOURCE_PATH] = it }
                    downloadImagewithGlide(
                        payload.data?.documents?.get(0)?.scan?.backImageId ?: ""
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

    override fun getFormatDateFromUqudo(string: String?): Date {
        val inputSDF = SimpleDateFormat(DATE_INPUT_FORMAT)
        val outputSDF = SimpleDateFormat(DATE_OUTPUT_FORMAT, Locale.getDefault())
        var date: Date? = null
        try {
            date = inputSDF.parse(string)
            inputSDF.applyPattern(DATE_OUTPUT_FORMAT)
            val newDate = outputSDF.format(date)
            val dateRes: Date = outputSDF.parse(newDate)
            return dateRes
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return Date()
    }

    override fun getUqudoIdentity(): V2DocumentDTO? = uqudoPayloadData.value?.let { uqudoIdentity ->
        V2DocumentDTO(
            filePaths = ArrayList(imagePaths.values),
            documentType = "EMIRATES_ID",
            firstName = "",
            middleName = "",
            lastName = "",
            dateExpiry = getExpiryDate(),
            dob = getDateOfBirth(),
            fullName = fetchDocumentFrontDate()?.fullName ?: "",
            gender = fetchDocumentBackDate()?.sex.toString(),
            digit3CountryCode = fetchDocumentBackDate()?.nationality ?: "UAE",
            nationality = fetchDocumentFrontDate()?.nationality ?: "",
            identityNo = fetchDocumentFrontDate()?.identityNumber
        )
    }

    override fun getPayloadData(): EidData? = uqudoPayloadData.value?.data

    override fun getHeaderData(): UqudoHeader? = uqudoHeader.value
    override fun getFrontImagePath(): String? = imagePaths[FRONT_IMAGE_RESOURCE_PATH]

    override fun getBackImagePath(): String? = imagePaths[BACK_IMAGE_RESOURCE_PATH]

    fun isExpiryDateValid(expirationDate: Date): Boolean {
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
        getUqudoIdentity()?.filePaths?.forEach { filePath ->
            File(filePath).deleteRecursively()
        }
    }
}