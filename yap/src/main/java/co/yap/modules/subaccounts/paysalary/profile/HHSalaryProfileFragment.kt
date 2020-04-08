package co.yap.modules.subaccounts.paysalary.profile

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhsalaryProfileBinding
import co.yap.translation.Strings
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class HHSalaryProfileFragment :
    BaseRecyclerViewFragment<FragmentHhsalaryProfileBinding, IHHSalaryProfile.State, HHSalaryProfileVM, HHSalaryProfileTransfersAdapter, PaySalaryModel>() {

    override fun getBindingVariable() = BR.hhSalaryProfileVM

    override fun getLayoutId() = R.layout.fragment_hhsalary_profile

    override fun onReload(view: View) {
    }

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()

        viewModel.setUpData(getPaySalaryData(), 2)
    }

    fun getPaySalaryData(): ArrayList<PaySalaryModel> {
        var array: ArrayList<PaySalaryModel> = ArrayList()
        array.add(
            PaySalaryModel(
                Strings.screen_house_hold_salary_profile_set_up_salary_text,
                context?.getDrawable(R.drawable.ic_transaction_rate_arrow)
            )
        )
        array.add(
            PaySalaryModel(
                Strings.screen_house_hold_salary_profile_set_up_expense_text,
                context?.getDrawable(R.drawable.ic_expense)
            )
        )
        array.add(
            PaySalaryModel(
                Strings.screen_house_hold_salary_profile_transfer_bonus_text,
                context?.getDrawable(R.drawable.ic_yap_to_yap)
            )
        )

        return array
    }

    override var toolBarTitle: String? = "Joe Smith"
    //override var toolBarVisibility: Boolean? = false

    class Adapter(mValue: MutableList<PaySalaryModel>, navigation: NavController?) :
        BaseRVAdapter<PaySalaryModel, HHSalaryProfileItemVM, HHSalaryProfileFragment.Adapter.ViewHolder>(
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


}