package co.yap.modules.dashboard.addionalinfo.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoQuestion
import co.yap.modules.dashboard.addionalinfo.viewmodels.AdditionalInfoQuestionViewModel

class AdditionalInfoQuestionFragment :
    AdditionalInfoBaseFragment<IAdditionalInfoQuestion.ViewModel>(),
    IAdditionalInfoQuestion.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_additional_info_question

    override val viewModel: IAdditionalInfoQuestion.ViewModel
        get() = ViewModelProviders.of(this).get(AdditionalInfoQuestionViewModel::class.java)
}