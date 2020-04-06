package co.yap.modules.subaccounts.paysalary.profile

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhsalaryProfileBinding
import co.yap.modules.subaccounts.HouseHoldMainAccountBottomSheet
import co.yap.modules.subaccounts.HouseHoldMainAccountBottomSheetClick
import co.yap.modules.subaccounts.paysalary.subscription.SubscriptionFragment
import co.yap.translation.Strings
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.toast
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class HHSalaryProfileFragment :
    BaseRecyclerViewFragment<FragmentHhsalaryProfileBinding, IHHSalaryProfile.State, HHSalaryProfileVM, HHSalaryProfileFragment.Adapter, PaySalaryModel>(),
    HouseHoldMainAccountBottomSheetClick {

    override fun getBindingVariable() = BR.hhSalaryProfileVM
    private lateinit var houseHoldMainAccountBottomSheet: HouseHoldMainAccountBottomSheet

    override fun getLayoutId() = R.layout.fragment_hhsalary_profile

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        houseHoldMainAccountBottomSheet =
            HouseHoldMainAccountBottomSheet(this)
        viewModel.setUpData(getPaySalaryData())
        setHasOptionsMenu(true)
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

    override var toolBarTitle: String? = "Your Name"
    //override var toolBarVisibility: Boolean? = false

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cart -> {
                houseHoldMainAccountBottomSheet.show(fragmentManager!!, "")
            }
            else -> return false
        }
        return true

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

    override fun onSubscriptionClick() {
        startFragment(SubscriptionFragment::class.java.name,showToolBar = true)
        houseHoldMainAccountBottomSheet.dismiss()
    }

    override fun salaryStatementClick() {
        houseHoldMainAccountBottomSheet.dismiss()

    }

}