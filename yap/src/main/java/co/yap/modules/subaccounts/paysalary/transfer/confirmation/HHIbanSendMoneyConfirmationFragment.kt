package co.yap.modules.subaccounts.paysalary.transfer.confirmation

import android.os.Bundle
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhibanSendMoneyConfirmationBinding
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HHIbanSendMoneyConfirmationFragment :
    BaseNavViewModelFragment<FragmentHhibanSendMoneyConfirmationBinding, IHHIbanSendMoneyConfirmation.State, HHIbanSendMoneyConfirmationVM>() {
    override fun getBindingVariable() = BR.hhIbanSendMoneyConfirmationVM
    override fun getLayoutId() = R.layout.fragment_hhiban_send_money_confirmation
    override fun getToolBarTitle() =
        getString(Strings.screen_iban_send_money_confirmation_display_text_transfer_completed)

    override fun setDisplayHomeAsUpEnabled() = false
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setBackButtonDispatcher()
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.tvDoItLater -> navigateForward(
                HHIbanSendMoneyConfirmationFragmentDirections.toSubAccountDashBoardFragment(),
                arguments
            )
        }
    }
}