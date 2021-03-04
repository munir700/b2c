package co.yap.modules.kyc.adapters

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemEmploymentInformationSelectionBinding
import co.yap.modules.kyc.models.EmpInfoStatusModel
import co.yap.modules.kyc.viewmodels.EmpInfoSelectionItemViewModel
import co.yap.yapcore.interfaces.OnItemClickListener

class EmpInfoViewHolder(private val itemEmploymentInformationSelectionBinding: ItemEmploymentInformationSelectionBinding) :
    RecyclerView.ViewHolder(itemEmploymentInformationSelectionBinding.root) {
    fun onBind(
        position: Int,
        empInfoStatusModel: EmpInfoStatusModel,
        onItemClickListener: OnItemClickListener?
    ) {
        itemEmploymentInformationSelectionBinding.tvEmploymentStatus.isChecked =
            empInfoStatusModel.isSelected
        itemEmploymentInformationSelectionBinding.viewModel =
            EmpInfoSelectionItemViewModel(empInfoStatusModel, position, onItemClickListener)
        itemEmploymentInformationSelectionBinding.executePendingBindings()
    }
}
