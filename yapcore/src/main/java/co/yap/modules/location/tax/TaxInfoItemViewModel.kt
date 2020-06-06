package co.yap.modules.location.tax

import android.view.View
import co.yap.yapcore.R
import co.yap.yapcore.interfaces.OnItemClickListener

class TaxInfoItemViewModel(
    val taxModel: TaxModel,
    val position: Int,
    val onItemClickListener: OnItemClickListener?
) {

    val spinnerItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is String) {
                taxModel.selectedReason.set(data)
                if (data.equals("yes", true)) {

                }
            }
        }
    }

    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.ivCross -> {
                taxModel.taxRowNumber.set(false)
            }
            R.id.lyAddCountry -> {
                taxModel.canAddMore.set(false)
            }
        }
        onItemClickListener?.onItemClick(view, taxModel, position)
    }
}