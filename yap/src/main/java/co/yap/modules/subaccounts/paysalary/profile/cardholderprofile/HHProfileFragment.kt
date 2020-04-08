package co.yap.modules.subaccounts.paysalary.profile.cardholderprofile

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhProfileBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HHProfileFragment :
    BaseNavViewModelFragment<FragmentHhProfileBinding, IHHProfile.State, HHProfileVM>() {

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_hh_profile
}