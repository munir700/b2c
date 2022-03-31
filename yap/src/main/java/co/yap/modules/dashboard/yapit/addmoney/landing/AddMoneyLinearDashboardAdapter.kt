package co.yap.modules.dashboard.yapit.addmoney.landing

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder

class AddMoneyLinearDashboardAdapter(
    mValue: MutableList<AddMoneyLandingOptions>, navigation: NavController?
) :
    BaseRVAdapter<AddMoneyLandingOptions, AddMoneyLinearDashboardItemViewModel, AddMoneyLinearDashboardAdapter.ViewHolder>(
        mValue, navigation
    ) {
    override fun getLayoutId(viewType: Int): Int = getViewModel(viewType).layoutRes()

    override fun getViewHolder(
        view: View,
        viewModel: AddMoneyLinearDashboardItemViewModel,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ): ViewHolder = ViewHolder(
        view,
        viewModel,
        mDataBinding
    )

    class ViewHolder(
        view: View,
        viewModel: AddMoneyLinearDashboardItemViewModel,
        mDataBinding: ViewDataBinding
    ) : BaseViewHolder<AddMoneyLandingOptions, AddMoneyLinearDashboardItemViewModel>(
        view,
        viewModel,
        mDataBinding
    )

    override fun getViewModel(viewType: Int): AddMoneyLinearDashboardItemViewModel =
        AddMoneyLinearDashboardItemViewModel()

    override fun getVariableId(): Int = BR.viewModel
}