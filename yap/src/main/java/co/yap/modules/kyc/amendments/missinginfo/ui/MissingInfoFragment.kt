package co.yap.modules.kyc.amendments.missinginfo.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentMissinginfoBinding
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.kyc.amendments.missinginfo.adapters.MissingInfoAdapter
import co.yap.modules.kyc.amendments.missinginfo.interfaces.IMissingInfo
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.managers.SessionManager

class MissingInfoFragment : BaseBindingFragment<IMissingInfo.ViewModel>(), IMissingInfo.View {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_missinginfo

    override val viewModel: IMissingInfo.ViewModel
        get() = ViewModelProvider(this).get(MissingInfoFragmentViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.adapter.set(MissingInfoAdapter(mutableListOf(),null))
        viewModel.onClickEvent.observe(viewLifecycleOwner, onClickView)
        SessionManager.getAccountInfo {
            getDataBindingView<FragmentMissinginfoBinding>().tvTitle.text =
                getString(Strings.screen_missing_info_title).format(
                    SessionManager.user?.currentCustomer?.firstName
                )
        }
    }

    private val onClickView = Observer<Int> {
        when (it) {
            R.id.btnGetStarted -> {
                if (viewModel.missingInfoMap.value?.get("eidInfo")?.isNotEmpty() == true) {
                    launchActivity<DocumentsDashboardActivity>(requestCode = RequestCodes.REQUEST_KYC_DOCUMENTS) {
                        putExtra(
                            Constants.name,
                            SessionManager.user?.currentCustomer?.firstName.toString()
                        )
                        putExtra(Constants.data, false)
                    }
                }
            }
            R.id.tvDoItLater -> {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onClickEvent.removeObserver(onClickView)
    }

}