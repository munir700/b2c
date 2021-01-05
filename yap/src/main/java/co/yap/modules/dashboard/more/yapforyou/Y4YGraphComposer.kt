package co.yap.modules.dashboard.more.yapforyou

import android.graphics.Color
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.yapforyou.interfaces.IY4YComposer
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.modules.dashboard.more.yapforyou.models.YAPForYouGoal
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyActivity
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.translation.Strings
import co.yap.yapcore.enums.Y4YAchievement
import co.yap.yapcore.enums.YAPForYouGoalMedia
import co.yap.yapcore.enums.YFYAchievementTaskType

class Y4YGraphComposer : IY4YComposer {
    var list: ArrayList<Achievement> = arrayListOf()
    override fun compose(achievementsList: ArrayList<Achievement>): ArrayList<Y4YAchievementData> {
        this.list = achievementsList
        return arrayListOf(
            Y4YAchievementData(
                title = Y4YAchievement.GET_STARTED.type,
                tintColor = getAchievementTintColor(Y4YAchievement.GET_STARTED),
                completedPercentage = getAchievementPercentage(Y4YAchievement.GET_STARTED),
                isLocked = isForceLocked(Y4YAchievement.GET_STARTED),
                goals = arrayListOf(
                    YAPForYouGoal(
                        isLottie = false,
                        lottieDetailsFileName = "ic_spare_card",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.OPEN_YOUR_YAP_ACCOUNT.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.GET_STARTED,
                            YFYAchievementTaskType.OPEN_YOUR_YAP_ACCOUNT
                        ),
                        description = Strings.screen_yfy_open_yap_account_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("R.drawable.ic_spare_card")
                    ),
                    YAPForYouGoal(
                        isLottie = false,
                        lottieDetailsFileName = "ic_set_pin",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.SET_PIN.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.GET_STARTED,
                            YFYAchievementTaskType.SET_PIN
                        ),
                        description = Strings.screen_yfy_set_your_pin_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("R.drawable.ic_spare_card")
                    ),
                    YAPForYouGoal(
                        isLottie = false,
                        lottieDetailsFileName = "ic_add_money",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.TOP_UP.title,
                        buttonTitle = Strings.screen_yfy_add_money_to_account_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.GET_STARTED,
                            YFYAchievementTaskType.TOP_UP
                        ),
                        activityOnAction = AddMoneyActivity::class.simpleName,
                        description = Strings.screen_yfy_add_money_to_account_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = false,
                        lottieDetailsFileName = "ic_set_profile",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.SET_PROFILE_PICTURE.title,
                        buttonTitle = Strings.screen_yfy_set_a_profile_photo_button_label,
                        activityOnAction = MoreActivity::javaClass.name,
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
                title = Y4YAchievement.UP_AND_RUNNING.type,
                tintColor = getAchievementTintColor(Y4YAchievement.UP_AND_RUNNING),
                completedPercentage = getAchievementPercentage(Y4YAchievement.UP_AND_RUNNING),
                isLocked = isForceLocked(Y4YAchievement.UP_AND_RUNNING),
                goals = arrayListOf(
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "use_yap_locally.json",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.USE_YAP_LOCALLY.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.UP_AND_RUNNING,
                            YFYAchievementTaskType.USE_YAP_LOCALLY
                        ),
                        description = Strings.screen_yfy_use_yap_locally_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "freez_and_unfreeze_your_card_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.FREEZE_UNFREEZE_CARD.title,
                        buttonTitle = Strings.screen_yfy_freeze_unfreeze_card_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.UP_AND_RUNNING,
                            YFYAchievementTaskType.FREEZE_UNFREEZE_CARD
                        ),
                        description = Strings.screen_yfy_freeze_unfreeze_card_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "spend_aed_amount.json",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.SPEND_AMOUNT.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.UP_AND_RUNNING,
                            YFYAchievementTaskType.SPEND_AMOUNT
                        ),
                        description = Strings.screen_yfy_spend_aed_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = false,
                        lottieDetailsFileName = "ic_card_controls",
                        lottieSuccessFileName = "success_lottie.json",
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
                title = Y4YAchievement.BETTER_TOGETHER.type,
                tintColor = getAchievementTintColor(Y4YAchievement.BETTER_TOGETHER),
                completedPercentage = getAchievementPercentage(Y4YAchievement.BETTER_TOGETHER),
                isLocked = isForceLocked(Y4YAchievement.BETTER_TOGETHER),
                goals = arrayListOf(
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "invite_friend_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.INVITE_FRIEND.title,
                        buttonTitle = Strings.screen_yfy_invite_a_friend_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.BETTER_TOGETHER,
                            YFYAchievementTaskType.INVITE_FRIEND
                        ),
                        activityOnAction = YFYAchievementTaskType.INVITE_FRIEND.title,
                        description = Strings.screen_yfy_invite_a_friend_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "make_a_y2y_transfer_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.MAKE_Y2Y_TRANSFER.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.BETTER_TOGETHER,
                            YFYAchievementTaskType.MAKE_Y2Y_TRANSFER
                        ),
                        description = Strings.screen_yfy_send_money_to_someone_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "ic_split_yap_bill",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.SPLIT_BILLS_WITH_YAP.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.BETTER_TOGETHER,
                            YFYAchievementTaskType.SPLIT_BILLS_WITH_YAP
                        ),
                        description = Strings.screen_yfy_split_bills_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = false,
                        lottieDetailsFileName = "ic_sm_out_side_yap",
                        lottieSuccessFileName = "success_lottie.json",
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
                title = Y4YAchievement.TAKE_THE_LEAP.type,
                tintColor = getAchievementTintColor(Y4YAchievement.TAKE_THE_LEAP),
                completedPercentage = getAchievementPercentage(Y4YAchievement.TAKE_THE_LEAP),
                isLocked = isForceLocked(Y4YAchievement.TAKE_THE_LEAP),
                goals = arrayListOf(
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "top_up_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.ORDER_SPARE_CARD.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.TAKE_THE_LEAP,
                            YFYAchievementTaskType.ORDER_SPARE_CARD
                        ),
                        description = Strings.screen_yfy_open_yap_account_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "top_up_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.UPGRADE_TO_PRIME.title,
                        buttonTitle = Strings.screen_yfy_upgrade_to_prime_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.TAKE_THE_LEAP,
                            YFYAchievementTaskType.UPGRADE_TO_PRIME
                        ),
                        description = Strings.screen_yfy_upgrade_to_prime_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "top_up_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.UPGRADE_TO_METAL.title,
                        buttonTitle = Strings.screen_yfy_upgrade_to_prime_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.TAKE_THE_LEAP,
                            YFYAchievementTaskType.UPGRADE_TO_METAL
                        ),
                        description = Strings.screen_yfy_go_metal_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "multicurrency_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
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
                title = Y4YAchievement.YAP_STORE.type,
                tintColor = getAchievementTintColor(Y4YAchievement.YAP_STORE),
                completedPercentage = getAchievementPercentage(Y4YAchievement.YAP_STORE),
                isLocked = isForceLocked(Y4YAchievement.YAP_STORE),
                goals = arrayListOf(
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "cards_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.GET_YAP_YOUNG.title,
                        buttonTitle = Strings.screen_yfy_get_yap_young_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YAP_STORE,
                            YFYAchievementTaskType.GET_YAP_YOUNG
                        ),
                        description = Strings.screen_yfy_get_yap_young_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "top_up_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.GET_YAP_HOUSEHOLD.title,
                        buttonTitle = Strings.screen_yfy_signup_to_hh_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YAP_STORE,
                            YFYAchievementTaskType.GET_YAP_HOUSEHOLD
                        ),
                        description = Strings.screen_yfy_signup_to_hh_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "set_missions_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.SET_MISSIONS_ON_YAP_YOUNG.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YAP_STORE,
                            YFYAchievementTaskType.SET_MISSIONS_ON_YAP_YOUNG
                        ),
                        description = Strings.screen_yfy_set_a_mission_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "top_up_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
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
                title = Y4YAchievement.YOU_ARE_PRO.type,
                tintColor = getAchievementTintColor(Y4YAchievement.YOU_ARE_PRO),
                completedPercentage = getAchievementPercentage(Y4YAchievement.YOU_ARE_PRO),
                isLocked = isForceLocked(Y4YAchievement.YOU_ARE_PRO),
                goals = arrayListOf(
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "invite_friend_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.INVITE_TEN_FRIENDS.title,
                        buttonTitle = Strings.screen_yfy_invite_ten_friends_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YOU_ARE_PRO,
                            YFYAchievementTaskType.INVITE_TEN_FRIENDS
                        ),
                        activityOnAction = YFYAchievementTaskType.INVITE_FRIEND.title,
                        description = Strings.screen_yfy_invite_ten_friends_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "top_up_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
                        title = YFYAchievementTaskType.SPEND_THOUSAND_AED.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YOU_ARE_PRO,
                            YFYAchievementTaskType.SPEND_THOUSAND_AED
                        ),
                        description = Strings.screen_yfy_spend_thousand_description,
                        successDescription = ""
                    ),
                    YAPForYouGoal(
                        isLottie = true,
                        lottieDetailsFileName = "top_up_lottie.json",
                        lottieSuccessFileName = "success_lottie.json",
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
