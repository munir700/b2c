package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import android.app.Application
import android.content.Context
import android.view.View
import co.yap.countryutils.country.Country
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.EmploymentQuestionnaireAdaptor
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.EmploymentSegment
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.modules.location.viewmodels.LocationChildViewModel
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.CoreBottomSheetData
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.EmploymentQuestionIdentifier
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class EmploymentQuestionnaireViewModel(application: Application) :
    LocationChildViewModel<IEmploymentQuestionnaire.State>(application),
    IEmploymentQuestionnaire.ViewModel {
    var oldPosition = -1
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val questionnaireAdaptor: EmploymentQuestionnaireAdaptor =
        EmploymentQuestionnaireAdaptor(
            arrayListOf()
        )
    override val state: IEmploymentQuestionnaire.State =
        EmploymentQuestionnaireState()

    override fun onResume() {
        super.onResume()
        parentViewModel?.countries
        setProgress(95)
    }

    val countriesItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is ArrayList<*>) {
                setBusinessCountries(data as ArrayList<String>, pos)
            }
        }
    }

    val segmentItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            val objQuestion = questionnaireAdaptor.getDataForPosition(pos)
            objQuestion.question.answer.set((data as CoreBottomSheetData).subTitle)
            questionnaireAdaptor.setItemAt(pos, objQuestion)
        }
    }

    override fun setBusinessCountries(
        countries: ArrayList<String>,
        position: Int
    ) {
        val objQuestion = questionnaireAdaptor.getDataForPosition(position)
        objQuestion.question.countriesAnswer.clear()
        objQuestion.question.countriesAnswer.addAll(countries)
        questionnaireAdaptor.setItemAt(position, objQuestion)
    }

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun questionnaires(forStatus: EmploymentStatus): ArrayList<QuestionUiFields> {
        val questionnairesComposer: ComplianceQuestionsItemsComposer = KYCComplianceComposer()
        return questionnairesComposer.compose(forStatus)
    }

    override fun employeeSegment(): MutableList<EmploymentSegment> {
        val gson = GsonBuilder().create()
        return gson.fromJson<MutableList<EmploymentSegment>>(
            context.getJsonDataFromAsset(
                "jsons/employment_describe_you_best.json"
            ), getEmploymentType()
        )
    }

    override fun getEmploymentType(): Type {
        val itemType = object : TypeToken<List<EmploymentSegment>>() {}.type
        return itemType
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.etQuestionEditText -> {
                }
            }
        }
    }

    override fun getSelectedStateCountries(countries: ArrayList<Country>): List<Country> {
        if (countries.isNullOrEmpty()) return emptyList()
        countries.forEach {
            it.subTitle = it.getName()
            it.sheetImage = CurrencyUtils.getFlagDrawable(
                context,
                it.isoCountryCode2Digit.toString()
            )
        }

        val position = -1
        if (oldPosition == -1) {
            oldPosition = position
            countries[oldPosition].isSelected = true
        } else {
            countries[oldPosition].isSelected = false
            oldPosition = position
            countries[oldPosition].isSelected = true
        }

        return countries
    }

    override fun parseSegment(
        context: Context,
        employmentSegments: MutableList<EmploymentSegment>
    ): MutableList<CoreBottomSheetData> {
        employmentSegments.forEach {
            it.subTitle = it.subSegmentDesc
        }
        return employmentSegments.toMutableList()
    }

    override fun onInfoClick(
        questionUiFields: QuestionUiFields,
        callBack: (title: String, message: String) -> Unit
    ) {
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
                else -> {

                }
            }
            if (title.isNotEmpty() && message.isNotEmpty()) {
                callBack(title, message)
            }
        }
    }
}
