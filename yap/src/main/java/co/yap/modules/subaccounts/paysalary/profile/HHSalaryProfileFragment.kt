package co.yap.modules.subaccounts.paysalary.profile

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhsalaryProfileBinding
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class HHSalaryProfileFragment :
    BaseRecyclerViewFragment<FragmentHhsalaryProfileBinding, IHHSalaryProfile.State, HHSalaryProfileVM, HHSalaryProfileFragment.Adapter, PaySalaryModel>() {

    override fun getBindingVariable() = BR.hhSalaryProfileVM

    override fun getLayoutId() = R.layout.fragment_hhsalary_profile

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        recyclerView?.adapter = Adapter(viewModel.getPaySalaryData(), null)
    }

    override fun onReload(view: View) {
    }

    class Adapter(mValue: MutableList<PaySalaryModel>, navigation: NavController?) :
        BaseRVAdapter<PaySalaryModel, HHSalaryProfileItemVM, Adapter.ViewHolder>(
            mValue,
            navigation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: HHSalaryProfileItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = HHSalaryProfileItemVM()
        override fun getVariableId() = BR.hhSalaryProfileItemVM

        class ViewHolder(
            view: View,
            viewModel: HHSalaryProfileItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<PaySalaryModel, HHSalaryProfileItemVM>(view, viewModel, mDataBinding)
    }

}