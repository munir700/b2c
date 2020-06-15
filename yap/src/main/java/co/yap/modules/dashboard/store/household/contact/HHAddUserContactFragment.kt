package co.yap.modules.dashboard.store.household.contact

import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhAddUserContactBinding
import co.yap.modules.dashboard.store.household.userinfo.HHAddUserNameFragmentDirections
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HHAddUserContactFragment :
    BaseNavViewModelFragment<FragmentHhAddUserContactBinding, IHHAddUserContact.State, HHAddUserContactVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hh_add_user_contact
    override fun getToolBarTitle() = "Create account"

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                viewModel.verifyMobileNumber {
                    if (it == true) {
                        navigateForwardWithAnimation(
                            HHAddUserContactFragmentDirections.actionHHAddUserContactFragmentToHHAddUserSuccessFragment(),
                            arguments
                        )
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
