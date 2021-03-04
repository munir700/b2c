package co.yap.modules.kyc.viewmodels

import android.view.View
import co.yap.modules.kyc.models.EmpInfoStatusModel
import co.yap.yapcore.interfaces.OnItemClickListener

class EmpInfoSelectionItemViewModel(
    val empInfoStatusModel: EmpInfoStatusModel,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, empInfoStatusModel, position)
    }
}
