package co.yap.yapcore.firebase

data class FirebaseTagManagerModel(
    var triggerType: String? = "Google_Analytics_Universal_Analytics",
    var trackType: String? = null,
    var category: String? = null,
    var action: String? = null,
    var label: String? = "Android"
)
