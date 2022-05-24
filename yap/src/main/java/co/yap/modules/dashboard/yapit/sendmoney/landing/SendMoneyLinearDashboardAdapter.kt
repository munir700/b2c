package co.yap.modules.dashboard.yapit.sendmoney.landing

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels.SendMoneyLinearDashboardItemViewModel
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyLinearOptions
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder


class SendMoneyLinearDashboardAdapter(
    mValue: MutableList<SendMoneyLinearOptions>, navigation: NavController?
) :
    BaseRVAdapter<SendMoneyLinearOptions, SendMoneyLinearDashboardItemViewModel, SendMoneyLinearDashboardAdapter.ViewHolder>(
        mValue, navigation
    ) {
    override fun getLayoutId(viewType: Int): Int = getViewModel(viewType).layoutRes()

    override fun getViewHolder(
        view: View,
        viewModel: SendMoneyLinearDashboardItemViewModel,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ): ViewHolder = ViewHolder(
        view,
        viewModel,
        mDataBinding
    )

    class ViewHolder(
        view: View,
        viewModel: SendMoneyLinearDashboardItemViewModel,
        mDataBinding: ViewDataBinding
    ) : BaseViewHolder<SendMoneyLinearOptions, SendMoneyLinearDashboardItemViewModel>(
        view,
        viewModel,
        mDataBinding
    )

    override fun getViewModel(viewType: Int): SendMoneyLinearDashboardItemViewModel =
        SendMoneyLinearDashboardItemViewModel()

    override fun getVariableId(): Int = BR.viewModel
}