package co.yap.modules.dashboard.more.yapforyou.models

data class Y4YAchievementTaskData(
    var lottieFileName: String?,
    var isLottie: Boolean,
    var title: String,
    var buttonTitle: String? = null,
    var isDone: Boolean,
    var activityOnAction: String? = null,
    var description: String = "",
    var successDescription: String = ""
)