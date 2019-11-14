package co.yap.modules.yapit.sendmoney.adapters

import android.content.Context
import androidx.databinding.ViewDataBinding
import co.yap.BR
import co.yap.countryutils.country.Country
import co.yap.databinding.ItemCountryBinding
import co.yap.modules.yapit.sendmoney.adapters.CountryAdapter.ViewHolder
import co.yap.yapcore.BaseBindingArrayAdapter
import co.yap.yapcore.BaseBindingHolder


public class CountryAdapter(context: Context, resource: Int, objects: List<Country>) :
    BaseBindingArrayAdapter<Country, ViewHolder>(context, resource, objects) {

    override fun createViewHolder(binding: ViewDataBinding): ViewHolder {
        return ViewHolder(binding)
    }


    inner class ViewHolder(binding: ViewDataBinding) : BaseBindingHolder(binding) {
        override fun getBindingVariable(): Int = BR.dataCountry

        //        private fun onBind(binding: ViewDataBinding) {
        private fun onBind(binding: Object) {
//        private fun onBind(binding: ViewDataBinding) {
//            binding.dataList = binding.dataList
//            binding.executePendingBindings()
        }
    }

//    inner class ViewHolder(binding: ItemCountryBinding) : BaseBindingHolder(binding) {
//
//        private fun onBind(binding: ItemCountryBinding) {
//            binding.dataList = binding.dataList
//            binding.executePendingBindings()
//
//
//        }
//    }

}