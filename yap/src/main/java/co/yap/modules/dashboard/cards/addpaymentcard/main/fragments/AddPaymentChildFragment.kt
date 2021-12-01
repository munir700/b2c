package co.yap.modules.dashboard.cards.addpaymentcard.main.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels.AddPaymentCardViewModel
import co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels.AddPaymentChildViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase


abstract class AddPaymentChildFragment<V : IBase.ViewModel<*>> : BaseBindingFragment<V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is AddPaymentChildViewModel<*>) {
            (viewModel as AddPaymentChildViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(AddPaymentCardViewModel::class.java)
        }
    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }

    fun navigateToAction(action: NavDirections) {
        findNavController().navigate(action)
    }
}