package co.yap.modules.dashboard.store.household.paymentconfirmation

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import co.yap.R
import co.yap.databinding.FragmentHouseHoldCofirmPaymentBinding
import co.yap.modules.dashboard.yapit.topup.landing.TopUpLandingActivity
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.widgets.popmenu.OnMenuItemClickListener
import co.yap.widgets.popmenu.PopupMenu
import co.yap.widgets.popmenu.PopupMenuItem
import co.yap.yapcore.BR
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.getCurrencyPopMenu
import co.yap.yapcore.helpers.extentions.launchActivityForResult
import co.yap.yapcore.helpers.extentions.plus
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import kotlinx.android.synthetic.main.fragment_house_hold_cofirm_payment.*

class HouseHoldConfirmPaymentFragment :
    BaseNavViewModelFragment<FragmentHouseHoldCofirmPaymentBinding, IHouseHoldConfirmPayment.State, HouseHoldConfirmPaymentVM>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_house_hold_cofirm_payment
    private var householdPlanPopMenu: PopupMenu? = null
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        initComponents()
        GetAccountBalanceLiveData.get().observe(this, Observer {
            state.availableBalance?.value = it?.availableBalance
        })
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.tvChangePlan -> {
                householdPlanPopMenu?.showAsAnchorRightBottom(tvChangePlan, 0, 30)
            }

            R.id.tvTopUp -> launchActivityForResult<TopUpLandingActivity>(completionHandler = { resultCode, data ->
                GetAccountBalanceLiveData.get().observe(this, Observer {
                    state.availableBalance?.value = it?.availableBalance
                })
            })
            R.id.confirmButton -> {
                viewModel.addHouseholdUser {
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

    override fun onBackPressed(): Boolean = false
}
