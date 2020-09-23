package co.yap.modules.dashboard.store.young.contact

import android.os.Bundle
import androidx.lifecycle.Observer
import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentYoungContactDetailsBinding
import co.yap.modules.dashboard.store.young.contact.IYoungContactDetails
import co.yap.modules.dashboard.store.young.contact.YoungContactDetailsVM
import co.yap.modules.dashboard.store.young.landing.YoungLandingFragmentDirections
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class YoungContactDetailsFragment:
    BaseNavViewModelFragment<FragmentYoungContactDetailsBinding, IYoungContactDetails.State, YoungContactDetailsVM>() {

    override fun getBindingVariable()= BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_contact_details
    override fun onClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                navigate(YoungContactDetailsFragmentDirections.actionYoungContactDetailsFragmentToYoungChildKycHomeFragment())
            }
        }    }

}
