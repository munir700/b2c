package co.yap.networking

data class AppData(
    var version_name: String = "",
    var version_code: Int = 0,
    var flavor: String = "",
    var build_type: String = "",
    var baseUrl: String = ""
) {
    fun isReleaseStg(): Boolean = build_type == "release" && flavor == "stg"
}