package co.yap.household.onboarding.onboarding.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldNumberRegistration
import co.yap.household.onboarding.onboarding.viewmodels.HouseHoldNumberRegistrationViewModel
import co.yap.yapcore.BaseBindingFragment

class HouseHoldNumberRegistrationFragment :
    BaseBindingFragment<IHouseHoldNumberRegistration.ViewModel>(),
    IHouseHoldNumberRegistration.View {
    override fun getBindingVariable(): Int = BR.houseHoldViewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_number_registration

    override val viewModel: IHouseHoldNumberRegistration.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldNumberRegistrationViewModel::class.java)
}