package co.yap.modules.dashboard.more.yapforyou

import android.graphics.Color
import co.yap.modules.dashboard.more.yapforyou.interfaces.IY4YComposer
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementTaskData
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.translation.Strings
import co.yap.yapcore.enums.Y4YAchievement
import co.yap.yapcore.enums.YFYAchievementTaskType

class Y4YGraphComposer : IY4YComposer {
    var list: ArrayList<Achievement> = arrayListOf()
    override fun compose(achievementsList: ArrayList<Achievement>): ArrayList<Y4YAchievementData> {
        this.list = achievementsList
        return arrayListOf(
            Y4YAchievementData(
                title = "Get Started",
                tintColor = getAchievementTintColor(Y4YAchievement.GET_STARTED),
                completedPercentage = getAchievementPercentage(Y4YAchievement.GET_STARTED),
                isLocked = isForceLocked(Y4YAchievement.GET_STARTED),
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        isLottie = false,
                        lottieFileName = "R.drawable.card_spare",
                        title = YFYAchievementTaskType.OPEN_YOUR_YAP_ACCOUNT.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.GET_STARTED,
                            YFYAchievementTaskType.OPEN_YOUR_YAP_ACCOUNT
                        ),
                        description = Strings.screen_yfy_open_yap_account_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "top_up_lottie.json",
                        title = YFYAchievementTaskType.SET_PIN.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.GET_STARTED,
                            YFYAchievementTaskType.SET_PIN
                        ),
                        description = Strings.screen_yfy_set_your_pin_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "top_up_lottie.json",
                        title = YFYAchievementTaskType.TOP_UP.title,
                        buttonTitle = Strings.screen_yfy_add_money_to_account_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.GET_STARTED,
                            YFYAchievementTaskType.TOP_UP
                        ),
                        description = Strings.screen_yfy_add_money_to_account_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "set_profile_picture_lottie.json",
                        title = YFYAchievementTaskType.SET_PROFILE_PICTURE.title,
                        buttonTitle = Strings.screen_yfy_set_a_profile_photo_button_label,
                        activityOnAction = SetCardPinWelcomeActivity::javaClass.name,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.GET_STARTED,
                            YFYAchievementTaskType.SET_PROFILE_PICTURE
                        ),
                        description = Strings.screen_yfy_set_a_profile_photo_description,
                        successDescription = ""
                    )
                )
            ),
            Y4YAchievementData(
                title = "Up and running",
                tintColor = getAchievementTintColor(Y4YAchievement.UP_AND_RUNNING),
                completedPercentage = getAchievementPercentage(Y4YAchievement.UP_AND_RUNNING),
                isLocked = isForceLocked(Y4YAchievement.UP_AND_RUNNING),
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "use_yap_locally.json",
                        title = YFYAchievementTaskType.USE_YAP_LOCALLY.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.UP_AND_RUNNING,
                            YFYAchievementTaskType.USE_YAP_LOCALLY
                        ),
                        description = Strings.screen_yfy_use_yap_locally_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "freez_and_unfreeze_your_card_lottie.json",
                        title = YFYAchievementTaskType.FREEZE_UNFREEZE_CARD.title,
                        buttonTitle = Strings.screen_yfy_freeze_unfreeze_card_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.UP_AND_RUNNING,
                            YFYAchievementTaskType.FREEZE_UNFREEZE_CARD
                        ),
                        description = Strings.screen_yfy_freeze_unfreeze_card_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "spend_aed_amount.json",
                        title = YFYAchievementTaskType.SPEND_AMOUNT.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.UP_AND_RUNNING,
                            YFYAchievementTaskType.SPEND_AMOUNT
                        ),
                        description = Strings.screen_yfy_spend_aed_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "top_up_lottie.json",
                        title = YFYAchievementTaskType.EXPLORE_CARD_CONTROLS.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.UP_AND_RUNNING,
                            YFYAchievementTaskType.EXPLORE_CARD_CONTROLS
                        ),
                        description = Strings.screen_yfy_explore_card_control_description,
                        successDescription = ""
                    )
                )
            ),
            Y4YAchievementData(
                title = "Better Together",
                tintColor = getAchievementTintColor(Y4YAchievement.BETTER_TOGETHER),
                completedPercentage = getAchievementPercentage(Y4YAchievement.BETTER_TOGETHER),
                isLocked = isForceLocked(Y4YAchievement.BETTER_TOGETHER),
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "invite_friend_lottie.json",
                        title = YFYAchievementTaskType.INVITE_FRIEND.title,
                        buttonTitle = Strings.screen_yfy_invite_a_friend_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.BETTER_TOGETHER,
                            YFYAchievementTaskType.INVITE_FRIEND
                        ),
                        description = Strings.screen_yfy_invite_a_friend_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "make_a_y2y_transfer_lottie.json",
                        title = YFYAchievementTaskType.MAKE_Y2Y_TRANSFER.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.BETTER_TOGETHER,
                            YFYAchievementTaskType.MAKE_Y2Y_TRANSFER
                        ),
                        description = Strings.screen_yfy_send_money_to_someone_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "top_up_lottie.json",
                        title = YFYAchievementTaskType.SPLIT_BILLS_WITH_YAP.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.BETTER_TOGETHER,
                            YFYAchievementTaskType.SPLIT_BILLS_WITH_YAP
                        ),
                        description = Strings.screen_yfy_split_bills_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "top_up_lottie.json",
                        title = YFYAchievementTaskType.SEND_MONEY_OUTSIDE_YAP.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.BETTER_TOGETHER,
                            YFYAchievementTaskType.SEND_MONEY_OUTSIDE_YAP
                        ),
                        description = Strings.screen_yfy_send_money_outside_yap_description,
                        successDescription = ""
                    )
                )
            ),
            Y4YAchievementData(
                title = "Take the leap",
                tintColor = getAchievementTintColor(Y4YAchievement.TAKE_THE_LEAP),
                completedPercentage = getAchievementPercentage(Y4YAchievement.TAKE_THE_LEAP),
                isLocked = isForceLocked(Y4YAchievement.TAKE_THE_LEAP),
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "top_up_lottie.json",
                        title = YFYAchievementTaskType.ORDER_SPARE_CARD.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.TAKE_THE_LEAP,
                            YFYAchievementTaskType.ORDER_SPARE_CARD
                        ),
                        description = Strings.screen_yfy_open_yap_account_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "top_up_lottie.json",
                        title = YFYAchievementTaskType.UPGRADE_TO_PRIME.title,
                        buttonTitle = Strings.screen_yfy_upgrade_to_prime_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.TAKE_THE_LEAP,
                            YFYAchievementTaskType.UPGRADE_TO_PRIME
                        ),
                        description = Strings.screen_yfy_upgrade_to_prime_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "top_up_lottie.json",
                        title = YFYAchievementTaskType.UPGRADE_TO_METAL.title,
                        buttonTitle = Strings.screen_yfy_upgrade_to_prime_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.TAKE_THE_LEAP,
                            YFYAchievementTaskType.UPGRADE_TO_METAL
                        ),
                        description = Strings.screen_yfy_go_metal_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "multicurrency_lottie.json",
                        title = YFYAchievementTaskType.SETUP_MULTI_CURRENCY.title,
                        buttonTitle = Strings.screen_yfy_set_multi_currency_account_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.TAKE_THE_LEAP,
                            YFYAchievementTaskType.SETUP_MULTI_CURRENCY
                        ),
                        description = Strings.screen_yfy_set_multi_currency_account_description,
                        successDescription = ""
                    )
                )
            ),
            Y4YAchievementData(
                title = "Yap store",
                tintColor = getAchievementTintColor(Y4YAchievement.YAP_STORE),
                completedPercentage = getAchievementPercentage(Y4YAchievement.YAP_STORE),
                isLocked = isForceLocked(Y4YAchievement.YAP_STORE),
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "cards_lottie.json",
                        title = YFYAchievementTaskType.GET_YAP_YOUNG.title,
                        buttonTitle = Strings.screen_yfy_get_yap_young_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YAP_STORE,
                            YFYAchievementTaskType.GET_YAP_YOUNG
                        ),
                        description = Strings.screen_yfy_get_yap_young_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "top_up_lottie.json",
                        title = YFYAchievementTaskType.GET_YAP_HOUSEHOLD.title,
                        buttonTitle = Strings.screen_yfy_signup_to_hh_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YAP_STORE,
                            YFYAchievementTaskType.GET_YAP_HOUSEHOLD
                        ),
                        description = Strings.screen_yfy_signup_to_hh_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "set_missions_lottie.json",
                        title = YFYAchievementTaskType.SET_MISSIONS_ON_YAP_YOUNG.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YAP_STORE,
                            YFYAchievementTaskType.SET_MISSIONS_ON_YAP_YOUNG
                        ),
                        description = Strings.screen_yfy_set_a_mission_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "top_up_lottie.json",
                        title = YFYAchievementTaskType.SALARY_TRANSFER_ON_YAP_HOUSEHOLD.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YAP_STORE,
                            YFYAchievementTaskType.SALARY_TRANSFER_ON_YAP_HOUSEHOLD
                        ),
                        description = Strings.screen_yfy_pay_your_help_description,
                        successDescription = ""
                    )
                )
            ),
            Y4YAchievementData(
                title = "You're a Pro!",
                tintColor = getAchievementTintColor(Y4YAchievement.YOU_ARE_PRO),
                completedPercentage = getAchievementPercentage(Y4YAchievement.YOU_ARE_PRO),
                isLocked = isForceLocked(Y4YAchievement.YOU_ARE_PRO),
                tasks = arrayListOf(
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "invite_friend_lottie.json",
                        title = YFYAchievementTaskType.INVITE_TEN_FRIENDS.title,
                        buttonTitle = Strings.screen_yfy_invite_ten_friends_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YOU_ARE_PRO,
                            YFYAchievementTaskType.INVITE_TEN_FRIENDS
                        ),
                        description = Strings.screen_yfy_invite_ten_friends_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "top_up_lottie.json",
                        title = YFYAchievementTaskType.SPEND_THOUSAND_AED.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YOU_ARE_PRO,
                            YFYAchievementTaskType.SPEND_THOUSAND_AED
                        ),
                        description = Strings.screen_yfy_spend_thousand_description,
                        successDescription = ""
                    ),
                    Y4YAchievementTaskData(
                        isLottie = true,
                        lottieFileName = "top_up_lottie.json",
                        title = YFYAchievementTaskType.COMPLETE_RENEWAL.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YOU_ARE_PRO,
                            YFYAchievementTaskType.COMPLETE_RENEWAL
                        ),
                        description = Strings.screen_yfy_complete_renewal_description,
                        successDescription = ""
                    )
                )
            )
        )
    }

    private fun checkIfLocked(previousAchievement: Y4YAchievement): Boolean =
        list.firstOrNull { it.achievementType == previousAchievement.name }?.isCompleted == false

    private fun isForceLocked(currentAchievement: Y4YAchievement): Boolean =
        list.firstOrNull { it.achievementType == currentAchievement.name }?.isForceLocked ?: true

    private fun getAchievementPercentage(currentAchievement: Y4YAchievement): Int =
        list.firstOrNull { it.achievementType == currentAchievement.name }?.percentage?.toInt() ?: 0


    private fun getAchievementTintColor(currentAchievement: Y4YAchievement): Int {
        return list.firstOrNull { it.achievementType == currentAchievement.name }?.color?.let {
            Color.parseColor(it)
        } ?: return -1
    }

    private fun checkIfTaskCompleted(
        currentAchievement: Y4YAchievement,
        achievementTask: YFYAchievementTaskType
    ): Boolean =
        list.firstOrNull { it.achievementType == currentAchievement.name }?.tasks?.firstOrNull {
            it.achievementTaskType == achievementTask.name
        }?.completion ?: false

}