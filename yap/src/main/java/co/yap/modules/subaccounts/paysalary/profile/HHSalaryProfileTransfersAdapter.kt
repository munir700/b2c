package co.yap.modules.subaccounts.paysalary.profile

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

const val HEADER_SECTION = 1
const val TRANSACTION = 2
class HHSalaryProfileTransfersAdapter(mValue: MutableList<PaySalaryModel>, navigation: NavController?) :
    BaseRVAdapter<PaySalaryModel, HHSalaryProfileTransfersItemVM, HHSalaryProfileTransfersAdapter.ViewHolder>(
        mValue,
        navigation
    ) {

    override fun getLayoutId(viewType: Int):Int {
        if(viewType == HEADER_SECTION){
            return R.layout.item_hhsalary_profile_transaction_list_header
        }else{
            return getViewModel(viewType).layoutRes()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position % 3 == 0){
            return HEADER_SECTION
        }else {
            return TRANSACTION
        }
    }

    override fun getItemCount(): Int {
        return 21
    }

    override fun getViewHolder(
        view: View,
        viewModel: HHSalaryProfileTransfersItemVM,
        mDataBinding: ViewDataBinding, viewType: Int
    ): ViewHolder {
        val kotlinClass: KClass<ViewHolder> = ViewHolder::class
        val ctor = kotlinClass.primaryConstructor
        val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
        return myObject
    }

    override fun getViewModel(viewType:Int) = HHSalaryProfileTransfersItemVM()
    override fun getVariableId() = BR.hhSalaryProfileItemVM

    class ViewHolder(
        view: View,
        viewModel: HHSalaryProfileTransfersItemVM,
        mDataBinding: ViewDataBinding
    ) :
        BaseViewHolder<PaySalaryModel, HHSalaryProfileTransfersItemVM>(view, viewModel, mDataBinding)

}
