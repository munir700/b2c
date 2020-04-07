package co.yap.modules.subaccounts.paysalary.profile

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class HHSalaryProfileTransfersAdapter(mValue: MutableList<PaySalaryModel>, navigation: NavController?) :
    BaseRVAdapter<PaySalaryModel, HHSalaryProfileItemVM, HHSalaryProfileTransfersAdapter.ViewHolder>(
        mValue,
        navigation
    ) {
    override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
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

    override fun getViewModel(viewType:Int) = HHSalaryProfileItemVM()
    override fun getVariableId() = BR.hhSalaryProfileItemVM

    class ViewHolder(
        view: View,
        viewModel: HHSalaryProfileItemVM,
        mDataBinding: ViewDataBinding
    ) :
        BaseViewHolder<PaySalaryModel, HHSalaryProfileItemVM>(view, viewModel, mDataBinding)
}
