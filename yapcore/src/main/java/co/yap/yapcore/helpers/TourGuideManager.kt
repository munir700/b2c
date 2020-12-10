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
                TourGuideType.YAP_HOME_FRAGMENT.name,
                false
            ) == true
        ) {
            blockedTourGuideList.add(TourGuideType.YAP_HOME_FRAGMENT)
        }
        if (sharedPreferenceManager?.getValueBoolien(
                TourGuideType.YAP_HOME_FRAGMENT_GRAPH.name,
                false
            ) == true
        ) {
            blockedTourGuideList.add(TourGuideType.YAP_HOME_FRAGMENT_GRAPH)
        }
        if (sharedPreferenceManager?.getValueBoolien(
                TourGuideType.YAP_STORE_FRAGMENT.name,
                false
            ) == true
        ) {
            blockedTourGuideList.add(TourGuideType.YAP_STORE_FRAGMENT)
        }
        if (sharedPreferenceManager?.getValueBoolien(
                TourGuideType.YAP_CARDS_FRAGMENT.name,
                false
            ) == true
        ) {
            blockedTourGuideList.add(TourGuideType.YAP_CARDS_FRAGMENT)
        }
        if (sharedPreferenceManager?.getValueBoolien(
                TourGuideType.YAP_MORE_FRAGMENT.name,
                false
            ) == true
        ) {
            blockedTourGuideList.add(TourGuideType.YAP_MORE_FRAGMENT)
        }

        return blockedTourGuideList
    }

    fun blockTourGuideScreen(screenName: TourGuideType) {
        sharedPreferenceManager?.save(screenName.name, true)
    }
}
