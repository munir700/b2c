package co.yap.modules.subaccounts.confirmation

import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentPaymentConfirmationBinding
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class PaymentConfirmationFragment :
    BaseNavViewModelFragment<FragmentPaymentConfirmationBinding, IPaymentConfirmation.State, PaymentConfirmationVM>() {
    override fun getBindingVariable() = BR.paymentConfirmationVM
    override fun getLayoutId() = R.layout.fragment_payment_confirmation
    override fun getToolBarTitle() =
        getString(Strings.screen_household_payment_confirmation_tool_bar_text)

    override fun setDisplayHomeAsUpEnabled() = false

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        setBackButtonDispatcher()
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnGoToDashboard -> navigateForwardWithAnimation(
                PaymentConfirmationFragmentDirections.actionPaymentConfirmationFragmentToSubAccountDashBoardFragment()
            )
            R.id.includeImageView -> navigateForwardWithAnimation(
                PaymentConfirmationFragmentDirections.actionPaymentConfirmationFragmentToHHProfileFragment(),
                arguments
            )
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.clickEvent.removeObservers(this)
    }
}
