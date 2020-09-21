package co.yap.modules.dashboard.store.young.kyc

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungChildKycHomeBinding
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.managers.MyUserManager

class YoungChildKycHomeFragment :
    BaseNavViewModelFragment<FragmentYoungChildKycHomeBinding, IYoungChildKycHome.State, YoungChildKycHomeVM>() {

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_young_child_kyc_home

    override fun onClick(id: Int) {
        when (id) {
            R.id.cvCard -> {
                launchActivity<DocumentsDashboardActivity> {
                    putExtra(
                        Constants.name,
                        MyUserManager.user?.currentCustomer?.firstName.toString()
                    )
                    putExtra(Constants.data, true)
                }
            }
        }
    }
}