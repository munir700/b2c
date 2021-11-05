package co.yap.modules.kyc.amendments.missinginfo.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemMisssingInfoBinding
import co.yap.yapcore.BaseBindingRecyclerAdapter

class MissingInfoAdapter(
    val listItems: MutableList<String>
) :
    BaseBindingRecyclerAdapter<String, MissingInfoAdapter.ViewHolder>(listItems) {

    override fun onCreateViewHolder(binding: ViewDataBinding): ViewHolder {
        return ViewHolder(binding as ItemMisssingInfoBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_misssing_info

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listItems[position])
    }

    inner class ViewHolder(val binding: ItemMisssingInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: String) {
            binding.adapterIndex = adapterPosition + 1
            binding.missingInfoItem = item
        }
    }
}