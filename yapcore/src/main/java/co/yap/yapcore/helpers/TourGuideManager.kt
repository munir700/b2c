package co.yap.yapcore.helpers

import android.content.Context
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.customers.responsedtos.TourGuide
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.managers.SessionManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object TourGuideManager {
    private val customerRepository: CustomersRepository = CustomersRepository
    private var tourGuides: List<TourGuide?> = arrayListOf()
    private var tourGuide: TourGuide? = null
    private var onCompleteTourGuideSuccess: MutableLiveData<Boolean> = MutableLiveData()
    private var onSkipTourGuideSuccess: MutableLiveData<Boolean> = MutableLiveData()
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

    fun completeTourGuide(viewName: String, completed: Boolean) {
        GlobalScope.launch {
            when (val response = customerRepository.completeTourGuide(viewName, completed)) {
                is RetroApiResponse.Success -> {
                    tourGuide = response.data.data as TourGuide
                    onCompleteTourGuideSuccess.postValue(true)
                }

                is RetroApiResponse.Error -> {
                    onCompleteTourGuideSuccess.postValue(false)
                }
            }
        }
    }

    fun skipTourGuide(viewName: String, skipped: Boolean) {
        GlobalScope.launch {
            when (val response = customerRepository.skipTourGuide(viewName, skipped)) {
                is RetroApiResponse.Success -> {
                    tourGuide = response.data.data as TourGuide
                    onSkipTourGuideSuccess.postValue(true)
                }

                is RetroApiResponse.Error -> {
                    onSkipTourGuideSuccess.postValue(false)
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
