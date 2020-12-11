package co.yap.modules.dashboard.more.yapforyou

import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.models.YapForYouDataModel
import co.yap.yapcore.enums.YFYAchievementType

class YapForYouManager {
    private var yfyContentMap: HashMap<String, YapForYouDataModel>? = null

    fun initiateHasMap(tag : String){
        when (tag){
            YFYAchievementType.GET_STARTED.type ->{
                yfyContentMap = getStartedHashMap()

            }
            YFYAchievementType.UP_RUNNING.type ->{
                yfyContentMap = getUpAndRunningHashMap()

            }
            YFYAchievementType.BETTER_TOGETHER.type ->{
                yfyContentMap = getBetterTogetherHashMap()

            }
            YFYAchievementType.TAKE_THE_LEAP.type ->{
                yfyContentMap = getTakeLeapHashMap()

            }
            YFYAchievementType.YAP_STORE.type ->{
                yfyContentMap = getYapStoreHashMap()

            }
            YFYAchievementType.YOU_ARE_PRO.type ->{
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
    private fun getTakeLeapHashMap(): HashMap<String, YapForYouDataModel> {
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
    private fun getYapStoreHashMap(): HashMap<String, YapForYouDataModel> {
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
    private fun getYouAreProHashMap(): HashMap<String, YapForYouDataModel> {
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

    fun getDescriptionData(tag: String?): YapForYouDataModel? {
        return yfyContentMap?.get(tag)
    }
}
