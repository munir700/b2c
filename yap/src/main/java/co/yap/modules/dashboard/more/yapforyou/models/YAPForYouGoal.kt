package co.yap.modules.dashboard.more.yapforyou.models

import co.yap.yapcore.enums.YAPForYouGoalMedia

data class YAPForYouGoal(
    var lottieSuccessFileName: String?,
    var title: String,
    var buttonTitle: String? = null,
    var isDone: Boolean,
    var activityOnAction: String? = null,
    var description: String = "",
    var successDescription: String = "",
    var media: YAPForYouGoalMedia? = null
)