package co.yap.modules.dashboard.more.yapforyou

import android.graphics.Color
import co.yap.modules.dashboard.more.yapforyou.interfaces.IY4YComposer
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementTaskData
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.yapcore.enums.Y4YAchievement

class Y4YGraphComposer : IY4YComposer {
    var list: ArrayList<Achievement> = arrayListOf()
    override fun compose(achievementsList: ArrayList<Achievement>): ArrayList<Y4YAchievementData> {
        this.list = achievementsList
        return arrayListOf(
            Y4YAchievementData(
                title = "Get Started",
                tintColor = getAchievementTintColor(Y4YAchievement.GET_STARTED),
                completedPercentage = getAchievementPercentage(Y4YAchievement.GET_STARTED),
                isLocked = false,
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Open yap Account",
                        buttonTitle = null,
                        isDone = true
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Set your PIN",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Add money to your account",
                        buttonTitle = "Add money now",
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Set a profile photo",
                        buttonTitle = "Set it now",
                        isDone = false
                    )
                )
            ),
            Y4YAchievementData(
                title = "Up and running",
                tintColor = getAchievementTintColor(Y4YAchievement.UP_AND_RUNNING),
                completedPercentage = getAchievementPercentage(Y4YAchievement.UP_AND_RUNNING),
                isLocked = checkIfLocked(Y4YAchievement.GET_STARTED),
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Use YAP locally",
                        buttonTitle = null,
                        isDone = true
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Freeze and unfreeze your card",
                        buttonTitle = "Try freezing now",
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Spend AED 100",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Explore card controls",
                        buttonTitle = null,
                        isDone = false
                    )
                )
            ),
            Y4YAchievementData(
                title = "Better Together",
                tintColor = getAchievementTintColor(Y4YAchievement.BETTER_TOGETHER),
                completedPercentage = getAchievementPercentage(Y4YAchievement.BETTER_TOGETHER),
                isLocked = checkIfLocked(Y4YAchievement.UP_AND_RUNNING),

                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Invite a friend",
                        buttonTitle = "Invite a friend",
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Make a Y2Y transfer",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Split bills with YAP",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Send money outside YAP",
                        buttonTitle = null,
                        isDone = false
                    )
                )
            ),
            Y4YAchievementData(
                title = "Take the leap",
                tintColor = getAchievementTintColor(Y4YAchievement.TAKE_THE_LEAP),
                completedPercentage = getAchievementPercentage(Y4YAchievement.TAKE_THE_LEAP),
                isLocked = checkIfLocked(Y4YAchievement.BETTER_TOGETHER),
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Order a virtual card",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Upgrade to Prime",
                        buttonTitle = "Upgrade now",
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Go Metal",
                        buttonTitle = "Upgrade now",
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Set up a multi-currency account",
                        buttonTitle = "Set up",
                        isDone = false
                    )
                )
            ),
            Y4YAchievementData(
                title = "Yap store",
                tintColor = getAchievementTintColor(Y4YAchievement.YAP_STORE),
                completedPercentage = getAchievementPercentage(Y4YAchievement.YAP_STORE),
                isLocked = checkIfLocked(Y4YAchievement.TAKE_THE_LEAP),
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Get YAP Young",
                        buttonTitle = "Get YAP Young now",
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Sign up to Household",
                        buttonTitle = "Get YAP Household now",
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Set missions on YAP Young",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Pay your help using Household",
                        buttonTitle = null,
                        isDone = false
                    )
                )
            ),
            Y4YAchievementData(
                title = "You're a Pro!",
                tintColor = getAchievementTintColor(Y4YAchievement.YOU_ARE_PRO),
                completedPercentage = getAchievementPercentage(Y4YAchievement.YOU_ARE_PRO),
                isLocked = checkIfLocked(Y4YAchievement.YAP_STORE),
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Invite 10 friends to YAP",
                        buttonTitle = "Invite friend",
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Spend AED 1000",
                        buttonTitle = null,
                        isDone = false
                    ),
                    Y4YAchievementTaskData(
                        lottieFileName = "abc.json",
                        title = "Complete a renewal",
                        buttonTitle = null,
                        isDone = false
                    )
                )
            )
        )
    }

    private fun checkIfLocked(previousAchievement: Y4YAchievement): Boolean =
        list.firstOrNull { it.acheivementType == previousAchievement.name }?.isCompleted == false

    private fun getAchievementPercentage(currentAchievement: Y4YAchievement): Int =
        list.firstOrNull { it.acheivementType == currentAchievement.name }?.percentage?.toInt() ?: 0


    private fun getAchievementTintColor(currentAchievement: Y4YAchievement): Int {
        return list.firstOrNull { it.acheivementType == currentAchievement.name }?.color?.let {
            Color.parseColor(it)
        } ?: return -1
    }
}