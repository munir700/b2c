package co.yap.modules.location.kyc_additional_info.employment_info .questionnaire

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.countryutils.country.Country
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.modules.location.fragments.LocationChildFragment
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentEmploymentQuestionnaireBinding
import co.yap.yapcore.enums.EmploymentQuestionIdentifier
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.helpers.extentions.launchMultiSelectionBottomSheet
import co.yap.yapcore.helpers.infoDialog
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager

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
                R.id.etAmount -> onInfoClick(data as QuestionUiFields)
                R.id.etAmount -> onInfoClick(data as QuestionUiFields)
                R.id.searchCountries -> {
                    requireActivity().launchMultiSelectionBottomSheet(
                        object : OnItemClickListener {
                            override fun onItemClick(view: View, data: Any, position: Int) {
                                if (data is ArrayList<*>) {
                                    setBusinessCountries(data as ArrayList<String>, pos)
                                }
                            }
                        },
                        countriesList = getSelectedStateCountries(SessionManager.getCountries())
                    )
                }
            }
        }
    }

    override fun setBusinessCountries(
        countries: ArrayList<String>,
        position: Int
    ) {
        val objQuestion = viewModel.questionnaireAdaptor.getDataForPosition(position)
        objQuestion.question.countriesAnswer.get()?.clear()
        objQuestion.question.countriesAnswer.get()?.addAll(countries)
        viewModel.questionnaireAdaptor.setItemAt(position, objQuestion)
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

    override fun onInfoClick(questionUiFields: QuestionUiFields) {
        if (questionUiFields.key != null) {
            var title = ""
            var message = ""
            when (questionUiFields.key) {
                EmploymentQuestionIdentifier.SALARY_AMOUNT -> {
                    title =
                        getString(Strings.screen_employment_information_dialog_display_text_heading)

                    message =
                        getString(Strings.screen_employment_information_dialog_display_text_subheading)
                }

                EmploymentQuestionIdentifier.DEPOSIT_AMOUNT -> {
                    title =
                        getString(Strings.screen_employment_information_cash_dialog_display_text_heading)
                    message =
                        getString(Strings.screen_employment_information_cash_dialog_display_text_subheading)
                }
            }
            showInfoDialog(title, message)
        }
    }

    override fun showInfoDialog(title: String, message: String) {
        requireContext().infoDialog(
            title = title,
            message = message,
            buttonText = getString(Strings.screen_employment_information_dialog_button_text_close)
        )
    }

    private fun getSelectedStateCountries(countries: ArrayList<Country>): List<Country> {
        if (countries.isNullOrEmpty()) return emptyList()
        countries.forEach {
            it.subTitle = it.getName()
            it.sheetImage = CurrencyUtils.getFlagDrawable(
                requireContext(),
                it.isoCountryCode2Digit.toString()
            )
        }

        return countries
    }
}
