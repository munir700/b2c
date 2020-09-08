package co.yap.modules.dashboard.store.young.landing.benefits

import android.os.Bundle
import android.view.View
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.modules.dashboard.store.young.landing.IYoungLanding
import co.yap.modules.dashboard.store.young.landing.benefits.adapter.YoungBenefitsAdapter
import co.yap.modules.dashboard.store.young.landing.benefits.adapter.YoungBenefitsModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
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
