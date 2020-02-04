package co.yap.modules.dashboard.more.notification.viewholder

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemNotificationBinding
import co.yap.modules.dashboard.more.notification.viewmodels.NotificationItemViewModel
import co.yap.modules.yapnotification.models.Notification

class NotificationViewHolder(private val itemNotificationBinding: ItemNotificationBinding) :
    RecyclerView.ViewHolder(itemNotificationBinding.root) {

    fun onBind(notification: Notification) {
        itemNotificationBinding.viewModel = NotificationItemViewModel(notification)
        //notification.date?.let {
        //    itemNotificationBinding.tvNumber.text = DateUtils.stringToDateLeanPlum(it)
        //}
        itemNotificationBinding.tvNumber.text = notification.date
        itemNotificationBinding.executePendingBindings()
    }
}
