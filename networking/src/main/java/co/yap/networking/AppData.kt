package co.yap.networking

data class AppData(
    var flavor: String = "",
    var build_type: String = "",
    var baseUrl: String = ""
) {
    fun isReleaseStg(): Boolean = build_type == "release" && flavor == "stg"
}