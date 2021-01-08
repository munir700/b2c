package co.yap.modules.dashboard.more.yapforyou.models

import co.yap.yapcore.enums.YAPForYouGoalAction
import co.yap.yapcore.enums.YAPForYouGoalMedia

data class YAPForYouGoal(
    var lottieSuccessFileName: YAPForYouGoalMedia? = null,
    var title: String,
    var action: YAPForYouGoalAction = YAPForYouGoalAction.None,
    var isDone: Boolean,
    var activityOnAction: String? = null,
    var description: String = "",
    var successDescription: String = "",
    var media: YAPForYouGoalMedia? = null,
    var completedMedia: YAPForYouGoalMedia? = null
)