package co.yap.modules.store.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.store.interfaces.IYapStore
import co.yap.modules.store.states.YapStoreState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.store.StoresRepository
import co.yap.networking.store.requestdtos.CreateStoreRequest
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapStoreViewModel(application: Application) : BaseViewModel<IYapStore.State>(application),
    IYapStore.ViewModel,
    IRepositoryHolder<StoresRepository> {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapStoreState = YapStoreState()
    override val repository: StoresRepository = StoresRepository
    override var yapStoreData: MutableLiveData<MutableList<Store>> = MutableLiveData()

    override fun onCreate() {
        super.onCreate()
        loadYapStores(2)
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    private fun loadYapStores(id: Int) {

        if (yapStoreData.value.isNullOrEmpty())
            launch {
                state.loading = true
                when (val response =
                    repository.getYapStores(CreateStoreRequest("$id"))) {
                    is RetroApiResponse.Success -> {
                        //Assume sample response
                        yapStoreData.value = getDummyList()
                        response.data
                    }
                    //Actual Line is RetroApiResponse.Error -> state.toast = response.error.message
                    is RetroApiResponse.Error -> {
                        yapStoreData.value = getDummyList()
                        response.error.message
                    }
                }
                state.loading = false
            }
    }

    //Assume sample response
    private fun getDummyList(): MutableList<Store> {
        return mutableListOf(
            Store(
                1,
                "YAP Young",
                "Open a bank account for your children and help empower them financially.",
                R.drawable.ic_store_young, R.drawable.ic_young_smile

            )
            ,
            Store(
                2,
                "YAP Household",
                "Manage your household salaries digitally.",
                R.drawable.ic_store_household, R.drawable.ic_young_household
            ),
            Store(
                3,
                "YAP B2B",
                "Manage your household salaries digitally.",
                R.drawable.ic_store_b2b, R.drawable.ic_young_smile
            ),
            Store(
                4,
                "YAP B2C",
                "Manage your household salaries digitally.",
                R.drawable.ic_store_b2c, R.drawable.ic_young_smile
            ),
            Store(
                5,
                "Financial Freedom for All",
                "Yap’s mission is to enable everyone with financial freedom. Financial fr…",
                R.drawable.ic_freedom, R.drawable.ic_young_smile
            )
        )
    }
}