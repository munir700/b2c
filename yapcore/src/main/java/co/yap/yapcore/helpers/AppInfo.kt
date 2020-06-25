package co.yap.yapcore.helpers

data class AppInfo(
    var version_name: String = "",
    var version_code: Int = 0,
    var flavor: String = "",
    var build_type: String = "",
    var baseUrl: String? = null
) {

    fun isReleaseStg(): Boolean =
        (build_type == "release" && flavor == "stg") || (build_type == "release" && flavor == "qa")

}