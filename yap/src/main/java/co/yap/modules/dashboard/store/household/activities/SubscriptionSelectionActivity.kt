package co.yap.modules.dashboard.store.household.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.modules.dashboard.cards.addpaymentcard.spare.SpareCardsLandingAdapter
import co.yap.modules.dashboard.store.household.interfaces.IHouseHoldSubscription
import co.yap.modules.dashboard.store.household.onboarding.HouseHoldOnboardingActivity
import co.yap.modules.dashboard.store.household.viewmodels.SubscriptionSelectionViewModel
import co.yap.yapcore.BaseBindingActivity
import kotlinx.android.synthetic.main.activity_house_hold_subscription_selction.*

class SubscriptionSelectionActivity :
    BaseBindingActivity<IHouseHoldSubscription.ViewModel>(),
    IHouseHoldSubscription.View, SpareCardsLandingAdapter.OnItemClickedListener {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_house_hold_subscription_selction

    override val viewModel: IHouseHoldSubscription.ViewModel
        get() = ViewModelProviders.of(this).get(SubscriptionSelectionViewModel::class.java)

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SubscriptionSelectionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addBenefitRecyclerView()

    }

    override fun onResume() {
        super.onResume()

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.btnClose -> {
//                    startActivity(newIntent(this))
                    finish() // will check in fsd/story till where is it required to go back
                }
                R.id.llAnnualSubscription -> {
                    startActivity(HouseHoldOnboardingActivity.newIntent(this))
//                    showToast("llAnnualSubscription")
                }


                R.id.llMonthlySubscription -> {
                    startActivity(HouseHoldOnboardingActivity.newIntent(this))
//                    showToast("llMonthlySubscription")
                }

                R.id.imgClose -> {
                    finish()
                }

            }
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.clickEvent.removeObservers(this)

    }

    private fun addBenefitRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        rvSubscriptionPackageBenefits.layoutManager = layoutManager
        rvSubscriptionPackageBenefits.isNestedScrollingEnabled = false
        rvSubscriptionPackageBenefits.adapter =
            SpareCardsLandingAdapter(
                viewModel.loadDummyData(),
                this
            )
    }

    override fun onItemClick(benefitsModel: BenefitsModel) {
//        start benefits screen if required

    }
}