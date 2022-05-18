package co.yap.modules.dashboard.store.young.confirmation

import android.os.Bundle
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungPaymentConfirmationBinding
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YoungPaymentConfirmationFragment :
    BaseNavViewModelFragmentV2<FragmentYoungPaymentConfirmationBinding, IYoungPaymentConfirmation.State, YoungPaymentConfirmationVM>() {

    override fun getBindingVariable() = BR.viewModel
    override val viewModel: YoungPaymentConfirmationVM by viewModels()

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
