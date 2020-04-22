package co.yap.modules.subaccounts.householdsetpin.hhsetpinsuccess

import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentHhSetPinSuccessBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HHSetPinSuccessFragment :
    BaseNavViewModelFragment<FragmentHhSetPinSuccessBinding, IHHSetPinSuccess.State, HHSetPinSuccessVM>() {
    override fun getBindingVariable(): Int =BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_hh_set_pin_success
}