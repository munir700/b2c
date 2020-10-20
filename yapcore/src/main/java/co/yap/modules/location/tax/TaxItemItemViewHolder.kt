package co.yap.modules.location.tax

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.yap.countryutils.country.Country
import co.yap.translation.Strings
import co.yap.translation.Translator
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
        itemTaxInfoBinding.spinner.showDropDownArrow(position != 0)

//        taxModel.countries.partition { it.isoCountryCode2Digit == "AE" }.let {
        itemTaxInfoBinding.spinner.setAdapter(if (position == 0) taxModel.countries else taxModel.countries.filterNot { it.isoCountryCode2Digit == "AE" })
        itemTaxInfoBinding.executePendingBindings()
        itemTaxInfoBinding.spinner.setSelectedItem(if (position == 0) taxModel.countries.indexOfFirst { it.isoCountryCode2Digit == "AE" } else 0)
//        }

        //Disable TIN for UAE
        itemTaxInfoBinding.optionsSpinner.setSelection(if (position == 0) taxModel.options.indexOfFirst { it == "No" } else 0)
        itemTaxInfoBinding.optionsSpinner.background =
            if (position == 0) itemTaxInfoBinding.reasonsSpinner.context.getDrawable(R.drawable.bg_spinner_empty) else itemTaxInfoBinding.reasonsSpinner.context.getDrawable(
                R.drawable.bg_spinner
            )
        itemTaxInfoBinding.optionsSpinner.isEnabled = (position != 0)

        //Disable TIN for UAE
        itemTaxInfoBinding.tvReason.text =
            Translator.getString(
                itemTaxInfoBinding.reasonsSpinner.context,
                if (position == 0) Strings.screen_tax_info_display_text_reason_no_tin_number_selected else Strings.screen_tax_info_display_text_reason_no_tin_number
            )
        itemTaxInfoBinding.reasonsSpinner.setSelection(if (position == 0) 0 else 0)
        itemTaxInfoBinding.reasonsSpinner.background =
            if (position == 0) itemTaxInfoBinding.reasonsSpinner.context.getDrawable(R.drawable.bg_spinner_empty) else itemTaxInfoBinding.reasonsSpinner.context.getDrawable(
                R.drawable.bg_spinner
            )
        itemTaxInfoBinding.reasonsSpinner.isEnabled = (position != 0)
    }

}
