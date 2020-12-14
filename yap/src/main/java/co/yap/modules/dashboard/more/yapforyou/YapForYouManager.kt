package co.yap.modules.dashboard.more.yapforyou

import android.content.Context
import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.models.YapForYouDataModel
import co.yap.translation.Strings
import co.yap.translation.Translator.getString
import co.yap.yapcore.enums.YFYAchievementType

class YapForYouManager(val context: Context) {
    private var yfyContentMap: HashMap<String, YapForYouDataModel>? = null
    
    fun initializeYFYData(tag: String) {
        when (tag) {
            YFYAchievementType.GET_STARTED.type -> {
                yfyContentMap = getStartedContent()

            }
            YFYAchievementType.UP_RUNNING.type -> {
                yfyContentMap = getUpAndRunningContent()

            }
            YFYAchievementType.BETTER_TOGETHER.type -> {
                yfyContentMap = getBetterTogetherContent()

            }
            YFYAchievementType.TAKE_THE_LEAP.type -> {
                yfyContentMap = getTakeLeapContent()

            }
            YFYAchievementType.YAP_STORE.type -> {
                yfyContentMap = getYapStoreContent()

            }
            YFYAchievementType.YOU_ARE_PRO.type -> {
                yfyContentMap = getYouAreProContent()

            }
        }

    }

    private fun getStartedContent(): HashMap<String, YapForYouDataModel> {
        val hashMap: HashMap<String, YapForYouDataModel> = HashMap()
        hashMap[YFYAchievementType.OPEN_YAP_ACCOUNT.type] = YapForYouDataModel(
            title = "",
            description =getString(context,Strings.screen_yfy_open_yap_account_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.TOP_UP.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_set_your_pin_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.ADD_CARD.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_add_money_to_account_description),
            image = R.drawable.card_spare,
            buttonVisibility = true,
            buttonTitle = getString(context,Strings.screen_yfy_add_money_to_account_button_label)
        )
        hashMap[YFYAchievementType.SET_PROFILE_PICTURE.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_set_a_profile_photo_description),
            image = R.drawable.card_spare,
            buttonVisibility = true,
            buttonTitle = getString(context,Strings.screen_yfy_set_a_profile_photo_button_label)
        )
        return hashMap
    }

    private fun getUpAndRunningContent(): HashMap<String, YapForYouDataModel> {
        val hashMap: HashMap<String, YapForYouDataModel> = HashMap()
        hashMap[YFYAchievementType.USE_YAP_LOCALLY.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_use_yap_locally_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.FREEZE_AND_UNFREEZE.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_freeze_unfreeze_card_description),
            image = R.drawable.card_spare,
            buttonVisibility = true,
            buttonTitle = getString(context,Strings.screen_yfy_freeze_unfreeze_card_button_label)
        )
        hashMap[YFYAchievementType.SPEND_HUNDRED_AED.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_spend_aed_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.EXPLORE_CARD_CONTROLS.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_explore_card_control_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        return hashMap
    }

    private fun getBetterTogetherContent(): HashMap<String, YapForYouDataModel> {
        val hashMap: HashMap<String, YapForYouDataModel> = HashMap()
        hashMap[YFYAchievementType.INVITE_A_FRIEND.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_invite_a_friend_description),
            image = R.drawable.card_spare,
            buttonVisibility = true,
            buttonTitle = getString(context,Strings.screen_yfy_invite_a_friend_button_label)
        )
        hashMap[YFYAchievementType.MAKE_Y2Y_TRANSFER.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_send_money_to_someone_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.SPLIT_BILLS.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_split_bills_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.SEND_MONEY.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_send_money_outside_yap_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        return hashMap
    }

    private fun getTakeLeapContent(): HashMap<String, YapForYouDataModel> {
        val hashMap: HashMap<String, YapForYouDataModel> = HashMap()
        hashMap[YFYAchievementType.ORDER_VIRTUAL_CARD.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_order_virtual_card_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.UPGRADE_TO_PRIME.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_upgrade_to_prime_description),
            image = R.drawable.card_spare,
            buttonVisibility = true,
            buttonTitle = getString(context,Strings.screen_yfy_upgrade_to_prime_button_label)
        )
        hashMap[YFYAchievementType.GO_METAL.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_go_metal_description),
            image = R.drawable.card_spare,
            buttonVisibility = true,
            buttonTitle = getString(context,Strings.screen_yfy_upgrade_to_prime_button_label)
        )
        hashMap[YFYAchievementType.SET_UP_MULTI_CURRENCY.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_set_multi_currency_account_description),
            image = R.drawable.card_spare,
            buttonVisibility = true,
            buttonTitle = getString(context,Strings.screen_yfy_set_multi_currency_account_button_label)
        )
        return hashMap
    }

    private fun getYapStoreContent(): HashMap<String, YapForYouDataModel> {
        val hashMap: HashMap<String, YapForYouDataModel> = HashMap()
        hashMap[YFYAchievementType.GET_YOUNG.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_get_yap_young_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = getString(context,Strings.screen_yfy_get_yap_young_button_label)
        )
        hashMap[YFYAchievementType.SIGN_UP_TO_HOUSEHOLD.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_signup_to_hh_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = getString(context,Strings.screen_yfy_signup_to_hh_button_label)
        )
        hashMap[YFYAchievementType.MISSION_ON_YOUNG.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_set_a_mission_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.PAY_YOU_HELP.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_pay_your_help_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        return hashMap
    }

    private fun getYouAreProContent(): HashMap<String, YapForYouDataModel> {
        val hashMap: HashMap<String, YapForYouDataModel> = HashMap()
        hashMap[YFYAchievementType.INVITE_TEN_FRIENDS.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_invite_ten_friends_description),
            image = R.drawable.card_spare,
            buttonVisibility = true,
            buttonTitle = getString(context,Strings.screen_yfy_invite_ten_friends_button_label)
        )
        hashMap[YFYAchievementType.SPEND_THOUSAND_AED.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_spend_thousand_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.COMPLETE_RENEWAL.type] = YapForYouDataModel(
            title = "",
            description = getString(context,Strings.screen_yfy_complete_renewal_description),
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        return hashMap
    }

    fun getDescriptionData(tag: String?): YapForYouDataModel? {
        return yfyContentMap?.get(tag)
    }
}
