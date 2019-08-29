package co.yap.modules.store.viewmodels

import android.app.Application
import co.yap.modules.store.interfaces.IYapStore
import co.yap.modules.store.models.YapStoreData
import co.yap.modules.store.states.YapStoreState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapStoreViewModel(application: Application) : BaseViewModel<IYapStore.State>(application),
    IYapStore.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapStoreState = YapStoreState()

    override var yapStoreData: MutableList<YapStoreData> = mutableListOf(
        YapStoreData(
            1,
            "YAP Young",
            "Open a bank account for your children and help empower them financially.",
            "http://yap.co/wp-content/uploads/2019/03/YAP-office-01.jpg"
        )
        ,
        YapStoreData(
            2,
            "YAP Household",
            "Manage your household salaries digitally.",
            "http://yap.co/wp-content/uploads/2019/03/YAP-office-01.jpg"
        ),
        YapStoreData(
            3,
            "YAP B2B",
            "Manage your household salaries digitally.",
            "http://yap.co/wp-content/uploads/2019/03/YAP-office-01.jpg"
        ),
        YapStoreData(
            4,
            "YAP B2C",
            "Manage your household salaries digitally.",
            "http://yap.co/wp-content/uploads/2019/03/YAP-office-01.jpg"
        ),
        YapStoreData(
            5,
            "Financial Freedom for All",
            "Yap’s mission is to enable everyone with financial freedom. Financial fr…",
            "http://yap.co/wp-content/uploads/2019/03/YAP-office-01.jpg"
        )
    )

}