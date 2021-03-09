package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter

import android.view.View
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.Question
import co.yap.yapcore.interfaces.OnItemClickListener

class QuestionnaireItemViewModel(
    val question: Question,
    val position: Int,
    val onItemClickListener: OnItemClickListener?
) {

    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, question, position)
    }
}