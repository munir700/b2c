package co.yap.modules.dashboard.transaction.feedback.adaptor

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemImprovementComponentsBinding
import co.yap.modules.dashboard.transaction.feedback.models.ItemFeedback

class TransactionFeedbackViewHolder (val binding: ItemImprovementComponentsBinding):
RecyclerView.ViewHolder(binding.root){
    fun onBind(
        positiion: Int,
        item: ItemFeedback){
        binding.viewmodel = TransactionFeedbackItemViewModel(item,positiion)
        binding.executePendingBindings()
    }
 }