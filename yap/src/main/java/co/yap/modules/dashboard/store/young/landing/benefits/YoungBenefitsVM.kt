package co.yap.modules.dashboard.store.young.landing.benefits

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.modules.dashboard.store.young.landing.IYoungLanding
import co.yap.modules.dashboard.store.young.landing.benefits.adapter.YoungBenefitsAdapter
import co.yap.modules.dashboard.store.young.landing.benefits.adapter.YoungBenefitsModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class YoungBenefitsVM @Inject constructor(override val state: IYoungBenefits.State) :
    DaggerBaseViewModel<IYoungBenefits.State>(), IYoungBenefits.ViewModel {
    override val clickEvent = SingleClickEvent()


    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    fun getBenefitList(): ArrayList<YoungBenefitsModel> {
        var benefits: ArrayList<YoungBenefitsModel> = ArrayList()
        benefits.add(YoungBenefitsModel("Send pocket money to their account"))
        benefits.add(YoungBenefitsModel("Monitor their spending habits"))
        benefits.add(YoungBenefitsModel("Set mission to your child to earn money"))
        benefits.add(YoungBenefitsModel("Create saving goals"))
        return benefits

    }

    override val benefitsAdapter: YoungBenefitsAdapter = YoungBenefitsAdapter(getBenefitList())

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

}