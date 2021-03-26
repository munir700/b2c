package co.yap.billpayments.billcategory.adapter

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener

class BillCategoryItemViewModel(
    val billCategoryModel: BillCategoryModel?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, billCategoryModel!!, position)
    }
}
