package co.yap.household.dashboard.home

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.household.BR
import co.yap.household.databinding.ItemHhNotificationBinding
import co.yap.modules.dashboard.home.interfaces.NotificationItemClickListener
import co.yap.networking.notification.responsedtos.HomeNotification
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder

class HHNotificationAdapter(mValue: MutableList<HomeNotification>, navigation: NavController?, val listener: NotificationItemClickListener?) :
    BaseRVAdapter<HomeNotification, HHNotificationItemVM, BaseViewHolder<HomeNotification, HHNotificationItemVM>>(
        mValue,
        navigation
    ) {
    override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
    override fun getViewHolder(
        view: View,
        viewModel: HHNotificationItemVM,
        mDataBinding: ViewDataBinding, viewType: Int
    ) = ViewHolder(view, viewModel, mDataBinding, listener)

    override fun getViewModel(viewType: Int) = HHNotificationItemVM()
    override fun getVariableId() = BR.viewModel

    class ViewHolder(view: View, viewModel: HHNotificationItemVM, mDataBinding: ViewDataBinding, listener: NotificationItemClickListener?) :
        BaseViewHolder<HomeNotification, HHNotificationItemVM>(view, viewModel, mDataBinding) {

        init {
            val binding = mDataBinding as ItemHhNotificationBinding
            binding.ivCross.setOnClickListener {
                listener?.onCloseClick(viewModel.getItem(),adapterPosition)
            }
        }
    }
}