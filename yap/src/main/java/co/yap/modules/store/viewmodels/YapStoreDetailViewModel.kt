package co.yap.modules.store.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.store.interfaces.IYapStoreDetail
import co.yap.modules.store.models.YapStoreData
import co.yap.modules.store.states.YapStoreDetailState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapStoreDetailViewModel(application: Application) :
    BaseViewModel<IYapStoreDetail.State>(application),
    IYapStoreDetail.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapStoreDetailState = YapStoreDetailState()
    override var yapStoreData: MutableList<YapStoreData> = mutableListOf(
        YapStoreData(
            1,
            "YAP Young",
            "Open a bank account for your children and help empower them financially.",
            R.drawable.ic_store_young, R.drawable.ic_young_smile

        )
        ,
        YapStoreData(
            2,
            "YAP Household",
            "Manage your household salaries digitally.",
            R.drawable.ic_store_household, R.drawable.ic_young_household
        ),
        YapStoreData(
            3,
            "YAP B2B",
            "Manage your household salaries digitally.",
            R.drawable.ic_store_b2b, R.drawable.ic_young_smile
        ),
        YapStoreData(
            4,
            "YAP B2C",
            "Manage your household salaries digitally.",
            R.drawable.ic_store_b2c, R.drawable.ic_young_smile
        ),
        YapStoreData(
            5,
            "Financial Freedom for All",
            "Yap’s mission is to enable everyone with financial freedom. Financial fr…",
            R.drawable.ic_freedom, R.drawable.ic_young_smile
        )
    )

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}