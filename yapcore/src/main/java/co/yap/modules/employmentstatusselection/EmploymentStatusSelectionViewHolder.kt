package co.yap.modules.employmentstatusselection

import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.databinding.ItemEmploymentStatusSelectionBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class EmploymentStatusSelectionViewHolder(private val itemEmploymentInformationSelectionBinding: ItemEmploymentStatusSelectionBinding) :
    RecyclerView.ViewHolder(itemEmploymentInformationSelectionBinding.root) {
    fun onBind(
        position: Int,
        employmentStatusSelectionModel: EmploymentStatusSelectionModel,
        onItemClickListener: OnItemClickListener?
    ) {
        itemEmploymentInformationSelectionBinding.tvEmploymentStatus.isChecked =
            employmentStatusSelectionModel.isSelected
        itemEmploymentInformationSelectionBinding.viewModel =
            EmploymentStatusSelectionItemViewModel(
                employmentStatusSelectionModel,
                position,
                onItemClickListener
            )
        itemEmploymentInformationSelectionBinding.executePendingBindings()
    }
}
