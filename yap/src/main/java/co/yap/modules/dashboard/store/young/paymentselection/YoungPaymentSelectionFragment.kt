package co.yap.modules.dashboard.store.young.paymentselection

import android.os.Bundle
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungPaymentSelectionBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class YoungPaymentSelectionFragment :
    BaseNavViewModelFragment<FragmentYoungPaymentSelectionBinding, IYoungPaymentSelection.State, YoungPaymentSelectionVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_payment_selection

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    override fun toolBarVisibility() = false
    private fun onClick(id: Int) {
        when (id) {
            R.id.btnGetHouseHoldAccount -> {
            }
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
