package co.yap.modules.kyc.adapters

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemEmploymentInformationSelectionBinding
import co.yap.modules.kyc.models.EmpInfoStatusModel
import co.yap.yapcore.BaseBindingRecyclerAdapter

class EmpInfoSelectionAdapter(
    context: Context,
    private val list: MutableList<EmpInfoStatusModel>
) :
    BaseBindingRecyclerAdapter<EmpInfoStatusModel, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return EmpInfoViewHolder(binding as ItemEmploymentInformationSelectionBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int =
        R.layout.item_employment_information_selection

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is EmpInfoViewHolder) {
            holder.onBind(position, list.get(position), onItemClickListener)
        }
    }
}
