package co.yap.yapcore.config

import co.yap.yapcore.adjust.AdjustEvent

data class BuildConfigManager(
    var md5: String?, var sha1: String?, var sha256: String?,
    var leanPlumSecretKey: String?,
    var leanPlumKey: String?,
    var adjustToken: String?,
    var baseUrl: String?,
    var buildType: String?,
    var flavor: String?,
    var versionName: String?,
    var versionCode: String?,
    var applicationId: String?,
    var hasValidSignature: Boolean = true,
    var sslPin1: String?,
    var sslPin2: String?,
    var sslPin3: String?,
    var sslHost: String?
) {
    fun isLiveRelease(): Boolean =
        (buildType == "release" && flavor == "live") || (buildType == "release" && flavor == "stg")

    fun isLive(): Boolean = flavor == "live"

    fun getAdjustEvent(event: AdjustEvent): String {
        return when (event) {
            AdjustEvent.KYC_END -> {
                when (flavor) {
                    "live" -> "8r4o52"
                    else -> "9um5u9"
                }
            }

            AdjustEvent.KYC_START -> {
                when (flavor) {
                    "live" -> "kelb07"
                    else -> "mdcyli"
                }
            }
            AdjustEvent.SET_PIN_END -> {
                when (flavor) {
                    "live" -> "7vzpfo"
                    else -> "cs2msk"
                }
            }
            AdjustEvent.SET_PIN_START -> {
                when (flavor) {
                    "live" -> "i3m1cv"
                    else -> "smn577"
                }
            }
            AdjustEvent.SIGN_UP_MOBILE_NUMBER_VERIFIED -> {
                when (flavor) {
                    "live" -> "kx5hl6"
                    else -> "6zou42"
                }
            }
            AdjustEvent.SIGN_UP_END -> {
                when (flavor) {
                    "live" -> "skzf2k"
                    else -> "4c9qmq"
                }
            }
            AdjustEvent.SIGN_UP_START -> {
                when (flavor) {
                    "live" -> "w6rmpa"
                    else -> "73mcc8"
                }
            }
            AdjustEvent.TOP_UP_END -> {
                when (flavor) {
                    "live" -> "6yum4e"
                    else -> "jw0tz5"
                }
            }
            AdjustEvent.TOP_UP_START -> {
                when (flavor) {
                    "live" -> "vquxsb"
                    else -> "cadxmk"
                }
            }
            AdjustEvent.INVITER -> {
                when (flavor) {
                    "live" -> "efnby4"
                    else -> "sgy2ni"
                }
            }
            else -> "null"
        }
    }

    override fun toString(): String {
        return "BuildConfigManager(md5=$md5, sha1=$sha1, sha256=$sha256, leanPlumSecretKey=$leanPlumSecretKey, leanPlumKey=$leanPlumKey, adjustToken=$adjustToken, baseUrl=$baseUrl, buildType=$buildType, flavor=$flavor, versionName=$versionName, versionCode=$versionCode, applicationId=$applicationId, hasValidSignature=$hasValidSignature, sslPin1=$sslPin1, sslPin2=$sslPin2, sslPin3=$sslPin3, sslHost=$sslHost)"
    }

}