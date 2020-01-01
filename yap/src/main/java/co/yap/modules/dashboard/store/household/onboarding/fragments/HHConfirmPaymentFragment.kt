package co.yap.modules.dashboard.store.household.onboarding.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldConfirmPayment
import co.yap.modules.dashboard.store.household.onboarding.viewmodels.HouseHoldConfirmPaymentViewModel
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.widgets.popmenu.OnMenuItemClickListener
import co.yap.widgets.popmenu.PopupMenu
import co.yap.widgets.popmenu.PopupMenuItem
import co.yap.yapcore.BR
import co.yap.yapcore.helpers.extentions.getCurrencyPopMenu
import kotlinx.android.synthetic.main.fragment_house_hold_cofirm_payment.*

class HHConfirmPaymentFragment : BaseOnBoardingFragment<IHouseHoldConfirmPayment.ViewModel>(),
    IHouseHoldConfirmPayment.View {
    override fun getBindingVariable(): Int = BR.viewModel
    private var householdPlanPopMenu: PopupMenu? = null
    override fun getLayoutId(): Int = R.layout.fragment_house_hold_cofirm_payment
    override val viewModel: IHouseHoldConfirmPayment.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldConfirmPaymentViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.tvChangePlan -> {
                householdPlanPopMenu?.showAsAnchorRightBottom(tvChangePlan, 0, 30)
            }
            R.id.confirmButton -> {
                findNavController().navigate(R.id.action_HHConfirmPaymentFragment_to_houseHoldSuccessFragment)
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
        val list = getPlansList()

        for (item in list.iterator()) {
            popMenuHouseholdPlansList.add(PopupMenuItem("${item.type} - AED ${item.amount}"))
        }

        return popMenuHouseholdPlansList
    }

    private fun getPlansList(): List<HouseHoldPlan> {
        val plans: ArrayList<HouseHoldPlan> = ArrayList()
        plans.add(HouseHoldPlan(type = "Monthly", amount = "59.99"))
        plans.add(HouseHoldPlan(type = "Yearly", amount = "720.00", discount = 25.00))
        return plans
    }

    private val popupItemClickListener =
        OnMenuItemClickListener<PopupMenuItem?> { position, _ ->
            householdPlanPopMenu?.selectedPosition = position
            populateHouseholdPlanData(getPlansList()[position])
        }

    private fun populateHouseholdPlanData(selectedPlan: HouseHoldPlan?) {
        viewModel.state.selectedPlanFee.set(viewModel.state.currencyType.get() + " " + selectedPlan?.amount)
        viewModel.state.selectedCardPlan.set(selectedPlan?.type + " | " + viewModel.state.selectedPlanFee.get())
        selectedPlan?.discount?.let {
            if (it != 0.0) {
                viewModel.state.selectedPlanSaving.set("Your saving $it%!")
            } else {
                viewModel.state.selectedPlanSaving.set("")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }
}