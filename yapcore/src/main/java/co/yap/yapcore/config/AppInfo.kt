package co.yap.yapcore.config

data class AppInfo(
    var version_name: String = "",
    var version_code: Int = 0,
    var flavor: String = "",
    var build_type: String = "",
    var baseUrl: String? = null
) {

    fun isLiveRelease(): Boolean =
        (build_type == "release" && flavor == "live")
}