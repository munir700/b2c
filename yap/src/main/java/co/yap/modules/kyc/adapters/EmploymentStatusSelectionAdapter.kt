package co.yap.modules.kyc.adapters

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemEmploymentStatusSelectionBinding
import co.yap.modules.kyc.models.EmploymentStatusSelectionModel
import co.yap.yapcore.BaseBindingRecyclerAdapter

class EmploymentStatusSelectionAdapter(
    context: Context,
    private val list: MutableList<EmploymentStatusSelectionModel>
) :
    BaseBindingRecyclerAdapter<EmploymentStatusSelectionModel, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return EmploymentStatusSelectionViewHolder(binding as ItemEmploymentStatusSelectionBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int =
        R.layout.item_employment_status_selection

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is EmploymentStatusSelectionViewHolder) {
            holder.onBind(position, list.get(position), onItemClickListener)
        }
    }
}
