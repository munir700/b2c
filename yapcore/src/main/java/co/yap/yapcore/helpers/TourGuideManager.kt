package co.yap.yapcore.helpers

import android.content.Context

object TourGuideManager {
    private var sharedPreferenceManager: SharedPreferenceManager? = null
    val getBlockedTourGuideScreens: ArrayList<TourGuideType>
        get() = getBlockedTourGuideList()

    fun configure(context: Context) {
        sharedPreferenceManager = SharedPreferenceManager(context = context)
    }

    private fun getBlockedTourGuideList(): ArrayList<TourGuideType> {
        val blockedTourGuideList: ArrayList<TourGuideType> = arrayListOf()
        if (sharedPreferenceManager?.getValueBoolien(
                TourGuideType.YAP_HOME_SCREEN.name,
                false
            ) == true
        ) {
            blockedTourGuideList.add(TourGuideType.YAP_HOME_SCREEN)
        }
        if (sharedPreferenceManager?.getValueBoolien(
                TourGuideType.YAP_HOME_GRAPH.name,
                false
            ) == true
        ) {
            blockedTourGuideList.add(TourGuideType.YAP_HOME_GRAPH)
        }
        if (sharedPreferenceManager?.getValueBoolien(
                TourGuideType.YAP_STORE_SCREEN.name,
                false
            ) == true
        ) {
            blockedTourGuideList.add(TourGuideType.YAP_STORE_SCREEN)
        }
        if (sharedPreferenceManager?.getValueBoolien(
                TourGuideType.YAP_CARDS_SCREEN.name,
                false
            ) == true
        ) {
            blockedTourGuideList.add(TourGuideType.YAP_CARDS_SCREEN)
        }
        if (sharedPreferenceManager?.getValueBoolien(
                TourGuideType.YAP_MORE_SCREEN.name,
                false
            ) == true
        ) {
            blockedTourGuideList.add(TourGuideType.YAP_MORE_SCREEN)
        }

        if (sharedPreferenceManager?.getValueBoolien(
                TourGuideType.YAP_CARD_DETAIL_SCREEN.name,
                false
            ) == true
        ) {
            blockedTourGuideList.add(TourGuideType.YAP_CARD_DETAIL_SCREEN)
        }

        return blockedTourGuideList
    }

    fun lockTourGuideScreen(screenName: TourGuideType) {
        sharedPreferenceManager?.save(screenName.name, true)
    }

    fun unlockTourGuideScreens() {
        getBlockedTourGuideScreens.forEach {
            sharedPreferenceManager?.save(it.name, false)
        }
    }
}
