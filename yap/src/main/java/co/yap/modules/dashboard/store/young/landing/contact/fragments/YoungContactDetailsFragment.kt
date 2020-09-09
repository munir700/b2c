package co.yap.modules.dashboard.store.young.landing.contact.fragments

import co.yap.R
import co.yap.databinding.FragmentYoungContactDetailsBinding
import co.yap.modules.dashboard.store.young.landing.contact.IYoungContactDetails
import co.yap.modules.dashboard.store.young.landing.contact.YoungContactDetailsVM
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class YoungContactDetailsFragment:
    BaseNavViewModelFragment<FragmentYoungContactDetailsBinding, IYoungContactDetails.State, YoungContactDetailsVM>() {

    override fun getBindingVariable(): Int {
        TODO("Not yet implemented")
    }
    override fun getLayoutId() = R.layout.fragment_young_contact_details
}
