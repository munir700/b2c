package co.yap.yapcore.helpers

import android.content.Context
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.TourGuideRequest
import co.yap.networking.customers.responsedtos.TourGuide
import co.yap.networking.models.RetroApiResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object TourGuideManager {
    private val customerRepository: CustomersRepository = CustomersRepository
    private var tourGuides: List<TourGuide?> = arrayListOf()
    private var tourGuide: TourGuide? = null
    private var onUpdateTourGuideSuccess: MutableLiveData<Boolean> = MutableLiveData()
    private var onGetTourGuidesSuccess: MutableLiveData<Boolean> = MutableLiveData()
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

    fun updateTourGuideStatus(viewName: String, completed: Boolean, skipped: Boolean, viewed: Boolean) {
        GlobalScope.launch {
            when (val response = customerRepository.updateTourGuideStatus(
                TourGuideRequest(viewName = viewName, completed = completed, skipped = skipped, viewed = viewed))) {
                is RetroApiResponse.Success -> {
                    tourGuide = response.data.data as TourGuide
                    onUpdateTourGuideSuccess.postValue(true)
                }

                is RetroApiResponse.Error -> {
                    onUpdateTourGuideSuccess.postValue(false)
                }
            }
        }
    }


    fun getTourGuides() {
        GlobalScope.launch {
            when (val response = customerRepository.getTourGuides()) {
                is RetroApiResponse.Success -> {
                    tourGuides = response.data.data as ArrayList<TourGuide>
                    onGetTourGuidesSuccess.postValue(true)
                }

                is RetroApiResponse.Error -> {
                    onGetTourGuidesSuccess.postValue(false)
                }
            }
        }
    }
}
