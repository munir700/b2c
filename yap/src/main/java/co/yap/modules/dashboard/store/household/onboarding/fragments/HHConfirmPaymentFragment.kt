package co.yap.modules.dashboard.store.household.onboarding.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.selectedPlanFee.set("AED 720.00")
        viewModel.state.selectedCardPlan.set("Yearly | AED 720")
        viewModel.state.selectedPlanSaving.set("Your saving 25%!")
    }

}