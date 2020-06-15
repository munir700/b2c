package co.yap.modules.dashboard.store.household.activities.subscriptionselection

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityHouseHoldSubscriptionSelctionBinding
import co.yap.databinding.FragmentHouseHoldSubscriptionSelctionBinding
import co.yap.modules.dashboard.store.household.onboarding.HouseHoldOnboardingActivity
import co.yap.modules.onboarding.models.WelcomeContent
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.extentions.launchActivityForResult
import kotlinx.android.synthetic.main.fragment_house_hold_subscription_selction.*
import javax.inject.Inject

class SubscriptionSelectionFragment :
    BaseNavViewModelFragment<FragmentHouseHoldSubscriptionSelctionBinding, ISubscriptionSelection.State, SubscriptionSelectionVM>() {
    lateinit var item: View
    var selectedPosition: Int = 0
    var incrementValue: Boolean = true
    var exitEvent: Boolean = false
    var selectedPlan: HouseHoldPlan = HouseHoldPlan()

    @Inject
    lateinit var adapter: Adapter

    @Inject
    lateinit var list: ArrayList<WelcomeContent>
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_house_hold_subscription_selction

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        adapter = Adapter(list, null)
        pagerSlider.adapter = adapter
        worm_dots_indicator?.setViewPager2(pagerSlider)
        addListeners()
    }

    private fun addListeners() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnClose -> {
                    setIntentResult(false)
                }
                R.id.llAnnualSubscription -> {
                    viewModel.state.hasSelectedPackage = true
                    llMonthlySubscription.isActivated = false
                    llAnnualSubscription.isActivated = true
                    if (!viewModel.plansList.isNullOrEmpty())
                        selectedPlan = viewModel.plansList[1]
                }

                R.id.llMonthlySubscription -> {
                    viewModel.state.hasSelectedPackage = true
                    llMonthlySubscription.isActivated = true
                    llAnnualSubscription.isActivated = false
                    if (!viewModel.plansList.isNullOrEmpty())
                        selectedPlan = viewModel.plansList[0]
                }

                R.id.btnGetStarted -> {
                    if (!viewModel.plansList.isNullOrEmpty())
                        launchActivityForResult<HouseHoldOnboardingActivity>(
                            init = {
                                putExtra(
                                    "selected_plan", selectedPlan
                                )
                                putExtra("plans_list", viewModel.plansList)
                            },
                            requestCode = RequestCodes.REQUEST_ADD_HOUSE_HOLD,
                            completionHandler = { resultCode, data ->
                                if (resultCode == RequestCodes.REQUEST_ADD_HOUSE_HOLD) {
                                    if (resultCode == Activity.RESULT_OK) {
                                        data?.let {
                                            val finishScreen =
                                                data.getValue(
                                                    RequestCodes.REQUEST_CODE_FINISH,
                                                    ExtraType.BOOLEAN.name
                                                ) as? Boolean
                                            finishScreen?.let { it ->
                                                if (it) {
                                                    setIntentResult(true)
                                                } else {
                                                    // other things?
                                                }
                                            }
                                        }
                                    }
                                }
                            })
                }

                R.id.imgClose -> {
                    setIntentResult(false)
                }
            }
        })
    }

    private fun setIntentResult(shouldFinished: Boolean) {
        val intent = Intent()
        intent.putExtra(RequestCodes.REQUEST_CODE_FINISH, shouldFinished)
        requireActivity().setResult(Activity.RESULT_OK, intent)
//        finish()
    }

    override fun toolBarVisibility() = false
    class Adapter(mValue: ArrayList<WelcomeContent>, navigation: NavController?) :
        BaseRVAdapter<WelcomeContent, SubscriptionSelectionItemVM, BaseViewHolder<WelcomeContent, SubscriptionSelectionItemVM>>(
            mValue,
            navigation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: SubscriptionSelectionItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ) = BaseViewHolder(view, viewModel, mDataBinding)

        override fun getViewModel(viewType: Int) = SubscriptionSelectionItemVM()
        override fun getVariableId() = BR.content
    }
}