package co.yap.modules.location.kyc_additional_info.employment_info.amendment

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.networking.customers.responsedtos.employment_amendment.Document
import co.yap.yapcore.BaseListItemViewModel
import co.yap.yapcore.R

class DocumentItemViewModel : BaseListItemViewModel<Document>() {
    private lateinit var document: Document

    override fun setItem(item: Document, position: Int) {
        document = item
        notifyChange()
    }

    override fun getItem(): Document = document

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}

    override fun layoutRes() = R.layout.item_employment_info_document

    override fun onItemClick(view: View, data: Any, pos: Int) {}
}