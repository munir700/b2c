package co.yap.modules.dashboard.addionalinfo.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAdditionalInfoStartBinding
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoStart
import co.yap.modules.dashboard.addionalinfo.viewmodels.AdditionalInfoStartViewModel
import co.yap.yapcore.enums.AdditionalInfoScreenType
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.managers.SessionManager

class AdditionalInfoStartFragment : AdditionalInfoBaseFragment<IAdditionalInfoStart.ViewModel>(),
    IAdditionalInfoStart.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_additional_info_start

    override val viewModel: AdditionalInfoStartViewModel
        get() = ViewModelProviders.of(this).get(AdditionalInfoStartViewModel::class.java)

    private fun getBindings(): FragmentAdditionalInfoStartBinding =
        viewDataBinding as FragmentAdditionalInfoStartBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.subTitle.set("We need additional information to be able to proceed with your application to get your YAP account activated")
        viewModel.state.title.set("Hey " + SessionManager.user?.currentCustomer?.firstName + ",")
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                moveNextScreen()
            }
            R.id.tvDoItLater -> {
                requireActivity().finish()
            }
        }
    }

    private fun moveNextScreen() {
        when (viewModel.getScreenType()) {
            AdditionalInfoScreenType.BOTH_SCREENS.name -> {
                val document =
                    viewModel.getDocumentList()
                        .filter { additionalDocument -> additionalDocument.isUploaded == false }
                if (document.isNotEmpty()) {
                    findNavController().navigate(R.id.action_additionalInfoStartFragment_to_selectDocumentFragment)
                } else {
                    viewModel.moveStep()
                    findNavController().navigate(R.id.action_additionalInfoStartFragment_to_additionalInfoQuestion)
                }
            }
            AdditionalInfoScreenType.DOCUMENT_SCREEN.name -> {
                findNavController().navigate(R.id.action_additionalInfoStartFragment_to_selectDocumentFragment)
            }
            AdditionalInfoScreenType.QUESTION_SCREEN.name -> {
                findNavController().navigate(R.id.action_additionalInfoStartFragment_to_additionalInfoQuestion)
            }
            AdditionalInfoScreenType.SUCCESS_SCREEN.name -> {
                startFragment(
                    fragmentName = AdditionalInfoCompleteFragment::class.java.name,
                    clearAllPrevious = true
                )
            }
        }
    }
}