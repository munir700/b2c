package co.yap.modules.dashboard.store.young.confirmation

import android.os.Bundle
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungPaymentConfirmationBinding
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class YoungPaymentConfirmationFragment :
    BaseNavViewModelFragment<FragmentYoungPaymentConfirmationBinding, IYoungPaymentConfirmation.State, YoungPaymentConfirmationVM>() {

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_payment_confirmation
    override fun getToolBarTitle() =
        getString(Strings.screen_household_payment_confirmation_tool_bar_text)

    override fun setDisplayHomeAsUpEnabled()=false
    override fun toolBarVisibility() = true

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setBackButtonDispatcher()
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnGetStarted -> {
                navigate(YoungPaymentConfirmationFragmentDirections.actionYoungPaymentConfirmationFragmentToYoungCardEditDetailsFragment())
            }
        }
    }
}
