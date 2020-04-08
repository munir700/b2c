package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import android.view.*
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.BottomSheetHouseHouldMainAccountBinding
import co.yap.databinding.FragmentHhsalaryProfileBinding
import co.yap.modules.subaccounts.paysalary.subscription.SubscriptionFragment
import co.yap.translation.Strings
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.helpers.extentions.startFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class HHSalaryProfileFragment :
    BaseRecyclerViewFragment<FragmentHhsalaryProfileBinding, IHHSalaryProfile.State, HHSalaryProfileVM, HHSalaryProfileTransfersAdapter, PaySalaryModel>() {

    override fun getBindingVariable() = BR.hhSalaryProfileVM
    private lateinit var houseHoldMainAccountBottomSheet: HouseHoldMainAccountBottomSheet

    override fun getLayoutId() = R.layout.fragment_hhsalary_profile

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        setObservers()
        houseHoldMainAccountBottomSheet =
            HouseHoldMainAccountBottomSheet(
                viewModel
            )
        setHasOptionsMenu(true)
        viewModel.setUpData(getPaySalaryData(), 2)
    }

    fun getPaySalaryData(): ArrayList<PaySalaryModel> {
        val array: ArrayList<PaySalaryModel> = ArrayList()
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

        override fun getViewModel(viewType: Int) = HHSalaryProfileItemVM()
        override fun getVariableId() = BR.hhSalaryProfileItemVM

        class ViewHolder(
            view: View,
            viewModel: HHSalaryProfileItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<PaySalaryModel, HHSalaryProfileItemVM>(view, viewModel, mDataBinding)
    }

    class HouseHoldMainAccountBottomSheet(
        var viewModel: HHSalaryProfileVM
    ) : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val binding: BottomSheetHouseHouldMainAccountBinding =
                BottomSheetHouseHouldMainAccountBinding.inflate(inflater, container, false)
            binding.viewModel = viewModel
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
            return binding.root
        }

    }

       private var clickEvent1 = Observer<Int> {
        when (it) {
            R.id.tvSubscription -> {
                startFragment(SubscriptionFragment::class.java.name, showToolBar = true)
                houseHoldMainAccountBottomSheet.dismiss()
            }
            R.id.tvSalaryStatements -> {
                houseHoldMainAccountBottomSheet.dismiss()
            }

        }
    }
    fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent1)
    }

    fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }
    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

}