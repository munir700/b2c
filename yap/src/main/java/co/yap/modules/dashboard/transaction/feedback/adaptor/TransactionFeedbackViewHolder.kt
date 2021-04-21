package co.yap.modules.dashboard.transaction.feedback.adaptor

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemImprovementComponentsBinding
import co.yap.modules.dashboard.transaction.feedback.TransactionFeedbackViewModel
import co.yap.modules.dashboard.transaction.feedback.models.ItemTransactionFeedback

class TransactionFeedbackViewHolder (val binding: ItemImprovementComponentsBinding):
RecyclerView.ViewHolder(binding.root){
    fun onBind(
        positiion: Int,
        item: ItemTransactionFeedback){
        binding.viewmodel = TransactionFeedbackItemViewModel(item,positiion)
        binding.executePendingBindings()
    }
 }