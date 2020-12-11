package co.yap.modules.dashboard.more.yapforyou

import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.models.YapForYouDataModel
import co.yap.yapcore.enums.YFYAchievementType

class YapForYouManager {
    private var yfyContentMap: HashMap<String, YapForYouDataModel>? = null

    fun initiateHasMap(tag: String) {
        when (tag) {
            YFYAchievementType.GET_STARTED.type -> {
                yfyContentMap = getStartedHashMap()

            }
            YFYAchievementType.UP_RUNNING.type -> {
                yfyContentMap = getUpAndRunningHashMap()

            }
            YFYAchievementType.BETTER_TOGETHER.type -> {
                yfyContentMap = getBetterTogetherHashMap()

            }
            YFYAchievementType.TAKE_THE_LEAP.type -> {
                yfyContentMap = getTakeLeapHashMap()

            }
            YFYAchievementType.YAP_STORE.type -> {
                yfyContentMap = getYapStoreHashMap()

            }
            YFYAchievementType.YOU_ARE_PRO.type -> {
                yfyContentMap = getYouAreProHashMap()

            }
        }

    }

    private fun getStartedHashMap(): HashMap<String, YapForYouDataModel> {
        val hashMap: HashMap<String, YapForYouDataModel> = HashMap()
        hashMap[YFYAchievementType.OPEN_YAP_ACCOUNT.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.TOP_UP.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.SET_PROFILE_PICTURE.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.ADD_CARD.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        return hashMap
    }

    private fun getUpAndRunningHashMap(): HashMap<String, YapForYouDataModel> {
        val hashMap: HashMap<String, YapForYouDataModel> = HashMap()
        hashMap[YFYAchievementType.USE_YAP_LOCALLY.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.FREEZE_AND_UNFREEZE.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.SPEND_HUNDRED_AED.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.EXPLORE_CARD_CONTROLS.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        return hashMap
    }

    private fun getBetterTogetherHashMap(): HashMap<String, YapForYouDataModel> {
        val hashMap: HashMap<String, YapForYouDataModel> = HashMap()
        hashMap[YFYAchievementType.INVITE_A_FRIEND.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.MAKE_Y2Y_TRANSFER.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.SPLIT_BILLS.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.SEND_MONEY.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        return hashMap
    }

    private fun getTakeLeapHashMap(): HashMap<String, YapForYouDataModel> {
        val hashMap: HashMap<String, YapForYouDataModel> = HashMap()
        hashMap[YFYAchievementType.ORDER_VIRTUAL_CARD.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.UPGRADE_TO_PRIME.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.GO_METAL.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.SET_UP_MULTI_CURRENCY.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        return hashMap
    }

    private fun getYapStoreHashMap(): HashMap<String, YapForYouDataModel> {
        val hashMap: HashMap<String, YapForYouDataModel> = HashMap()
        hashMap[YFYAchievementType.GET_YOUNG.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.SIGN_UP_TO_HOUSEHOLD.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.MISSION_ON_YOUNG.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.PAY_YOU_HELP.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        return hashMap
    }

    private fun getYouAreProHashMap(): HashMap<String, YapForYouDataModel> {
        val hashMap: HashMap<String, YapForYouDataModel> = HashMap()
        hashMap[YFYAchievementType.INVITE_TEN_FRIENDS.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.SPEND_THOUSAND_AED.type] = YapForYouDataModel(
            title = "",
            description = "",
            image = R.drawable.card_spare,
            buttonVisibility = false,
            buttonTitle = ""
        )
        hashMap[YFYAchievementType.COMPLETE_RENEWAL.type] = YapForYouDataModel(
            title = "",
            description = "",
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
