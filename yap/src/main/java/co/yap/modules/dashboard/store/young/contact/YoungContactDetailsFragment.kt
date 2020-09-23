package co.yap.modules.dashboard.store.young.contact

import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentYoungContactDetailsBinding
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class YoungContactDetailsFragment :
    BaseNavViewModelFragment<FragmentYoungContactDetailsBinding, IYoungContactDetails.State, YoungContactDetailsVM>() {

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_contact_details
    override fun setHomeAsUpIndicator() = co.yap.yapcore.R.drawable.ic_back_arrow_left
    override fun toolBarVisibility() = true
    override fun getToolBarTitle() =
        getString(Strings.screen_young_contact_details_toolbar_text)

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                navigate(YoungContactDetailsFragmentDirections.actionYoungContactDetailsFragmentToYoungPaymentSelectionFragment())
            }
        }
    }
}
