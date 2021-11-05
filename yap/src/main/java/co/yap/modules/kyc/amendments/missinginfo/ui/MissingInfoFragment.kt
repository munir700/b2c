package co.yap.modules.kyc.amendments.missinginfo.ui

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.amendments.missinginfo.interfaces.IMissingInfo
import co.yap.yapcore.BaseBindingFragment

class MissingInfoFragment : BaseBindingFragment<IMissingInfo.ViewModel>(), IMissingInfo.View {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_missinginfo

    override val viewModel: IMissingInfo.ViewModel
        get() = ViewModelProviders.of(this).get(MissingInfoFragmentViewModel::class.java)
}