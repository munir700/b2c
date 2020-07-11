package co.yap.security

data class AppSignature(
    var md5: String?, var sha1: String?, var sha256: String?,
    var leanPlumSecretKey: String?,
    var leanPlumKey: String?,
    var adjustToken: String?,
    var baseUrl: String?,
    var buildType: String?,
    var flavor: String?,
    var versionName: String?,
    var versionCode: String?
) {
    fun isLiveRelease(): Boolean =
        (buildType == "release" && flavor == "live")

    override fun equals(other: Any?): Boolean {
        return if (other is AppSignature) {
            (this.sha1.equals(other.sha1, true)
                    && this.md5.equals(other.md5, true)
                    && this.sha256.equals(other.sha256, true))
        } else
            false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "AppSignature(md5=$md5, sha1=$sha1, sha256=$sha256, leanPlumSecretKey=$leanPlumSecretKey, leanPlumKey=$leanPlumKey, adjustToken=$adjustToken, baseUrl=$baseUrl, buildType=$buildType, flavor=$flavor, versionName=$versionName, versionCode=$versionCode)"
    }


}