package co.yap.yapcore.config

data class BuildConfigManager(
    var leanPlumSecretKey: String = "",
    var leanPlumKey: String = "",
    var adjustToken: String = "",
    var apiEndPoint: String = "",
    var buildType: String = "",
    var flavor: String = "",
    var versionName: String = "",
    var versionCode: String = ""
) {
    fun isLiveRelease(): Boolean =
        (buildType == "release" && flavor == "live")
}