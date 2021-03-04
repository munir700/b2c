package co.yap.modules.kyc.adapters

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemEmploymentStatusSelectionBinding
import co.yap.modules.kyc.models.EmploymentStatusSelectionModel
import co.yap.modules.kyc.viewmodels.EmploymentStatusSelectionItemViewModel
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
