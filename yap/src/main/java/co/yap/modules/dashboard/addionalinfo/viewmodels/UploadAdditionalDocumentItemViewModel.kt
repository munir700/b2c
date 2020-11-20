package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.view.View
import co.yap.modules.dashboard.addionalinfo.model.AdditionalDocument
import co.yap.yapcore.interfaces.OnItemClickListener

class UploadAdditionalDocumentItemViewModel(
    var additionalDocument: AdditionalDocument,
    var position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, additionalDocument, position)
    }
}