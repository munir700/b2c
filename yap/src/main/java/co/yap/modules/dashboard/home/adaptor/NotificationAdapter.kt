package co.yap.modules.dashboard.home.adaptor

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.dashboard.home.interfaces.NotificationItemClickListener
import co.yap.networking.notification.responsedtos.HomeNotification
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.databinding.ViewNotificationsBinding

class NotificationAdapter(
    val context: Context,
    val listItems: MutableList<HomeNotification>,
    val clickListener: NotificationItemClickListener
) :
    BaseBindingRecyclerAdapter<HomeNotification, NotificationAdapter.ViewHolder>(listItems) {

    override fun onCreateViewHolder(binding: ViewDataBinding): ViewHolder {
        return ViewHolder(binding as ViewNotificationsBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.view_notifications

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listItems[position])
    }

    inner class ViewHolder(val binding: ViewNotificationsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(notification: HomeNotification) {

            binding.tvTitle.text = notification.title

            notification.fileName?.let { binding.lottie.setAnimation(it) }

//            binding.ivNotification
            binding.tvDescription.text = notification.description
            if (notification.title?.isBlank() == true) {
                binding.tvTitle.visibility = View.INVISIBLE
            } else {
                binding.tvTitle.visibility = View.VISIBLE
            }

            binding.cvNotification.setOnClickListener {
                clickListener.onClick(listItems[adapterPosition], adapterPosition)
            }

            binding.ivCross.setOnClickListener {
                clickListener.onCloseClick(listItems[adapterPosition], adapterPosition)
            }
        }
    }
}