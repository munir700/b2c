package co.yap.wallet.samsung

import android.content.Context
import co.yap.wallet.R
import co.yap.wallet.encriptions.encryption.*
import co.yap.wallet.encriptions.json.GsonJsonEngine
import co.yap.wallet.encriptions.utils.EncryptionUtils
import co.yap.yapcore.helpers.DateUtils
import java.io.IOException
import java.security.GeneralSecurityException
import java.util.*

fun Context.getTestPayloadForSamsung(payload: (String) -> Unit) {
    try {
//        var privateKey: PrivateKey? = null
//        var publicKey: PublicKey? = null
//
//        val secureRandom = SecureRandom()
//        var keyPairGenerator: KeyPairGenerator? = null
//        try {
//            keyPairGenerator = KeyPairGenerator
//                .getInstance("RSA")
//        } catch (e: NoSuchAlgorithmException) {
//        }
//        keyPairGenerator?.initialize(
//                2048, secureRandom
//            )
//        privateKey = keyPairGenerator?.generateKeyPair()?.private
//        publicKey = keyPairGenerator?.generateKeyPair()?.public


//"yyyy-MM-dd'T'HH:mm"
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, 30)
        val dataValidUntilTimestamp =
            DateUtils.dateToString(calendar.time, "yyyy-MM-dd'T'HH:mm:ss'Z'")
        //5381230100036491
        val cardNumber = "5381230100035253"
        val expiryYear = "25"
        val expiryMonth = "07"
        val source = "CARD_ADDED_VIA_APPLICATION"
        val cardholderName = "JENNIFER WIGGINS"
        // TAV Process start
        val tavSignatureConfig = TAVSignatureConfigBuilder.aTAVSignatureConfig()
            .withAccountExpiry("0725")
            .withAccountNumber(cardNumber)
            .withTavFormat(TAVSignatureConfig.TAVFormat.TAV_FORMAT_2)
            .withDataValidUntilTimestamp(dataValidUntilTimestamp)// "2021-03-25T16:10:59Z"
            .withPrivateKey(
                EncryptionUtils.loadDecryptionKey(
                    resources.openRawResource(R.raw.privatekey)
                )
            ).withPublicKey(
                EncryptionUtils.loadPublicKey(
                    resources.openRawResource(R.raw.tav_public_key)
                )
            ).build()

        val base64DigitalSignature =
            TAVSignatureMethod.createBase64DigitalSignature(tavSignatureConfig)
        //TAV process End
        val iss =
            resources.openRawResource(R.raw.pepk_certificate)
        val cardInfoData =
            "{\"cardInfoData\": {\"accountNumber\": \"$cardNumber\",\"expiryMonth\": \"$expiryMonth\",\"expiryYear\":\"$expiryYear\",\"source\": \"$source\",\"cardholderName\": \"$cardholderName\"}}"
        val encryptionCertificate =
            EncryptionUtils.loadEncryptionCertificate(iss)
        val config =
            FieldLevelEncryptionConfigBuilder.aFieldLevelEncryptionConfig()
                .withEncryptionCertificate(encryptionCertificate)
                .withEncryptionPath("$.cardInfoData", "$.cardInfo")
                .withOaepPaddingDigestAlgorithm("SHA-256")
                .withEncryptionCertificateFingerprintFieldName("publicKeyFingerprint")
//                .withEncryptionKeyFingerprintFieldName("publicKeyFingerprint")
                .withEncryptedValueFieldName("encryptedData")
                .withEncryptedKeyFieldName("encryptedKey")
                .withIvField(true)
                .withIvFieldName("iv")
                .withOaepPaddingDigestAlgorithmFieldName("oaepHashingAlgorithm")
                .withTokenizationAuthenticationValueFieldName("tokenizationAuthenticationValue")
                .withFieldValueEncoding(FieldLevelEncryptionConfig.FieldValueEncoding.HEX)
                .build()
        FieldLevelEncryption.withJsonEngine(GsonJsonEngine())
        val finalPayload = FieldLevelEncryption.encryptPayloadWithTAV(
            cardInfoData,
            config, base64DigitalSignature
        )

        println("SamsungTestPayload Json>>$finalPayload")
        payload.invoke(finalPayload)
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: GeneralSecurityException) {
        e.printStackTrace()
    } catch (e: EncryptionException) {
        e.printStackTrace()
    } catch (e: InvalidSignatureException) {
        e.printStackTrace()
    }

//    "displayName" : "Tayyab Islam",
//    2020-12-22 17:04:50.920 30338-30932/co.yap.stg D/OkHttp:     "customerId" : "3000000207",
//    2020-12-22 17:04:50.920 30338-30932/co.yap.stg D/OkHttp:     "mobileNumber" : "3484509888",
//    2020-12-22 17:04:50.920 30338-30932/co.yap.stg D/OkHttp:     "emailId" : "yapdevteam@yap.com",
//    2020-12-22 17:04:50.920 30338-30932/co.yap.stg D/OkHttp:     "proxyNumber" : "1000000002792",
//    2020-12-22 17:04:50.920 30338-30932/co.yap.stg D/OkHttp:     "cvv" : "717",
//    2020-12-22 17:04:50.920 30338-30932/co.yap.stg D/OkHttp:     "cardNumber" : "5381230100016626",
//    2020-12-22 17:04:50.920 30338-30932/co.yap.stg D/OkHttp:     "expiryDate" : "01/25",
//    2020-12-22 17:04:50.920 30338-30932/co.yap.stg D/OkHttp:     "activationDate" : "01/20",
}

