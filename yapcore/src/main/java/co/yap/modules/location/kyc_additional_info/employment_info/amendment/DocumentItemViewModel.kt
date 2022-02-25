package co.yap.modules.location.kyc_additional_info.employment_info.amendment

import android.view.View
import co.yap.networking.customers.responsedtos.employment_amendment.Document
import co.yap.yapcore.interfaces.OnItemClickListener

class DocumentItemViewModel(
    val document: Document,
    val position: Int,
    val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, document, position)
    }
}