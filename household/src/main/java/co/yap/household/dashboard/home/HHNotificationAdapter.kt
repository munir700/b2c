package co.yap.household.dashboard.home

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.household.BR
import co.yap.networking.notification.HomeNotification
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder

class HHNotificationAdapter(mValue: MutableList<HomeNotification>, navigation: NavController?) :
    BaseRVAdapter<HomeNotification, HHNotificationItemVM, BaseViewHolder<HomeNotification, HHNotificationItemVM>>(
        mValue,
        navigation
    ) {
    override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
    override fun getViewHolder(
        view: View,
        viewModel: HHNotificationItemVM,
        mDataBinding: ViewDataBinding, viewType: Int
    ) = BaseViewHolder(view, viewModel, mDataBinding)

    override fun getViewModel(viewType: Int) = HHNotificationItemVM()
    override fun getVariableId() = BR.viewModel
}