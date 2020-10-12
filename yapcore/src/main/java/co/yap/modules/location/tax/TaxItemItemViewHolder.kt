package co.yap.modules.location.tax

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.yap.countryutils.country.Country
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ItemTaxInfoBinding
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.interfaces.OnItemClickListener

class TaxItemItemViewHolder(private val itemTaxInfoBinding: ItemTaxInfoBinding) :
    RecyclerView.ViewHolder(itemTaxInfoBinding.root) {

    fun onBind(
        taxModel: TaxModel,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        val selectedItemListener = object : OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                if (data is Country) {
                    taxModel.selectedCountry = data
                    view.id = R.id.spinner_container
                    onItemClickListener?.onItemClick(view, data, pos)
                }
            }
        }

        itemTaxInfoBinding.viewModel =
            TaxInfoItemViewModel(taxModel, position, onItemClickListener)
        itemTaxInfoBinding.spinner.setItemSelectedListener(selectedItemListener)
        itemTaxInfoBinding.etTinNumber.afterTextChanged {
            onItemClickListener?.onItemClick(itemTaxInfoBinding.etTinNumber, it, -1)
        }
        itemTaxInfoBinding.spinner.setEnabledSpinner(position != 0)
        itemTaxInfoBinding.spinner.setAdapter(taxModel.countries)
        itemTaxInfoBinding.executePendingBindings()
        itemTaxInfoBinding.spinner.setSelectedItem(if (position == 0) taxModel.countries.indexOfFirst {
            it.isoCountryCode2Digit.equals(
                "AE",
                true
            )
        } else 0)
    }

}