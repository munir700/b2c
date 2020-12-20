package co.yap.modules.dashboard.addionalinfo.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoQuestion
import co.yap.modules.dashboard.addionalinfo.viewmodels.AdditionalInfoQuestionViewModel
import co.yap.networking.customers.requestdtos.UploadAdditionalInfo
import co.yap.yapcore.helpers.extentions.startFragment

class AdditionalInfoQuestionFragment :
    AdditionalInfoBaseFragment<IAdditionalInfoQuestion.ViewModel>(),
    IAdditionalInfoQuestion.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_additional_info_question

    override val viewModel: AdditionalInfoQuestionViewModel
        get() = ViewModelProviders.of(this).get(AdditionalInfoQuestionViewModel::class.java)

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                uploadAndMoveNext()
            }

            R.id.tvDoItLater -> {
                requireActivity().finish()
            }
        }
    }

    private fun uploadAndMoveNext() {
        viewModel.uploadAnswer(
            UploadAdditionalInfo(
                questionAnswer = "A digitify ",
                id = viewModel.parentViewModel?.state?.questionList?.firstOrNull()?.id.toString()
            )
        ) {
            viewModel.moveToNext()
            startFragment(
                fragmentName = AdditionalInfoCompleteFragment::class.java.name,
                clearAllPrevious = true
            )
        }
    }
}