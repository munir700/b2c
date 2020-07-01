package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhsalaryProfileBinding
import co.yap.networking.models.ApiResponse
import co.yap.yapcore.BaseListItemViewModel
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.dagger.base.BaseRecyclerViewFragment
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.toast
import com.arthurivanets.bottomsheets.ktx.actionPickerConfig
import com.arthurivanets.bottomsheets.ktx.showActionPickerBottomSheet
import com.arthurivanets.bottomsheets.sheets.listeners.OnItemSelectedListener
import com.arthurivanets.bottomsheets.sheets.model.Option
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class HHSalaryProfileFragment :
    BaseRecyclerViewFragment<FragmentHhsalaryProfileBinding, IHHSalaryProfile.State, HHSalaryProfileVM, HHSalaryProfileFragment.Adapter, PaySalaryModel>() {

    override fun getBindingVariable() = BR.hhSalaryProfileVM
    override fun getLayoutId() = R.layout.fragment_hhsalary_profile

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setHasOptionsMenu(true)
        setRefreshEnabled(false)
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.ivSalary -> navigateForwardWithAnimation(
                HHSalaryProfileFragmentDirections.actionHHSalaryProfileFragmentToPayHHEmployeeSalaryFragment(),
                arguments
            )
            R.id.ivExpenses -> toast("Coming Soon")
            R.id.ivUserImage -> navigateForwardWithAnimation(
                HHSalaryProfileFragmentDirections.actionHHSalaryProfileFragmentToHHProfileFragment(),
                arguments
            )
            R.id.ivTransfer -> navigateForwardWithAnimation(
                HHSalaryProfileFragmentDirections.actionHHSalaryProfileFragmentToHHIbanSendMoneyFragment(),
                arguments
            )
        }
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
        when (pos) {
            0 -> navigateForwardWithAnimation(
                HHSalaryProfileFragmentDirections.actionHHSalaryProfileFragmentToPayHHEmployeeSalaryFragment(),
                arguments
            )
            1 -> toast("Coming Soon")
            2 -> navigateForwardWithAnimation(
                HHSalaryProfileFragmentDirections.actionHHSalaryProfileFragmentToHHIbanSendMoneyFragment(),
                arguments
            )
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }

    override fun getToolBarTitle() = state.subAccount.value?.getFullName()
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showActionPickerBottomSheet(
            options = ArrayList<Option>().apply {
                add(Option().setId(1L).setTitle("Subscription"))
                add(Option().setId(2L).setTitle("Salary statements"))
            },
            config = actionPickerConfig {
                sheetAnimationDuration(300L)
                    .topGapSize(dimen(R.dimen.margin_extra_small).toFloat())
            },
            onItemSelectedListener = OnItemSelectedListener {
                when (it.title) {
                    "Subscription" -> navigateForwardWithAnimation(
                        HHSalaryProfileFragmentDirections.actionHHSalaryProfileFragmentToSubscriptionFragment(),
                        arguments
                    )
                    "Salary statements" -> toast("Coming Soon")
                }
            }
        )
        return super.onOptionsItemSelected(item)
    }

    class Adapter(mValue: MutableList<PaySalaryModel>, navigation: NavController?) :
        BaseRVAdapter<PaySalaryModel, HHSalaryProfileItemVM, BaseViewHolder<PaySalaryModel, HHSalaryProfileItemVM>>(
            mValue,
            navigation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: HHSalaryProfileItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ) = BaseViewHolder(view, viewModel, mDataBinding)

        override fun getViewModel(viewType: Int) = HHSalaryProfileItemVM()
        override fun getVariableId() = BR.hhSalaryProfileItemVM
    }
}