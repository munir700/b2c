package co.yap.modules.yapit.sendmoney.adapters

import android.content.Context
import androidx.databinding.ViewDataBinding
import co.yap.BR
import co.yap.countryutils.country.Country
import co.yap.modules.yapit.sendmoney.adapters.CountryAdapter.ViewHolder
import co.yap.yapcore.BaseBindingArrayAdapter
import co.yap.yapcore.BaseBindingHolder

class CountryAdapter(context: Context, resource: Int, objects: List<Country>) :
    BaseBindingArrayAdapter<Country, ViewHolder>(context, resource, objects) {

    override fun createViewHolder(binding: ViewDataBinding): ViewHolder {
        return ViewHolder(binding)
    }


    inner class ViewHolder(binding: ViewDataBinding) : BaseBindingHolder(binding) {
        override fun getBindingVariable(): Int = BR.dataCountry
        private fun onBind(binding: Object) {

        }
    }
}