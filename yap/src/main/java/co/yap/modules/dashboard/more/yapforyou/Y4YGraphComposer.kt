package co.yap.modules.dashboard.more.yapforyou

import android.graphics.Color
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.PaymentCardDetailActivity
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.yapforyou.interfaces.IY4YComposer
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.modules.dashboard.more.yapforyou.models.YAPForYouGoal
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyActivity
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.translation.Strings
import co.yap.widgets.CoreButton
import co.yap.yapcore.enums.Y4YAchievement
import co.yap.yapcore.enums.YAPForYouGoalAction
import co.yap.yapcore.enums.YAPForYouGoalMedia
import co.yap.yapcore.enums.YapForYouGoalType
import co.yap.yapcore.managers.SessionManager

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
                completeAchievementIcon = R.drawable.ic_gs_badge,
                incompleteAchievementIcon = R.drawable.ic_gs_badge_faded,
                goals = arrayListOf(
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.OPEN_YOUR_YAP_ACCOUNT.title,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.OPEN_YOUR_YAP_ACCOUNT
                        ),
                        description = Strings.screen_yfy_open_yap_account_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_spare_card"),
                        locked = isGoalLocked(YapForYouGoalType.OPEN_YOUR_YAP_ACCOUNT)
                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SET_PIN.title,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.SET_PIN
                        ),
                        description = Strings.screen_yfy_set_your_pin_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_set_pin"),
                        locked = isGoalLocked(YapForYouGoalType.SET_PIN)
                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.TOP_UP.title,
                        action = YAPForYouGoalAction.Button(
                            title = Strings.screen_yfy_add_money_to_account_button_label,
                            enabled = true,
                            buttonSize = CoreButton.ButtonSize.SMALL
                        ),
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.TOP_UP
                        ),
                        activityOnAction = AddMoneyActivity::class.simpleName,
                        description = Strings.screen_yfy_add_money_to_account_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("top_up_lottie.json"),
                        completedMedia = YAPForYouGoalMedia.Image("ic_add_money_completed"),
                        locked = isGoalLocked(YapForYouGoalType.TOP_UP)
                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SET_PROFILE_PICTURE.title,
                        action = YAPForYouGoalAction.Button(
                            title = Strings.screen_yfy_set_a_profile_photo_button_label,
                            enabled = true,
                            buttonSize = CoreButton.ButtonSize.SMALL
                        ),
                        activityOnAction = MoreActivity::javaClass.name,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.SET_PROFILE_PICTURE
                        ),
                        description = Strings.screen_yfy_set_a_profile_photo_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("set_profile_picture_lottie.json"),
                        completedMedia = SessionManager.user?.currentCustomer?.getPicture()?.let {
                            YAPForYouGoalMedia.ImageUrl(
                                it
                            )
                        } ?: YAPForYouGoalMedia.LottieAnimation("set_profile_picture_lottie.json"),
                        locked = isGoalLocked(YapForYouGoalType.SET_PROFILE_PICTURE)
                    )
                )
            ),
            Y4YAchievementData(
                title = Y4YAchievement.UP_AND_RUNNING.type,
                tintColor = getAchievementTintColor(Y4YAchievement.UP_AND_RUNNING),
                completedPercentage = getAchievementPercentage(Y4YAchievement.UP_AND_RUNNING),
                isLocked = isForceLocked(Y4YAchievement.UP_AND_RUNNING),
                lastUpdated = getAchievementLastFunction(Y4YAchievement.UP_AND_RUNNING),
                completeAchievementIcon = R.drawable.ic_uar_badge,
                incompleteAchievementIcon = R.drawable.ic_uar_badge_faded,
                goals = arrayListOf(
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.LOCAL_USE.title,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.LOCAL_USE
                        ),
                        description = Strings.screen_yfy_use_yap_locally_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("use_yap_locally.json"),
                        completedMedia = YAPForYouGoalMedia.Image("ic_spare_card"),
                        locked = isGoalLocked(YapForYouGoalType.LOCAL_USE)
                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.FREEZE_UNFREEZE_CARD.title,
                        action = YAPForYouGoalAction.Button(
                            title = Strings.screen_yfy_freeze_unfreeze_card_button_label,
                            enabled = true,
                            buttonSize = CoreButton.ButtonSize.MEDIUM
                        ),
                        activityOnAction = PaymentCardDetailActivity::class.simpleName,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.FREEZE_UNFREEZE_CARD
                        ),
                        description = Strings.screen_yfy_freeze_unfreeze_card_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("freez_and_unfreeze_your_card_lottie.json"),
                        locked = isGoalLocked(YapForYouGoalType.SET_PROFILE_PICTURE)

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SPEND_AMOUNT.title,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.SPEND_AMOUNT
                        ),
                        description = Strings.screen_yfy_spend_aed_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("spend_aed_amount.json"),
                        completedMedia = YAPForYouGoalMedia.Image("ic_spend_aed_amount_completed"),
                        locked = isGoalLocked(YapForYouGoalType.SPEND_AMOUNT)

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.EXPLORE_CARD_CONTROLS.title,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.EXPLORE_CARD_CONTROLS
                        ),
                        description = Strings.screen_yfy_explore_card_control_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_card_controls"),
                        completedMedia = YAPForYouGoalMedia.Image("ic_card_controls_completed"),
                        locked = isGoalLocked(YapForYouGoalType.EXPLORE_CARD_CONTROLS)
                    )
                )
            ),
            Y4YAchievementData(
                title = Y4YAchievement.BETTER_TOGETHER.type,
                tintColor = getAchievementTintColor(Y4YAchievement.BETTER_TOGETHER),
                completedPercentage = getAchievementPercentage(Y4YAchievement.BETTER_TOGETHER),
                isLocked = isForceLocked(Y4YAchievement.BETTER_TOGETHER),
                lastUpdated = getAchievementLastFunction(Y4YAchievement.BETTER_TOGETHER),
                completeAchievementIcon = R.drawable.ic_bt_badge,
                incompleteAchievementIcon = R.drawable.ic_bt_badge_faded,
                goals = arrayListOf(
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.INVITE_FRIEND.title,
                        action = YAPForYouGoalAction.Button(
                            title = Strings.screen_yfy_invite_a_friend_button_label,
                            enabled = true,
                            buttonSize = CoreButton.ButtonSize.SMALL
                        ),
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.INVITE_FRIEND
                        ),
                        activityOnAction = YapForYouGoalType.INVITE_FRIEND.title,
                        description = Strings.screen_yfy_invite_a_friend_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("invite_friend_lottie.json"),
                        completedMedia = YAPForYouGoalMedia.Image("ic_goal_invite_friend_completed"),
                        locked = isGoalLocked(YapForYouGoalType.INVITE_FRIEND)

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.MAKE_Y2Y_TRANSFER.title,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.MAKE_Y2Y_TRANSFER
                        ),
                        description = Strings.screen_yfy_send_money_to_someone_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("make_a_y2y_transfer_lottie.json"),
                        locked = isGoalLocked(YapForYouGoalType.MAKE_Y2Y_TRANSFER)

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SPLIT_BILLS_WITH_YAP.title,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.SPLIT_BILLS_WITH_YAP
                        ),
                        description = Strings.screen_yfy_split_bills_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_split_yap_bill"),
                        locked = isGoalLocked(YapForYouGoalType.SPLIT_BILLS_WITH_YAP)

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SEND_MONEY_OUTSIDE_YAP.title,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.SEND_MONEY_OUTSIDE_YAP
                        ),
                        description = Strings.screen_yfy_send_money_outside_yap_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_sm_out_side_yap"),
                        locked = isGoalLocked(YapForYouGoalType.SEND_MONEY_OUTSIDE_YAP)

                    )
                )
            ),
            Y4YAchievementData(
                title = Y4YAchievement.TAKE_THE_LEAP.type,
                tintColor = getAchievementTintColor(Y4YAchievement.TAKE_THE_LEAP),
                completedPercentage = getAchievementPercentage(Y4YAchievement.TAKE_THE_LEAP),
                isLocked = isForceLocked(Y4YAchievement.TAKE_THE_LEAP),
                lastUpdated = getAchievementLastFunction(Y4YAchievement.TAKE_THE_LEAP),
                completeAchievementIcon = R.drawable.ic_ttl_badge,
                incompleteAchievementIcon = R.drawable.ic_ttl_badge_faded,
                goals = arrayListOf(
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.ORDER_SPARE_CARD.title,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.ORDER_SPARE_CARD
                        ),
                        description = Strings.screen_yfy_order_virtual_card_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_virtual_card"),
                        locked = isGoalLocked(YapForYouGoalType.ORDER_SPARE_CARD)

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.UPGRADE_TO_PRIME.title,
                        action = YAPForYouGoalAction.Button(
                            title = Strings.screen_yfy_upgrade_to_prime_button_label,
                            enabled = false,
                            buttonSize = CoreButton.ButtonSize.SMALL
                        ),
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.UPGRADE_TO_PRIME
                        ),
                        description = Strings.screen_yfy_upgrade_to_prime_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_prime_card"),
                        locked = isGoalLocked(YapForYouGoalType.UPGRADE_TO_PRIME)

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.UPGRADE_TO_METAL.title,
                        action = YAPForYouGoalAction.Button(
                            title = Strings.screen_yfy_upgrade_to_prime_button_label,
                            enabled = false,
                            buttonSize = CoreButton.ButtonSize.SMALL
                        ),
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.UPGRADE_TO_METAL
                        ),
                        description = Strings.screen_yfy_go_metal_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_metal_card"),
                        locked = isGoalLocked(YapForYouGoalType.UPGRADE_TO_METAL)

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SETUP_MULTI_CURRENCY.title,
                        action = YAPForYouGoalAction.Button(
                            title = Strings.screen_yfy_set_multi_currency_account_button_label,
                            enabled = false,
                            buttonSize = CoreButton.ButtonSize.SMALL
                        ),
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.SETUP_MULTI_CURRENCY
                        ),
                        description = Strings.screen_yfy_set_multi_currency_account_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("multicurrency_lottie.json"),
                        completedMedia = YAPForYouGoalMedia.Image("ic_goal_setup_multi_currency_completed"),
                        locked = isGoalLocked(YapForYouGoalType.SETUP_MULTI_CURRENCY)

                    )
                )
            ),
            Y4YAchievementData(
                title = Y4YAchievement.YAP_STORE.type,
                tintColor = getAchievementTintColor(Y4YAchievement.YAP_STORE),
                completedPercentage = getAchievementPercentage(Y4YAchievement.YAP_STORE),
                isLocked = isForceLocked(Y4YAchievement.YAP_STORE),
                lastUpdated = getAchievementLastFunction(Y4YAchievement.YAP_STORE),
                completeAchievementIcon = R.drawable.ic_ys_badge,
                incompleteAchievementIcon = R.drawable.ic_ys_badge_faded,
                goals = arrayListOf(
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.GET_YAP_YOUNG.title,
                        action = YAPForYouGoalAction.Button(
                            title = Strings.screen_yfy_get_yap_young_button_label,
                            enabled = false,
                            buttonSize = CoreButton.ButtonSize.MEDIUM
                        ),
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.GET_YAP_YOUNG
                        ),
                        description = Strings.screen_yfy_get_yap_young_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("cards_lottie.json"),
                        completedMedia = YAPForYouGoalMedia.Image("ic_goal_young_card_completed"),
                        locked = isGoalLocked(YapForYouGoalType.GET_YAP_YOUNG)

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.GET_YAP_HOUSEHOLD.title,
                        action = YAPForYouGoalAction.Button(
                            title = Strings.screen_yfy_signup_to_hh_button_label,
                            enabled = false,
                            buttonSize = CoreButton.ButtonSize.LARGE
                        ),
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.GET_YAP_HOUSEHOLD
                        ),
                        description = Strings.screen_yfy_signup_to_hh_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_hh_card"),
                        locked = isGoalLocked(YapForYouGoalType.GET_YAP_HOUSEHOLD)

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SET_MISSIONS_ON_YAP_YOUNG.title,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.SET_MISSIONS_ON_YAP_YOUNG
                        ),
                        description = Strings.screen_yfy_set_a_mission_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("set_missions_lottie.json"),
                        completedMedia = YAPForYouGoalMedia.Image("ic_goal_set_mission_young_completed"),
                        locked = isGoalLocked(YapForYouGoalType.SET_MISSIONS_ON_YAP_YOUNG)

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SALARY_TRANSFER_ON_YAP_HOUSEHOLD.title,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.SALARY_TRANSFER_ON_YAP_HOUSEHOLD
                        ),
                        description = Strings.screen_yfy_pay_your_help_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_hh_salary_transfer"),
                        locked = isGoalLocked(YapForYouGoalType.SALARY_TRANSFER_ON_YAP_HOUSEHOLD)

                    )
                )
            ),
            Y4YAchievementData(
                title = Y4YAchievement.YOU_ARE_A_PRO.type,
                tintColor = getAchievementTintColor(Y4YAchievement.YOU_ARE_A_PRO),
                completedPercentage = getAchievementPercentage(Y4YAchievement.YOU_ARE_A_PRO),
                isLocked = isForceLocked(Y4YAchievement.YOU_ARE_A_PRO),
                lastUpdated = getAchievementLastFunction(Y4YAchievement.YOU_ARE_A_PRO),
                completeAchievementIcon = R.drawable.ic_yrp_badge,
                incompleteAchievementIcon = R.drawable.ic_yrp_badge_faded,
                goals = arrayListOf(
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.INVITE_TEN_FRIENDS.title,
                        action = YAPForYouGoalAction.Button(
                            title = Strings.screen_yfy_invite_ten_friends_button_label,
                            enabled = true,
                            buttonSize = CoreButton.ButtonSize.SMALL
                        ),
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.INVITE_TEN_FRIENDS
                        ),
                        activityOnAction = YapForYouGoalType.INVITE_FRIEND.title,
                        description = Strings.screen_yfy_invite_ten_friends_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("invite_friend_lottie.json"),
                        completedMedia = YAPForYouGoalMedia.Image("ic_goal_invite_friend_completed"),
                        locked = isGoalLocked(YapForYouGoalType.INVITE_TEN_FRIENDS)

                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.SPEND_THOUSAND_AED.title,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.SPEND_THOUSAND_AED
                        ),
                        description = Strings.screen_yfy_spend_thousand_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.LottieAnimation("spend_aed_amount.json"),
                        completedMedia = YAPForYouGoalMedia.Image("ic_spend_aed_amount_completed"),
                        locked = isGoalLocked(YapForYouGoalType.SPEND_THOUSAND_AED)
                    ),
                    YAPForYouGoal(
                        lottieSuccessFileName = YAPForYouGoalMedia.LottieAnimation("success_lottie.json"),
                        title = YapForYouGoalType.COMPLETE_RENEWAL.title,
                        isDone = checkIfTaskCompleted(
                            YapForYouGoalType.COMPLETE_RENEWAL
                        ),
                        description = Strings.screen_yfy_complete_renewal_description,
                        successDescription = "",
                        media = YAPForYouGoalMedia.Image("ic_complete_renewal"),
                        locked = isGoalLocked(YapForYouGoalType.COMPLETE_RENEWAL)
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

    private fun isGoalLocked(goalType: YapForYouGoalType): Boolean =
        list.flatMap { it.goals!!.toList() }.firstOrNull {
            it.achievementTaskType == goalType.name
        }?.locked ?: false


    private fun checkIfTaskCompleted(
        goalType: YapForYouGoalType
    ): Boolean =
        list.flatMap { it.goals!!.toList() }.firstOrNull {
            it.achievementTaskType == goalType.name
        }?.completion ?: false
}
