package co.yap.household.onboarding.onboarding.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldCreatePassCode
import co.yap.household.onboarding.onboarding.viewmodels.HouseHoldCreatePassCodeViewModel
import co.yap.yapcore.BaseBindingFragment

class HouseHoldCreatePassCodeFragment :
    BaseBindingFragment<IHouseHoldCreatePassCode.ViewModel>(), IHouseHoldCreatePassCode.View {

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_create_passcode
    override fun getBindingVariable() = BR.createPasscodeViewModel

    override val viewModel: IHouseHoldCreatePassCode.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldCreatePassCodeViewModel::class.java)

}