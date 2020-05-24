package co.yap.modules.dashboard.home.adaptor

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ViewNotificationsBinding
import co.yap.modules.dashboard.home.interfaces.NotificationItemClickListener
import co.yap.networking.notification.HomeNotification
import co.yap.yapcore.BaseBindingRecyclerAdapter

class NotificationAdapter(
    val listItems: MutableList<HomeNotification>,
    val clickListener: NotificationItemClickListener
) :
    BaseBindingRecyclerAdapter<HomeNotification, NotificationAdapter.ViewHolder>(listItems) {


    override fun onCreateViewHolder(binding: ViewDataBinding): ViewHolder {
        return ViewHolder(binding as ViewNotificationsBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.view_notifications

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification: HomeNotification = listItems[position]

        holder.binding?.tvTitle?.text = notification.title
        holder.binding?.tvDescription?.text = notification.description
        if (notification.title.isBlank()) {
            holder.binding?.tvTitle?.visibility = View.GONE
        } else {
            holder.binding?.tvTitle?.visibility = View.VISIBLE
        }
    }

    inner class ViewHolder(binding: ViewNotificationsBinding) :
        RecyclerView.ViewHolder(binding.root) {
//        val ivNotification: ImageView = itemView.ivNotification
//        val ivCross: ImageView = itemView.ivCross
//        val tvTitle: TextView = itemView.tvTitle
//        val tvDescription: TextView = itemView.tvDescription
//        val cvNotification: CardView = itemView.cvNotification

        val binding: ViewNotificationsBinding?

        init {
            this.binding = binding
            binding.cvNotification.setOnClickListener {
                try {
                    clickListener.onClick(listItems[adapterPosition])
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            binding.ivCross.setOnClickListener {
                try {
                    clickListener.onCloseClick(listItems[adapterPosition])
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }
}