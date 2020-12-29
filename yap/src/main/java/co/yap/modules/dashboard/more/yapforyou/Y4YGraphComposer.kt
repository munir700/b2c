package co.yap.modules.dashboard.more.yapforyou

import android.graphics.Color
import co.yap.modules.dashboard.more.yapforyou.interfaces.IY4YComposer
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementTaskData
import co.yap.networking.transactions.responsedtos.achievement.Achievement

class Y4YGraphComposer : IY4YComposer {
    var list: ArrayList<Achievement> = arrayListOf()
    override fun compose(achievementsList: ArrayList<Achievement>): ArrayList<Y4YAchievementData> {
        this.list = achievementsList
        return arrayListOf(
            Y4YAchievementData(
                title = "Get Started",
                tintColor = Color.parseColor("#DFFFEEE"),
                completedPercentage = 100,
                isLocked = false,
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    )
                )
            ),
            Y4YAchievementData(
                title = "Up and running",
                tintColor = Color.parseColor("#DFFFEEE"),
                completedPercentage = 100,
                isLocked = checkIfLocked("GET_STARTED"),
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    )
                )
            ),
            Y4YAchievementData(
                title = "Better Together",
                tintColor = Color.parseColor("#DFFFEEE"),
                completedPercentage = 100,
                isLocked = checkIfLocked("UP_AND_RUNNING"),

                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    )
                )
            ),
            Y4YAchievementData(
                title = "Take the leap",
                tintColor = Color.parseColor("#DFFFEEE"),
                completedPercentage = 100,
                isLocked = checkIfLocked("BETTER_TOGETHER"),
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    )
                )
            ),
            Y4YAchievementData(
                title = "Yap store",
                tintColor = Color.parseColor("#DFFFEEE"),
                completedPercentage = 100,
                isLocked = false,
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    )
                )
            ),
            Y4YAchievementData(
                title = "You're a Pro!",
                tintColor = Color.parseColor("#DFFFEEE"),
                completedPercentage = 100,
                isLocked = false,
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = false
                    )
                )
            )
        )
    }

    private fun checkIfLocked(previousAchievement: String): Boolean =
        list.firstOrNull { it.acheivementType == previousAchievement }?.isCompleted ?: true


    private fun getAchievementPercentageFromResponse(currentAchievement: String): Int =
        list.firstOrNull { it.acheivementType == currentAchievement }?.percentage?.toInt() ?: 0


    private fun getAchievementTintColor(currentAchievement: String): Int =
       Color.parseColor(list.firstOrNull { it.acheivementType == currentAchievement }?.color)

}