package co.yap.modules.dashboard.store.household.onboarding.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldUserContact
import co.yap.modules.dashboard.store.household.onboarding.viewmodels.HouseHoldUserContactViewModel
import kotlinx.android.synthetic.main.fragment_house_hold_user_contact_info.*
import kotlinx.android.synthetic.main.fragment_mobile.etMobileNumber

class HouseHoldUserContactFragment : BaseOnBoardingFragment<IHouseHoldUserContact.ViewModel>(),
    IHouseHoldUserContact.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_user_contact_info

    override val viewModel: IHouseHoldUserContact.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldUserContactViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnNext -> {
                    viewModel.verifyMobileNumber()
                }
            }
        })
        viewModel.verifyMobileSuccess.observe(this, Observer {
            if (it) findNavController().navigate(R.id.action_houseHoldUserContactFragment_to_HHConfirmPaymentFragment)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.countryCode = ccpSelector.getselectedCountryCodeAsInt().toString()
        viewModel.getConfirmCcp(etConfirmMobileNumber)
        viewModel.getCcp(etMobileNumber)

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.verifyMobileSuccess.removeObservers(this)
        viewModel.clickEvent.removeObservers(this)
    }
}