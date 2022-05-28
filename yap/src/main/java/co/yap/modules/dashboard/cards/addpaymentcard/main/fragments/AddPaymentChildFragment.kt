package co.yap.modules.dashboard.cards.addpaymentcard.main.fragments

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels.AddPaymentCardViewModel
import co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels.AddPaymentChildViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase


abstract class AddPaymentChildFragment<VB : ViewDataBinding, V : IBase.ViewModel<*>> :
    BaseBindingFragment<VB, V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is AddPaymentChildViewModel<*>) {
            (viewModel as AddPaymentChildViewModel<*>).parentViewModel =
                ViewModelProvider(requireActivity()).get(AddPaymentCardViewModel::class.java)
        }
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }

    fun handleNavigation(
        destinationId: NavDirections,
        @IdRes popupTo: Int
    ) {
        navigateToBack(destinationId, popupTo)
    }
}