package co.yap.networking

data class AppData(
    var flavor: String = "",
    var build_type: String = "",
    var baseUrl: String = ""
) {
    fun isReleaseMode(): Boolean =
        (build_type == "release" && flavor == "stg") || (build_type == "release" && flavor == "live")
}