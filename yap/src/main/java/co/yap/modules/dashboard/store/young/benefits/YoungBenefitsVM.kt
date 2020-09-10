package co.yap.modules.dashboard.store.young.benefits

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.modules.dashboard.store.young.benefits.adapter.YoungBenefitsModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import javax.inject.Inject

class
YoungBenefitsVM @Inject constructor(override var state: IYoungBenefits.State) :
    BaseRecyclerAdapterVM<YoungBenefitsModel,IYoungBenefits.State>(), IYoungBenefits.ViewModel {
    override val clickEvent = SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}
