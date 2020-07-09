package co.yap.household.onboarding.onboardmobile

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import javax.inject.Inject

class HHOnBoardingMobileVM @Inject constructor(
    override val state: IHHOnBoardingMobile.State,
    override var validator: Validator?
) : DaggerBaseViewModel<IHHOnBoardingMobile.State>(), IHHOnBoardingMobile.ViewModel, IValidator {
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
