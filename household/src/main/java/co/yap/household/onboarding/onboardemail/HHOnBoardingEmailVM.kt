package co.yap.household.onboarding.onboardemail

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import javax.inject.Inject

class HHOnBoardingEmailVM @Inject constructor(
    override val state: IHHOnBoardingEmail.State,
    override var validator: Validator?
) : DaggerBaseViewModel<IHHOnBoardingEmail.State>(), IHHOnBoardingEmail.ViewModel, IValidator {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
    }

    override val clickEvent = SingleClickEvent()

    override fun handlePressOnClick(id: Int) {
        clickEvent.setValue(id)
    }
}
