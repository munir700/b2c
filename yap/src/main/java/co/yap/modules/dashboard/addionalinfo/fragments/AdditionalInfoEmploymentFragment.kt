package co.yap.modules.dashboard.addionalinfo.fragments

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoEmployment
import co.yap.modules.dashboard.addionalinfo.viewmodels.AdditionalInfoEmploymentViewModel

class AdditionalInfoEmploymentFragment :
    AdditionalInfoBaseFragment<IAdditionalInfoEmployment.ViewModel>(),
    IAdditionalInfoEmployment.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_additional_info_employment

    override val viewModel: IAdditionalInfoEmployment.ViewModel
        get() = ViewModelProviders.of(this).get(AdditionalInfoEmploymentViewModel::class.java)

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                viewModel.moveToNext()
                findNavController().navigate(R.id.action_additionalInfoEmployment_to_additionalInfoQuestion)
            }
        }
    }
}