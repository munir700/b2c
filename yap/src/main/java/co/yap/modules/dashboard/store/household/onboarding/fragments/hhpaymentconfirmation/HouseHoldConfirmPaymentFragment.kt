package co.yap.modules.dashboard.store.household.onboarding.fragments.hhpaymentconfirmation

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.databinding.FragmentHouseHoldCofirmPaymentV2Binding
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.widgets.popmenu.OnMenuItemClickListener
import co.yap.widgets.popmenu.PopupMenu
import co.yap.widgets.popmenu.PopupMenuItem
import co.yap.yapcore.BR
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.getCurrencyPopMenu
import kotlinx.android.synthetic.main.fragment_house_hold_cofirm_payment.*

class HouseHoldConfirmPaymentFragment :
    BaseNavViewModelFragment<FragmentHouseHoldCofirmPaymentV2Binding, IHouseHoldConfirmPayment.State, HouseHoldConfirmPaymentVM>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_house_hold_cofirm_payment_v2
    private var householdPlanPopMenu: PopupMenu? = null
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        setObservers()
        initComponents()
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.onBoardUserSuccess.observe(this, Observer {
            if (it) findNavController().navigate(R.id.action_HHConfirmPaymentFragment_to_houseHoldSuccessFragment)
        })
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.tvChangePlan -> {
                householdPlanPopMenu?.showAsAnchorRightBottom(tvChangePlan, 0, 30)
            }
            R.id.confirmButton -> {
                viewModel.addHouseholdUser()
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
        getPlansList()?.let {
            for (item in it.iterator()) {
                popMenuHouseholdPlansList.add(PopupMenuItem("${item.type} - ${item.amount}"))
            }
        }

        return popMenuHouseholdPlansList
    }

    private fun getPlansList(): List<HouseHoldPlan>? {
        return viewModel.plansList.value
    }

    private val popupItemClickListener =
        OnMenuItemClickListener<PopupMenuItem?> { position, _ ->
            householdPlanPopMenu?.selectedPosition = position
            populateHouseholdPlanData(getPlansList()?.get(position))
        }

    private fun populateHouseholdPlanData(selectedPlan: HouseHoldPlan?) {
        viewModel.selectedPlanType?.type = selectedPlan?.type
        viewModel.state.selectedPlanFee.set(viewModel.state.currencyType.get() + " " + selectedPlan?.amount)
        viewModel.state.selectedCardPlan.set(selectedPlan?.type + " | " + viewModel.state.selectedPlanFee.get())
        selectedPlan?.discount?.let {
            if (it != 0) {
                viewModel.state.selectedPlanSaving.set("Your saving $it%!")
            } else {
                viewModel.state.selectedPlanSaving.set("")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
        viewModel.onBoardUserSuccess.removeObservers(this)
    }

    override fun onBackPressed(): Boolean = false
}
