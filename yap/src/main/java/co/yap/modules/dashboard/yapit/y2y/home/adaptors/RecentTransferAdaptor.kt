package co.yap.modules.dashboard.yapit.y2y.home.adaptors

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.modules.dashboard.yapit.y2y.home.viewmodel.RecentTransferItemVM
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiary
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import kotlin.reflect.full.primaryConstructor


class RecentTransferAdaptor(mValue: MutableList<RecentBeneficiary>, navigation: NavController): BaseRVAdapter<RecentBeneficiary, RecentTransferItemVM, RecentTransferAdaptor.ViewHolder>(mValue,navigation) {
    override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
    override fun getViewHolder(
        view: View,
        viewModel: RecentTransferItemVM,
        mDataBinding: ViewDataBinding, viewType: Int
    ): ViewHolder {
        return ViewHolder::class.primaryConstructor?.call(view, viewModel, mDataBinding) as ViewHolder
    }

    override fun getViewModel() = RecentTransferItemVM()
    override fun getVariableId() = BR.recentTransferItemVM

    class ViewHolder(view: View, viewModel: RecentTransferItemVM, mDataBinding: ViewDataBinding) :
        BaseViewHolder<RecentBeneficiary, RecentTransferItemVM>(view, viewModel, mDataBinding)
}