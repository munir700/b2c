package co.yap.modules.kyc.viewmodels

import android.view.View
import co.yap.modules.kyc.models.EmploymentStatusSelectionModel
import co.yap.yapcore.interfaces.OnItemClickListener

class EmploymentStatusSelectionItemViewModel(
    val employmentStatusSelectionModel: EmploymentStatusSelectionModel,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, employmentStatusSelectionModel, position)
    }
}
