package co.yap.modules.dashboard.addionalinfo.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAdditionalInfoStartBinding
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoStart
import co.yap.modules.dashboard.addionalinfo.viewmodels.AdditionalInfoStartViewModel
import co.yap.networking.customers.models.additionalinfo.AdditionalQuestion
import co.yap.yapcore.enums.AdditionalInfoScreenType

class AdditionalInfoStartFragment : AdditionalInfoBaseFragment<IAdditionalInfoStart.ViewModel>(),
    IAdditionalInfoStart.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_additional_info_start

    override val viewModel: AdditionalInfoStartViewModel
        get() = ViewModelProviders.of(this).get(AdditionalInfoStartViewModel::class.java)

    private fun getBindings(): FragmentAdditionalInfoStartBinding =
        viewDataBinding as FragmentAdditionalInfoStartBinding

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
        val document =
            viewModel.getDocumentList()
                .filter { additionalDocument -> additionalDocument.status == "PENDING" }
        val questions =
            viewModel.getQuestionList()
                .filter { additionalQuestion -> additionalQuestion.status == "PENDING" }
        when (viewModel.getScreenType()) {
            AdditionalInfoScreenType.BOTH_SCREENS.name -> {
                if (document.isNotEmpty()) {
                    navigate(R.id.action_additionalInfoStartFragment_to_selectDocumentFragment)
                } else {
                    viewModel.moveStep()
                    navigateToQuestion(questions)
                }
            }
            AdditionalInfoScreenType.DOCUMENT_SCREEN.name -> {
                navigate(R.id.action_additionalInfoStartFragment_to_selectDocumentFragment)
            }
            AdditionalInfoScreenType.QUESTION_SCREEN.name -> {
                navigateToQuestion(questions)
            }
            AdditionalInfoScreenType.SUCCESS_SCREEN.name -> {
                navigate(R.id.action_additionalInfoStartFragment_to_additionalInfoComplete)
            }
        }
    }

    private fun navigateToQuestion(questions: List<AdditionalQuestion>) {
        if (questions.isNotEmpty()) {
            navigate(R.id.action_additionalInfoStartFragment_to_additionalInfoQuestion)
        } else {
            navigate(R.id.action_additionalInfoStartFragment_to_additionalInfoComplete)
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}