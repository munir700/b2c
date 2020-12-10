package co.yap.modules.dashboard.more.yapforyou

import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.models.YapForYouDataModel
import co.yap.yapcore.enums.YFYAchievementType

class YapForYouManager {
    private var yfyContentMap: HashMap<String, YapForYouDataModel>? = null

    init {
        yfyContentMap = getYapForYouHashMap()
    }

    private fun getYapForYouHashMap(): HashMap<String, YapForYouDataModel> {
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
