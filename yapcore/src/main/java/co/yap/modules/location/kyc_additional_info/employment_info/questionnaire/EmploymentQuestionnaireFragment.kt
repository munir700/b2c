package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.location.fragments.LocationChildFragment
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.Question
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentEmploymentQuestionnaireBinding
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.helpers.showAlertCustomDialog
import co.yap.yapcore.interfaces.OnItemClickListener

class EmploymentQuestionnaireFragment : LocationChildFragment<IEmploymentQuestionnaire.ViewModel>(),
    IEmploymentQuestionnaire.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_employment_questionnaire

    override val viewModel: EmploymentQuestionnaireViewModel
        get() = ViewModelProviders.of(this).get(EmploymentQuestionnaireViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
        val status = arguments?.get("EMPLOYMENT_STATUS") as EmploymentStatus
        viewModel.questionnaireAdaptor.setList(viewModel.questionnaires(status))
    }

    private val clickObserver = Observer<Int> {
    }
    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.listener.onItemClick(view, data, pos)
            when (view.id) {
                R.id.etAmount -> {
                    val question = data as Question
                    onInfoClick(question)
                }
            }
        }
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.questionnaireAdaptor.setItemListener(listener)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun getBinding(): FragmentEmploymentQuestionnaireBinding =
        viewDataBinding as FragmentEmploymentQuestionnaireBinding

    override fun onInfoClick(question: Question) {
        if (!question.key.isNullOrBlank()) {
            var title = ""
            var message = ""
            when (question.key) {
                "SALARY_AMOUNT" -> {
                    title = "Why do we ask you about your monthly salary?"
                    message =
                        "There is no minimum salary requirement to sign up to YAP. We only ask this question for compliance purposes to safeguard your account."
                }

                "DEPOSIT_AMOUNT" -> {
                    title =
                        "For those people who still use cash, how much cash will you add to YAP per month?"
                    message =
                        "We are asking about CASH only that would be deposited in a bank machine.  We only ask this question for compliance purposes to safeguard your account."
                }
            }
            showInfoDialog(title, message)
        }
    }

    override fun showInfoDialog(title: String, message: String) {
        requireActivity().showAlertCustomDialog(title,message)
    }
}