package co.yap.household.onboarding.cardselection

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHOnBoardingCardSelectionVM @Inject constructor(override val state: IHHOnBoardingCardSelection.State) :
    DaggerBaseViewModel<IHHOnBoardingCardSelection.State>(), IHHOnBoardingCardSelection.ViewModel {
    override val clickEvent = SingleClickEvent()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
    }

    override fun handlePressOnClick(id: Int) {
        clickEvent.setValue(id)
    }
}
