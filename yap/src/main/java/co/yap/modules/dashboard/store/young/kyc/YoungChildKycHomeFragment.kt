package co.yap.modules.dashboard.store.young.kyc


import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungChildKycHomeBinding
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.ACCOUNT_UUID
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.launchActivityForResult
import co.yap.yapcore.managers.SessionManager

class YoungChildKycHomeFragment :
    BaseNavViewModelFragment<FragmentYoungChildKycHomeBinding, IYoungChildKycHome.State, YoungChildKycHomeVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_child_kyc_home
    override fun toolBarVisibility() = true
    override fun getToolBarTitle() =
        getString(Strings.screen_young_kyc_toolbar_title_text)

    override fun onClick(id: Int) {
        when (id) {
            R.id.cvCard -> {
                launchActivityForResult<DocumentsDashboardActivity>(
                    init = {
                        putExtra(
                            Constants.name,
                            SessionManager.user?.currentCustomer?.firstName.toString()
                        )
                        putExtra(Constants.data, true)
                        putExtra(ACCOUNT_UUID, "")
                    }, completionHandler = { resultCode, data ->
                        data?.let {
                            val status = it.getStringExtra("status")
                            if (it.getBooleanExtra(Constants.result, false)) {
                                navigate(YoungChildKycHomeFragmentDirections.actionYoungChildKycHomeFragmentToYoungPaymentSelectionFragment())
                                //Handler().post { launchAddressSelection(true) }
                                return@let
                            } else if (it.getBooleanExtra(Constants.skipped, false)) {
                                navigate(YoungChildKycHomeFragmentDirections.actionYoungChildKycHomeFragmentToYoungPaymentSelectionFragment())
                                return@let
                            }
                        }
                    })
            }
        }
    }
}
