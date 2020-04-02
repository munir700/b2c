package co.yap.modules.subaccounts.paysalary.profile

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhsalaryProfileBinding
import co.yap.modules.subaccounts.paysalary.profile.HHSalaryProfileVM

import co.yap.yapcore.dagger.base.BaseViewModelFragment


class HHSalaryProfileFragment :
    BaseViewModelFragment<FragmentHhsalaryProfileBinding, IHHSalaryProfile.State, HHSalaryProfileVM>() {

    override fun getBindingVariable() = BR.hhSalaryProfileVM

    override fun getLayoutId() = R.layout.fragment_hhsalary_profile
}