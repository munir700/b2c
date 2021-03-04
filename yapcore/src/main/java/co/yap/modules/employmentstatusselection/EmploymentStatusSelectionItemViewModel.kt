package co.yap.modules.employmentstatusselection

import android.view.View
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
