package co.yap.household.onboarding.onboarding.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldNumberRegistration
import co.yap.household.onboarding.onboarding.viewmodels.HouseHoldNumberRegistrationViewModel
import co.yap.yapcore.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_house_hold_number_registration.*


class HouseHoldNumberRegistrationFragment :
    BaseBindingFragment<IHouseHoldNumberRegistration.ViewModel>(),
    IHouseHoldNumberRegistration.View {
    override fun getBindingVariable(): Int = BR.houseHoldViewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_number_registration

    override val viewModel: IHouseHoldNumberRegistration.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldNumberRegistrationViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialer.setInPutEditText(etPhoneNumber)
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent?.observe(this, Observer {
            when (it) {
                R.id.btnConfirm -> {
                    findNavController().navigate(R.id.to_houseHoldCreatePassCodeFragment)
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.clickEvent?.removeObservers(this)
    }
}