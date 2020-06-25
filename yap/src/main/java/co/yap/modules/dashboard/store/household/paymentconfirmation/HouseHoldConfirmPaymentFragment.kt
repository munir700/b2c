package co.yap.modules.dashboard.store.household.paymentconfirmation

import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.databinding.FragmentHouseHoldCofirmPaymentV2Binding
import co.yap.modules.dashboard.yapit.topup.landing.TopUpLandingActivity
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.widgets.popmenu.OnMenuItemClickListener
import co.yap.widgets.popmenu.PopupMenu
import co.yap.widgets.popmenu.PopupMenuItem
import co.yap.yapcore.BR
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.getCurrencyPopMenu
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.launchActivityForResult
import co.yap.yapcore.helpers.extentions.plus
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import kotlinx.android.synthetic.main.fragment_house_hold_cofirm_payment.*

class HouseHoldConfirmPaymentFragment :
    BaseNavViewModelFragment<FragmentHouseHoldCofirmPaymentV2Binding, IHouseHoldConfirmPayment.State, HouseHoldConfirmPaymentVM>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_house_hold_cofirm_payment_v2
    private var householdPlanPopMenu: PopupMenu? = null
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        viewModel.clickEvent.observe(this, onClick)
        initComponents()
        GetAccountBalanceLiveData.get().observe(this, Observer {
            state.availableBalance?.value = it?.availableBalance
        })
    }

    private val onClick = Observer<Int> {
        when (it) {
            R.id.tvChangePlan -> {
                householdPlanPopMenu?.showAsAnchorRightBottom(tvChangePlan, 0, 30)
            }

            R.id.tvTopUp -> launchActivityForResult<TopUpLandingActivity>(completionHandler = { resultCode, data ->
                GetAccountBalanceLiveData.get().observe(this, Observer {
                    state.availableBalance?.value = it?.availableBalance
                })
            })
            R.id.confirmButton -> {
                viewModel.addHouseholdUser() {
                    navigateForwardWithAnimation(
                        HouseHoldConfirmPaymentFragmentDirections.actionHouseHoldConfirmPaymentFragmentToHHAddUserSuccessFragment(),
                        arguments?.plus(bundleOf(HouseholdOnboardRequest::class.java.name to state.onBoardRequest?.value))
                    )
                }
            }
        }
    }

    private fun initComponents() {
        householdPlanPopMenu =
            requireContext().getCurrencyPopMenu(
                this,
                getHouseholdPlans(),
                popupItemClickListener,
                null
            )
    }

    private fun getHouseholdPlans(): List<PopupMenuItem> {
        val popMenuHouseholdPlansList = ArrayList<PopupMenuItem>()
        state.plansList?.value?.forEach { item -> popMenuHouseholdPlansList.add(PopupMenuItem("${item.type} - ${item.amount}")) }
        return popMenuHouseholdPlansList
    }

    private val popupItemClickListener =
        OnMenuItemClickListener<PopupMenuItem?> { position, _ ->
            householdPlanPopMenu?.selectedPosition = position
            state.selectedPlan?.value = state.plansList?.value?.get(position)
        }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onBackPressed(): Boolean = false
}
