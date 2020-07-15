package co.yap.yapcore.config

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
    var hasValidSignature: Boolean = false,
    var sslPin1: String?,
    var sslPin2: String?,
    var sslPin3: String?,
    var sslHost: String?
) {
    fun isLiveRelease(): Boolean =
        (buildType == "release" && flavor == "live") || (buildType == "release" && flavor == "stg")

}