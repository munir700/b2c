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
import co.yap.modules.dashboard.store.household.viewmodels.HouseHoldSubscriptionViewModel
import co.yap.yapcore.BaseBindingActivity
import kotlinx.android.synthetic.main.activity_yap_house_hold_subscription_selction.*

class YapHouseHoldSubscriptionSelectionActivity :
    BaseBindingActivity<IHouseHoldSubscription.ViewModel>(),
    IHouseHoldSubscription.View, SpareCardsLandingAdapter.OnItemClickedListener {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_yap_house_hold_subscription_selction

    override val viewModel: IHouseHoldSubscription.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldSubscriptionViewModel::class.java)

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, YapHouseHoldSubscriptionSelectionActivity::class.java)
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

                R.id.llAnnualSubscription -> {
                    startActivity(newIntent(this))
                }

                R.id.llMonthlySubscription -> {
                    startActivity(newIntent(this))
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