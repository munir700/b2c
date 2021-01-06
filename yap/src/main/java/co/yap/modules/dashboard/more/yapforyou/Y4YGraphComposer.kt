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
import co.yap.yapcore.enums.YapForYouGoalType

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
                lastUpdated = getAchievementLastFunction(Y4YAchievement.GET_STARTED),
                goals = arrayListOf(
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.OPEN_YOUR_YAP_ACCOUNT.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.GET_STARTED,
                            YapForYouGoalType.OPEN_YOUR_YAP_ACCOUNT
                        ),
                        description = Strings.screen_yfy_open_yap_account_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_spare_card")
                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SET_PIN.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.GET_STARTED,
                            YapForYouGoalType.SET_PIN
                        ),
                        description = Strings.screen_yfy_set_your_pin_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_set_pin")
                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.TOP_UP.title,
                        buttonTitle = Strings.screen_yfy_add_money_to_account_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.GET_STARTED,
                            YapForYouGoalType.TOP_UP
                        ),
                        activityOnAction = AddMoneyActivity::class.simpleName,
                        description = Strings.screen_yfy_add_money_to_account_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_add_money")
                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SET_PROFILE_PICTURE.title,
                        buttonTitle = Strings.screen_yfy_set_a_profile_photo_button_label,
                        activityOnAction = MoreActivity::javaClass.name,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.GET_STARTED,
                            YapForYouGoalType.SET_PROFILE_PICTURE
                        ),
                        description = Strings.screen_yfy_set_a_profile_photo_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_set_profile")

                    )
                )
            ),
            Y4YAchievementData(
                title = Y4YAchievement.UP_AND_RUNNING.type,
                tintColor = getAchievementTintColor(Y4YAchievement.UP_AND_RUNNING),
                completedPercentage = getAchievementPercentage(Y4YAchievement.UP_AND_RUNNING),
                isLocked = isForceLocked(Y4YAchievement.UP_AND_RUNNING),
                lastUpdated = getAchievementLastFunction(Y4YAchievement.UP_AND_RUNNING),
                goals = arrayListOf(
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.LOCAL_USE.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.UP_AND_RUNNING,
                            YapForYouGoalType.LOCAL_USE
                        ),
                        description = Strings.screen_yfy_use_yap_locally_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("use_yap_locally.json")
                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.FREEZE_UNFREEZE_CARD.title,
                        buttonTitle = Strings.screen_yfy_freeze_unfreeze_card_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.UP_AND_RUNNING,
                            YapForYouGoalType.FREEZE_UNFREEZE_CARD
                        ),
                        description = Strings.screen_yfy_freeze_unfreeze_card_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("freez_and_unfreeze_your_card_lottie.json")

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SPEND_AMOUNT.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.UP_AND_RUNNING,
                            YapForYouGoalType.SPEND_AMOUNT
                        ),
                        description = Strings.screen_yfy_spend_aed_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("spend_aed_amount.json")

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.EXPLORE_CARD_CONTROLS.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.UP_AND_RUNNING,
                            YapForYouGoalType.EXPLORE_CARD_CONTROLS
                        ),
                        description = Strings.screen_yfy_explore_card_control_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_card_controls")

                    )
                )
            ),
            Y4YAchievementData(
                title = Y4YAchievement.BETTER_TOGETHER.type,
                tintColor = getAchievementTintColor(Y4YAchievement.BETTER_TOGETHER),
                completedPercentage = getAchievementPercentage(Y4YAchievement.BETTER_TOGETHER),
                isLocked = isForceLocked(Y4YAchievement.BETTER_TOGETHER),
                lastUpdated = getAchievementLastFunction(Y4YAchievement.BETTER_TOGETHER),
                goals = arrayListOf(
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.INVITE_FRIEND.title,
                        buttonTitle = Strings.screen_yfy_invite_a_friend_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.BETTER_TOGETHER,
                            YapForYouGoalType.INVITE_FRIEND
                        ),
                        activityOnAction = YapForYouGoalType.INVITE_FRIEND.title,
                        description = Strings.screen_yfy_invite_a_friend_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("invite_friend_lottie.json")

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.MAKE_Y2Y_TRANSFER.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.BETTER_TOGETHER,
                            YapForYouGoalType.MAKE_Y2Y_TRANSFER
                        ),
                        description = Strings.screen_yfy_send_money_to_someone_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("make_a_y2y_transfer_lottie.json")

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SPLIT_BILLS_WITH_YAP.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.BETTER_TOGETHER,
                            YapForYouGoalType.SPLIT_BILLS_WITH_YAP
                        ),
                        description = Strings.screen_yfy_split_bills_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_split_yap_bill")

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SEND_MONEY_OUTSIDE_YAP.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.BETTER_TOGETHER,
                            YapForYouGoalType.SEND_MONEY_OUTSIDE_YAP
                        ),
                        description = Strings.screen_yfy_send_money_outside_yap_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_sm_out_side_yap")

                    )
                )
            ),
            Y4YAchievementData(
                title = Y4YAchievement.TAKE_THE_LEAP.type,
                tintColor = getAchievementTintColor(Y4YAchievement.TAKE_THE_LEAP),
                completedPercentage = getAchievementPercentage(Y4YAchievement.TAKE_THE_LEAP),
                isLocked = isForceLocked(Y4YAchievement.TAKE_THE_LEAP),
                lastUpdated = getAchievementLastFunction(Y4YAchievement.TAKE_THE_LEAP),
                goals = arrayListOf(
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.ORDER_SPARE_CARD.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.TAKE_THE_LEAP,
                            YapForYouGoalType.ORDER_SPARE_CARD
                        ),
                        description = Strings.screen_yfy_open_yap_account_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_virtual_card")

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.UPGRADE_TO_PRIME.title,
                        buttonTitle = Strings.screen_yfy_upgrade_to_prime_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.TAKE_THE_LEAP,
                            YapForYouGoalType.UPGRADE_TO_PRIME
                        ),
                        description = Strings.screen_yfy_upgrade_to_prime_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_prime_card")

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.UPGRADE_TO_METAL.title,
                        buttonTitle = Strings.screen_yfy_upgrade_to_prime_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.TAKE_THE_LEAP,
                            YapForYouGoalType.UPGRADE_TO_METAL
                        ),
                        description = Strings.screen_yfy_go_metal_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_metal_card")

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SETUP_MULTI_CURRENCY.title,
                        buttonTitle = Strings.screen_yfy_set_multi_currency_account_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.TAKE_THE_LEAP,
                            YapForYouGoalType.SETUP_MULTI_CURRENCY
                        ),
                        description = Strings.screen_yfy_set_multi_currency_account_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("multicurrency_lottie.json")

                    )
                )
            ),
            Y4YAchievementData(
                title = Y4YAchievement.YAP_STORE.type,
                tintColor = getAchievementTintColor(Y4YAchievement.YAP_STORE),
                completedPercentage = getAchievementPercentage(Y4YAchievement.YAP_STORE),
                isLocked = isForceLocked(Y4YAchievement.YAP_STORE),
                lastUpdated = getAchievementLastFunction(Y4YAchievement.YAP_STORE),
                goals = arrayListOf(
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.GET_YAP_YOUNG.title,
                        buttonTitle = Strings.screen_yfy_get_yap_young_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YAP_STORE,
                            YapForYouGoalType.GET_YAP_YOUNG
                        ),
                        description = Strings.screen_yfy_get_yap_young_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("cards_lottie.json")

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.GET_YAP_HOUSEHOLD.title,
                        buttonTitle = Strings.screen_yfy_signup_to_hh_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YAP_STORE,
                            YapForYouGoalType.GET_YAP_HOUSEHOLD
                        ),
                        description = Strings.screen_yfy_signup_to_hh_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("ic_hh_card")

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SET_MISSIONS_ON_YAP_YOUNG.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YAP_STORE,
                            YapForYouGoalType.SET_MISSIONS_ON_YAP_YOUNG
                        ),
                        description = Strings.screen_yfy_set_a_mission_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("set_missions_lottie.json")

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SALARY_TRANSFER_ON_YAP_HOUSEHOLD.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YAP_STORE,
                            YapForYouGoalType.SALARY_TRANSFER_ON_YAP_HOUSEHOLD
                        ),
                        description = Strings.screen_yfy_pay_your_help_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("ic_hh_salary_transfer")

                    )
                )
            ),
            Y4YAchievementData(
                title = Y4YAchievement.YOU_ARE_A_PRO.type,
                tintColor = getAchievementTintColor(Y4YAchievement.YOU_ARE_A_PRO),
                completedPercentage = getAchievementPercentage(Y4YAchievement.YOU_ARE_A_PRO),
                isLocked = isForceLocked(Y4YAchievement.YOU_ARE_A_PRO),
                lastUpdated = getAchievementLastFunction(Y4YAchievement.YOU_ARE_A_PRO),
                goals = arrayListOf(
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.INVITE_TEN_FRIENDS.title,
                        buttonTitle = Strings.screen_yfy_invite_ten_friends_button_label,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YOU_ARE_A_PRO,
                            YapForYouGoalType.INVITE_TEN_FRIENDS
                        ),
                        activityOnAction = YapForYouGoalType.INVITE_FRIEND.title,
                        description = Strings.screen_yfy_invite_ten_friends_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("invite_friend_lottie.json")

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SPEND_THOUSAND_AED.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YOU_ARE_A_PRO,
                            YapForYouGoalType.SPEND_THOUSAND_AED
                        ),
                        description = Strings.screen_yfy_spend_thousand_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("spend_aed_amount.json")
                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.COMPLETE_RENEWAL.title,
                        isDone = checkIfTaskCompleted(
                            Y4YAchievement.YOU_ARE_A_PRO,
                            YapForYouGoalType.COMPLETE_RENEWAL
                        ),
                        description = Strings.screen_yfy_complete_renewal_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("ic_complete_renewal")

                    )
                )
            )
        )
    }

    private fun isForceLocked(currentAchievement: Y4YAchievement): Boolean =
        list.firstOrNull { it.achievementType == currentAchievement.name }?.isForceLocked ?: true

    private fun getAchievementPercentage(currentAchievement: Y4YAchievement): Int =
        list.firstOrNull { it.achievementType == currentAchievement.name }?.percentage?.toInt() ?: 0

    private fun getAchievementLastFunction(currentAchievement: Y4YAchievement): String =
        list.firstOrNull { it.achievementType == currentAchievement.name }?.lastUpdated ?: ""

    private fun getAchievementTintColor(currentAchievement: Y4YAchievement): Int {
        return list.firstOrNull { it.achievementType == currentAchievement.name }?.color?.let {
            Color.parseColor(it)
        } ?: return -1
    }

    private fun checkIfTaskCompleted(
        currentAchievement: Y4YAchievement,
        achievementTask: YapForYouGoalType
    ): Boolean =
        list.firstOrNull { it.achievementType == currentAchievement.name }?.tasks?.firstOrNull {
            it.achievementTaskType == achievementTask.name
        }?.completion ?: false
}
