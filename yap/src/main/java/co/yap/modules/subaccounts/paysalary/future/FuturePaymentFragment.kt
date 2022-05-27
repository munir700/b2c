package co.yap.modules.subaccounts.paysalary.future

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentFuturePaymentBinding
import co.yap.modules.others.helper.Constants
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.translation.Strings

import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FuturePaymentFragment :
    BaseNavViewModelFragmentV2<FragmentFuturePaymentBinding, IFuturePayment.State, FuturePaymentVM>() {
    override fun getBindingVariable() = BR.futurePaymentVM
    override fun getLayoutId() = R.layout.fragment_future_payment
    
    override val viewModel: FuturePaymentVM by viewModels (  )

    override fun getToolBarTitle() =
        getString(Strings.screen_household_future_payment_screen_tool_bar_text)

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
    }

    override fun onClick(id: Int) {
        when (id) {
            viewModel.GO_TO_CONFIRMATION -> {
                arguments?.putParcelable(
                    SchedulePayment::class.java.simpleName, SchedulePayment(
                        amount = viewModel.state.amount.value,
                        nextProcessingDate = viewModel.state.date.value
                    )
                )
                navigateForwardWithAnimation(
                    FuturePaymentFragmentDirections.actionFuturePaymentFragmentToPaymentConfirmationFragment(),
                    arguments
                )
            }
            Constants.EVENT_GO_BACK -> navigateBackWithResult(
                resultCode = Constants.EVENT_GO_BACK,
                data = arguments
            )
        }
    }
}
