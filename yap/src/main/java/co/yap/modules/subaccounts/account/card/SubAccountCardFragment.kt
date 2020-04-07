package co.yap.modules.subaccounts.account.card

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubAccountCardBinding
import co.yap.modules.subaccounts.account.dashboard.SubAccountDashBoardFragmentDirections
import co.yap.networking.models.ApiResponse
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterVM
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor


class SubAccountCardFragment :
    BaseRecyclerViewFragment<FragmentSubAccountCardBinding, ISubAccountCard.State, SubAccountCardVM,
            SubAccountCardFragment.Adapter, ApiResponse>() {

    override fun getBindingVariable() = BR.subAccountCardVM

    override fun getLayoutId() = R.layout.fragment_sub_account_card

    override fun onItemClick(view: View, data: Any, pos: Int) {
        navigateForwardWithAnimation(SubAccountDashBoardFragmentDirections.actionSubAccountDashBoardFragmentToHHSalaryProfileFragment())
//        navigate(
//            destinationId = R.id.action_subAccountDashBoardFragment_to_HHSalaryProfileFragment,
//            extras = Bundle()
//        )
    }

    class Adapter(mValue: MutableList<ApiResponse>, navigation: NavController?) :
        BaseRVAdapter<ApiResponse, SubAccountCardItemVM, Adapter.ViewHolder>(
            mValue,
            navigation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: SubAccountCardItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel(viewType: Int) = SubAccountCardItemVM()
        override fun getVariableId() = BR.subAccountCardItemVm

        class ViewHolder(
            view: View,
            viewModel: SubAccountCardItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<ApiResponse, SubAccountCardItemVM>(view, viewModel, mDataBinding)
    }

}