package co.yap.modules.dashboard.store.household.onboarding.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldConfirmPayment
import co.yap.modules.dashboard.store.household.onboarding.viewmodels.HouseHoldConfirmPaymentViewModel
import co.yap.yapcore.BR

class HHConfirmPaymentFragment : BaseOnBoardingFragment<IHouseHoldConfirmPayment.ViewModel>(),
    IHouseHoldConfirmPayment.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_cofirm_payment
    override val viewModel: IHouseHoldConfirmPayment.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldConfirmPaymentViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickObserver)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.tvChangePlan -> {

            }
            R.id.confirmButton -> {
                findNavController().navigate(R.id.action_HHConfirmPaymentFragment_to_houseHoldSuccessFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }
}